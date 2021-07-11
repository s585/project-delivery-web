package tech.itpark.framework.http;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.itpark.framework.bodyconverter.BodyConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestResponseReaderWriter {
    private final List<BodyConverter> converters;

    public <T> T read(Class<T> clazz, HttpServletRequest request) {
        for (final var converter : converters) {
            if (!converter.canRead(request.getContentType(), clazz)) {
                continue;
            }

            try {
                InputStream inputStream = request.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream , StandardCharsets.UTF_8));
//                String collect = reader.lines().collect(Collectors.joining());
                return converter.read(request, reader, clazz);
//                return converter.read(request, request.getReader(), clazz);
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: convert to special exception
                throw new RuntimeException(e);
            }
        }
        // TODO: convert to special exception
        throw new RuntimeException("no converters support given content type");
    }

    public void write(Object data, String contentType, HttpServletResponse response) {
        for (final var converter : converters) {
            if (!converter.canWrite(contentType, data.getClass())) {
                continue;
            }

            try {
                response.setContentType(contentType);
                converter.write(response, response.getWriter(), data);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: convert to special exception
                throw new RuntimeException(e);
            }
        }
        // TODO: convert to special exception
        throw new RuntimeException("no converters support given content type");
    }

    public void error(int statusCode, Object data, String contentType, HttpServletResponse response){
        response.setStatus(statusCode);
        write(data, contentType, response);
    }

}

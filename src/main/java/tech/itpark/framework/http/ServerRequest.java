package tech.itpark.framework.http;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ServerRequest {

    private static final String TOKEN = "token";

    private final HttpServletRequest original;
    private final RequestResponseReaderWriter rw;


    public String getToken() {
        return original.getHeader("Authorization").replace("Bearer_", "");
    }

    public String getRequestBody() {
        try {
            return original.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRequestParameter(String parameterName) {
        return original.getParameter(parameterName);
    }

    public String getParameter(String parameterName) {
        return original.getParameter(parameterName);
    }

    public String getBody() {
        try {
            return original.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("error");
        }
    }

    public <T> T read(Class<T> clazz) {
        return rw.read(clazz, original);
    }
}

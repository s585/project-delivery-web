package tech.itpark.framework.bodyconverter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.project_delivery_web.exception.ConversionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
// option -> r -> i -> enter
public class JacksonBodyConverter implements BodyConverter {

    @Override
    public boolean canRead(String contentType, Class<?> clazz) {
        return ContentTypes.APPLICATION_JSON.equals(contentType);
    }

    @Override
    public boolean canWrite(String contentType, Class<?> clazz) {
        return ContentTypes.APPLICATION_JSON.equals(contentType);
    }

    @Override
    public <T> T read(HttpServletRequest request, Reader reader, Class<T> clazz) {
        try {
            String jsonString = ((BufferedReader) reader).lines().collect(Collectors.joining());
            return JsonUtil.fromJson(jsonString, clazz);
        } catch (Exception e) {
            throw new ConversionException(e);
        }
    }

    // TODO: convert to unchecked exception
    @Override
    public void write(HttpServletResponse response, Writer writer, Object value) {
        try {
            String jsonString = JsonUtil.toJsonString(value);
            writer.write(jsonString);

        } catch (Exception e) {
            throw new ConversionException(e);
        }
    }

    public static class JsonUtil {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        public static byte[] toJson(Object object) throws JsonProcessingException {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            ObjectWriter objectWriter = objectMapper.writer();

            return objectWriter.writeValueAsBytes(object);
        }

        public static String toJsonString(Object object) throws JsonProcessingException {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

            return objectWriter.writeValueAsString(object);
        }

        public static <T> T fromJson(String contentAsString, TypeReference<T> valueType) throws IOException {
            return new ObjectMapper().readValue(contentAsString, valueType);
        }

        public static <T> T fromJson(String contentAsString, Class<T> valueType) throws IOException {
            return new ObjectMapper().readValue(contentAsString, valueType);
        }
    }
}
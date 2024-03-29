package tech.itpark.framework.bodyconverter;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.project_delivery_web.exception.ConversionException;

import java.io.Reader;
import java.io.Writer;

@RequiredArgsConstructor
// option -> r -> i -> enter
public class GsonBodyConverter implements BodyConverter {
  private final Gson gson;

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
      return gson.fromJson(reader, clazz);
    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }

  // TODO: convert to unchecked exception
  @Override
  public void write(HttpServletResponse response, Writer writer, Object value) {
    try {
      writer.write(gson.toJson(value));
    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }
}

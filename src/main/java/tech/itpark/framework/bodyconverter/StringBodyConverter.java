package tech.itpark.framework.bodyconverter;

import lombok.RequiredArgsConstructor;
import tech.itpark.project_delivery_web.exception.ConversionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@RequiredArgsConstructor
// option -> r -> i -> enter
public class StringBodyConverter implements BodyConverter {

  @Override
  public boolean canRead(String contentType, Class<?> clazz) {
    return clazz.isAssignableFrom(String.class);
  }

  @Override
  public boolean canWrite(String contentType, Class<?> clazz) {
    return clazz.isAssignableFrom(String.class);
  }

  @Override
  public <T> T read(HttpServletRequest request, Reader reader, Class<T> clazz) {
    try {
      // TODO: efficiency
      final var writer = new StringWriter();
      reader.transferTo(writer);
      return (T) writer.toString();
    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }

  @Override
  public void write(HttpServletResponse response, Writer writer, Object value) {
    try {
      writer.write((String) value);
    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }
}

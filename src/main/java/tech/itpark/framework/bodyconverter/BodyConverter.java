package tech.itpark.framework.bodyconverter;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Reader;
import java.io.Writer;

public interface BodyConverter {
  boolean canRead(String contentType, Class<?> clazz);

  boolean canWrite(String contentType, Class<?> clazz);

  <T> T read(HttpServletRequest request, Reader reader, Class<T> clazz);

  void write(HttpServletResponse response, Writer writer, Object value);
}

package tech.itpark.project_delivery_web.framework.bodyconverter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Reader;
import java.io.Writer;

public interface BodyConverter {
  boolean canRead(String contentType, Class<?> clazz);

  boolean canWrite(String contentType, Class<?> clazz);

  <T> T read(HttpServletRequest request, Reader reader, Class<T> clazz);

  void write(HttpServletResponse response, Writer writer, Object value);
}

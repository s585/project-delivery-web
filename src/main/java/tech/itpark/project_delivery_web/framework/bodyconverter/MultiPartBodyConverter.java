package tech.itpark.project_delivery_web.framework.bodyconverter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.itpark.app.exception.ConversionException;
import tech.itpark.framework.http.ContentTypes;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class MultiPartBodyConverter implements BodyConverter{
    @Override
    public boolean canRead(String contentType, Class<?> clazz) {
        return contentType != null && ContentTypes.MULTIPART_FORM_DATA.startsWith(ContentTypes.MULTIPART_FORM_DATA);
    }

    @Override
    public boolean canWrite(String contentType, Class<?> clazz) {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T read(HttpServletRequest request, Reader reader, Class<T> clazz) {
        try {
            return (T) request.getParts();
        } catch (IOException | ServletException e) {
           throw new ConversionException(e);
        }
    }

    @Override
    public void write(HttpServletResponse response, Writer writer, Object value) {
        throw new UnsupportedOperationException();
    }
}

package tech.itpark.project_delivery_web.exception;

import javax.servlet.http.HttpServletResponse;


public interface ExceptionHandler {

    void handle(Exception e, HttpServletResponse response);
}

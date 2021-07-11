package tech.itpark.project_delivery_web.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.RequestResponseReaderWriter;
import tech.itpark.project_delivery_web.controller.exception_handler.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Component
public class GeneralExceptionHandler implements ExceptionHandler {

    RequestResponseReaderWriter rw;

    @Autowired
    public void setRw(RequestResponseReaderWriter rw) {
        this.rw = rw;
    }

    @Override
    public void handle(Exception e, HttpServletResponse response) {
        final ResponseBody responseBody = new ResponseBody(e.getMessage());
        rw.write(responseBody, ContentTypes.APPLICATION_JSON, response);
    }
}
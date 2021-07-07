package tech.itpark.framework.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.util.AntPathMatcher;
import tech.itpark.framework.filter.CustomFilter;
import tech.itpark.framework.http.*;
import tech.itpark.project_delivery_web.exception.ExceptionHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FrontController extends HttpServlet {

    private final Handler notFoundHandler = (request, response) -> response
            .error(404, "Not Found", ContentTypes.TEXT_PLAIN);
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private List<CustomFilter> customFilters;
    private Map<String, Map<String, Handler>> routes;
    private Map<String, ExceptionHandler> exceptionHandler;
    private RequestResponseReaderWriter rw;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext servletContext = getServletContext();
            ApplicationContext context = (ApplicationContext) servletContext.getAttribute("CONTEXT");
            rw = context.getBean(RequestResponseReaderWriter.class);
            routes = (Map<String, Map<String, Handler>>) context.getBean("routes");
            customFilters = (List<CustomFilter>) context.getBean("customFilters");
            exceptionHandler = (Map<String, ExceptionHandler>)context.getBean("handlers");
        } catch (Exception e) {
            throw new UnavailableException(e.getMessage());
        }
    }

    @Override // in multiple threads
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        customFilters.forEach(filter -> filter.doFilter(request, response));

        final var path = request.getServletPath();
        final var method = request.getMethod();
        try {
            Optional<String> key = routes.keySet().stream()
                    .filter(pattern -> pathMatcher.match(pattern, path))
                    .findFirst();

            if (key.isPresent())
                Optional.of(routes.get(key.get()))
                        .map(route -> {
                            parsePathVariables(key.get(), request);
                            return route.get(method);
                        }).get().handle(new ServerRequest(request, rw), new ServerResponse(response, rw));
            else
                notFoundHandler.handle(new ServerRequest(request, rw), new ServerResponse(response, rw));
        } catch (Exception e) {
//            e.printStackTrace();
            final ExceptionHandler exceptionHandler = this.exceptionHandler.get(e.getClass().getSimpleName());
            exceptionHandler.handle(e, response);
        }

    }

    private void parsePathVariables(String key, ServletRequest request) {
        Map<String, String> params = pathMatcher
                .extractUriTemplateVariables(key, ((HttpServletRequest) request).getServletPath());

        params.forEach(request::setAttribute);
    }
}

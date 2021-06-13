package tech.itpark.framework.servlet;

import org.springframework.context.ApplicationContext;
import tech.itpark.framework.filter.CustomFilter;
import tech.itpark.framework.http.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FrontController extends HttpServlet {

    private List<CustomFilter> customFilters;
    private Map<String, Map<String, Handler>> routes;
    private RequestResponseReaderWriter rw;
    private final Handler notFoundHandler = (request, response) -> response
            .error(404, "Not Found", ContentTypes.TEXT_PLAIN);

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext servletContext = getServletContext();
            ApplicationContext context = (ApplicationContext) servletContext.getAttribute("CONTEXT");
            rw = context.getBean(RequestResponseReaderWriter.class);
            routes = (Map<String, Map<String, Handler>>) context.getBean("routes");
            customFilters = (List<CustomFilter>) context.getBean("customFilters");
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
            Optional.ofNullable(routes.get(path))
                    .map(o -> o.get(method))
                    .orElse(notFoundHandler)
                    .handle(new ServerRequest(request, rw), new ServerResponse(response, rw));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

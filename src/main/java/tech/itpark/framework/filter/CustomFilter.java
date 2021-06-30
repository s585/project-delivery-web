package tech.itpark.framework.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * A class implements the CustomFilter interface to indicate that this is a custom request filter.
 */
public interface CustomFilter {

    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse);
}

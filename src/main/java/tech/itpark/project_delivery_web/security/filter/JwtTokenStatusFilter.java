package tech.itpark.project_delivery_web.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import tech.itpark.framework.filter.CustomFilter;
import tech.itpark.project_delivery_web.security.exception.JwtTokenIsNotActiveException;
import tech.itpark.project_delivery_web.service.token.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtTokenStatusFilter extends GenericFilterBean implements CustomFilter {

    private static final String ERROR_MESSAGE = "Token is not valid";

    private JwtTokenService jwtTokenService;

    @Autowired
    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        doFilter(servletRequest, servletResponse);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String token = httpRequest.getHeader("Authorization");
        if (Objects.nonNull(token) && !jwtTokenService.isActive(jwtTokenService.parseToken(token))) {
            throw new JwtTokenIsNotActiveException(ERROR_MESSAGE);
        }
    }
}

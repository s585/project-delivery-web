package tech.itpark.project_delivery_web.security.filter;

import lombok.AllArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;
import tech.itpark.project_delivery_web.security.exeption.JwtTokenIsNotActiveException;
import tech.itpark.project_delivery_web.service.token.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
public class JwtTokenStatusFilter extends GenericFilterBean {

    private static final String ERROR_MESSAGE = "Token is not valid";
    private final JwtTokenService jwtTokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String token = httpRequest.getHeader("Authorization");
        if (Objects.nonNull(token) && !jwtTokenService.isActive(jwtTokenService.parseToken(token))) {
            throw new JwtTokenIsNotActiveException(ERROR_MESSAGE);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

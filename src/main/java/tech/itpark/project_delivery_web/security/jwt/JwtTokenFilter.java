package tech.itpark.project_delivery_web.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter for pulling a token from a request
 * {@link JwtTokenProvider#resolveToken(HttpServletRequest)}
 * and set Authentication in SecurityContext
 * {@link SecurityContext#setAuthentication(Authentication)}.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public void filter(ServletRequest request, ServletResponse response) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("token", token);
            }
        }
    }
}

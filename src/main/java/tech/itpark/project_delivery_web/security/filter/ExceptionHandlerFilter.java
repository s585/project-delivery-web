package tech.itpark.project_delivery_web.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.itpark.project_delivery_web.security.exeption.JwtAuthenticationException;
import tech.itpark.project_delivery_web.security.exeption.JwtTokenIsNotActiveException;
import tech.itpark.project_delivery_web.security.exeption.JwtTokenNotFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private static final String TOKEN_IS_EXPIRED_OR_INVALID = "Token is expired or invalid. Login please";

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException | JwtTokenNotFoundException | JwtTokenIsNotActiveException e) {
            errorDetails.put("message", TOKEN_IS_EXPIRED_OR_INVALID);
            errorDetails.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getWriter(), errorDetails);
        }
    }
}

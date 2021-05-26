package tech.itpark.project_delivery_web.security.filter;

import andersen.rnm.security.exception.JwtAuthenticationException;
import andersen.rnm.security.exception.JwtTokenIsNotActiveException;
import andersen.rnm.security.exception.JwtTokenNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


/**
 * Фильтр для обработки JwtAuthenticationException и JwtTokenIsNotActiveException при обработке запросов
 */
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private static final String TOKEN_IS_EXPIRED_OR_INVALID = "Token is expired or invalid. Login please";

    /**
     * @param request     объект запроса
     * @param response    объект ответа
     * @param filterChain цепочка фильтров
     * @throws ServletException when processing errors
     * @throws IOException      when processing errors
     * @descr метод, который при падении JwtAuthenticationException и JwtTokenIsNotActiveException заканчивает
     * обработку запроса и отправляет сообщения с описанием ошибки
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException | JwtTokenNotFoundException | JwtTokenIsNotActiveException e) {
            log.warn("Exception: {}. Message:{}", e.getClass().toString(), e.getMessage());
            errorDetails.put("message", TOKEN_IS_EXPIRED_OR_INVALID);
            errorDetails.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getWriter(), errorDetails);
        }
    }
}
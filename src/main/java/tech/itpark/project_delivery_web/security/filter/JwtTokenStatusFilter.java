package tech.itpark.project_delivery_web.security.filter;

import andersen.rnm.security.exception.JwtTokenIsNotActiveException;
import andersen.rnm.service.jwt_token.JwtTokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @descr фильтр для проверки состояния токена
 */
@AllArgsConstructor
public class JwtTokenStatusFilter extends GenericFilterBean {

    private static final String ERROR_MESSAGE = "Token is not valid";
    private final JwtTokenService jwtTokenService;

    /**
     * @param servletRequest  объект запроса
     * @param servletResponse объект ответа
     * @param filterChain     цепочка фильтров
     * @throws IOException when processing errors
     * @throws ServletException when processing errors
     * @throws JwtTokenIsNotActiveException, если токен, полученный из заголовка запроса неактивный
     * @descr метод, осуществляющий фильтрацию запросов. Если токен из заголовка активный либо нет информации о нём,
     * то продолжается дальнейшая обработка запроса.
     */
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

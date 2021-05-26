package tech.itpark.project_delivery_web.security.authentication;

import andersen.rnm.service.authentication.UserAuthenticationDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserAuthenticationDetailsService userAuthenticationDetailsService;
    private final AuthenticationFailureHandler handler;

    /**
     * @param event - объект, содержащий информацию об аутентификации пользователя.
     * @description Метод производит обработку события при удачной аутентификации пользователя.
     * Из объекта event получает username пользователя и передает его в метод resetFailedAttempts, возвращающий boolean,
     * который затем будет передан методу обработчика authenticationFailed(boolean allowed). Если boolean == true -
     * пользователь успешно авторизован, а вся информацию о неудачных попытках аутентификации удаляется из базы.
     * Если boolean == false, то аккаунт пользователя заблокирован и будет выброшено исключение LockedException;
     */
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        handler.authenticationFailed(userAuthenticationDetailsService.resetFailedAttempts(username));
    }
}

package tech.itpark.project_delivery_web.security.authentication;

import andersen.rnm.service.authentication.UserAuthenticationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private UserAuthenticationDetailsService userAuthenticationDetailsService;
    private AuthenticationFailureHandler handler;

    @Autowired
    public void setUserAuthenticationDetailsService(UserAuthenticationDetailsService userAuthenticationDetailsService) {
        this.userAuthenticationDetailsService = userAuthenticationDetailsService;
    }

    @Autowired
    public void setHandler(AuthenticationFailureHandler handler) {
        this.handler = handler;
    }

    @Value("${security.authentication.max-failed-attempts}")
    private int maxFailedAttempts;

    /**
     * @param event - объект, содержащий информацию об аутентификации пользователя.
     * @description Метод производит обработку события при неудачной аутентификации пользователя.
     * Из объекта event получают username пользователя.Далее вызывается метод increaseFailedAttempts(username),
     * озвращающий boolean, который будет передан обработчику в метод authenticationFailed(boolean allowed).
     * Если значение boolean == true, то аккаунт пользователя не будет заблокирован, в случае boolean == false -
     * аккаунт пользователя будет заблокирован и будет выброшено LockedException исключение
     */
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        if (userAuthenticationDetailsService.increaseFailedAttempts(username)) {
            //Можно использовать get() без проверки, т. к. в increaseFailedAttempts(username)
            //при необходимости создаётся UserAuthenticationDetails
            int count = userAuthenticationDetailsService.findByEmail(username).get().getFailedAttemptCount();
            throw new BadCredentialsException("Неверный пароль. Осталось попыток входа: " + (maxFailedAttempts - count));
        }
        handler.authenticationFailed(false);
    }
}

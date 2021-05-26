package tech.itpark.project_delivery_web.security.authentication;

import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureHandler {

    private static final String EXCEPTION_MESSAGE = "Ваша учетная запись заблокирована";

    /**
     * @param allowed - boolean.
     * @description Метод производит проверку параметра allowed. Если allowed == false - будет выброшено LockedException.
     * Если allowed == true, никаких действий не будет производится.
     */
    public void authenticationFailed(boolean allowed) {
        if (!allowed) {
            throw new LockedException(EXCEPTION_MESSAGE);
        }
    }
}

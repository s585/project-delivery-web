package tech.itpark.project_delivery_web.configuration;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.itpark.framework.bodyconverter.BodyConverter;
import tech.itpark.framework.bodyconverter.GsonBodyConverter;
import tech.itpark.framework.bodyconverter.MultiPartBodyConverter;
import tech.itpark.framework.bodyconverter.StringBodyConverter;
import tech.itpark.framework.crypto.PasswordHasher;
import tech.itpark.framework.crypto.PasswordHasherDefaultImpl;
import tech.itpark.framework.crypto.TokenGenerator;
import tech.itpark.framework.crypto.TokenGeneratorDefaultImpl;
import tech.itpark.framework.http.Handler;
import tech.itpark.framework.http.Methods;
import tech.itpark.project_delivery_web.controller.GroupController;
import tech.itpark.project_delivery_web.controller.MediaController;
import tech.itpark.project_delivery_web.controller.UserController;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Configuration
public class AppConfiguration {
    @Bean
    public DataSource dataSource() throws NamingException {
        // Spring way:
        // return (DataSource) new JndiTemplate().lookup("java:/comp/env/jdbc/db");
        final var cxt = new InitialContext();
        return (DataSource) cxt.lookup("java:/comp/env/jdbc/db");
    }

    @Bean
    public PasswordHasher passwordHasher(MessageDigest digest) {
        return new PasswordHasherDefaultImpl(digest);
    }

    @Bean
    public MessageDigest messageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256");
    }

    @Bean
    public TokenGenerator tokenGenerator() {
        return new TokenGeneratorDefaultImpl();
    }

    @Bean
    public List<BodyConverter> bodyConverters() {
        return List.of(
                new GsonBodyConverter(new Gson()), new StringBodyConverter(), new MultiPartBodyConverter());
    }

    @Bean
    public Map<String, Map<String, Handler>> routes(UserController userCtrl, GroupController groupCtrl, MediaController mediaCtrl) {
        return Map.of(
                "/api/auth/registration", Map.of(
                        Methods.POST, userCtrl::register
                ),
                "/api/auth/login", Map.of(
                        Methods.POST, userCtrl::login
                ),
                "/api/auth/reset-password", Map.of(
                        Methods.POST, userCtrl::passwordReset
                ),
                "/api/users/all", Map.of(
                        Methods.GET, userCtrl::getAll
                ),
                "/api/groups/save", Map.of(
                        Methods.POST, groupCtrl::save
                ),
                "api/media", Map.of(
                        Methods.POST, mediaCtrl::save
                )
        );
    }
}

package tech.itpark.project_delivery_web.configuration;

import com.google.gson.Gson;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
import tech.itpark.project_delivery_web.controller.MediaController;
import tech.itpark.project_delivery_web.controller.UserController;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
        "tech.itpark.project_delivery_web.repository"
})
@ComponentScan(basePackages = "tech.itpark.project_delivery_web")
public class AppConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/db");
        dataSource.setUsername("app");
        dataSource.setPassword("pass");
        dataSource.setDriverClassName(Driver.class.getName());
        return dataSource;
//        Spring way:
//        return (DataSource) new JndiTemplate().lookup("java:/comp/env/jdbc/db");
//        final var cxt = new InitialContext();
//        return (DataSource) cxt.lookup("java:/comp/env/jdbc/db");
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("tech.itpark.project_delivery_web.model");

        Properties jpaProperties = new Properties();

        jpaProperties.put(Environment.DIALECT, PostgreSQL10Dialect.class.getName());
        jpaProperties.put(Environment.HBM2DDL_AUTO, "update");
        jpaProperties.put(Environment.SHOW_SQL, true);
        jpaProperties.put(Environment.FORMAT_SQL, true);

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
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
    public Map<String, Map<String, Handler>> routes(UserController userCtrl, MediaController mediaCtrl) {
//        return Map.of(
//                "/api/auth/registration", Map.of(
//                        Methods.POST, userCtrl::register
//                ),
//                "/api/auth/login", Map.of(
//                        Methods.POST, userCtrl::login
//                ),
//                "/api/auth/reset-password", Map.of(
//                        Methods.POST, userCtrl::passwordReset
//                ),
//                "/api/users/all", Map.of(
//                        Methods.GET, userCtrl::getAll
//                ),
//                "api/media", Map.of(
//                        Methods.POST, mediaCtrl::save
//                )
//        );
        return null;
    }
}

package tech.itpark.project_delivery_web.configuration;

import com.google.gson.Gson;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
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
import tech.itpark.project_delivery_web.controller.AuthenticationController;
import tech.itpark.project_delivery_web.controller.MediaController;
import tech.itpark.project_delivery_web.controller.UserController;
import tech.itpark.project_delivery_web.repository.JwtTokenRepository;
import tech.itpark.project_delivery_web.service.token.JwtTokenService;
import tech.itpark.project_delivery_web.service.token.JwtTokenServiceImpl;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
        "tech.itpark.project_delivery_web.repository"
})
@ComponentScan(basePackages = "tech.itpark.project_delivery_web")
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE)
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE);
        return mapper;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource basic = new BasicDataSource();
        basic.setMinIdle(10);
        basic.setMaxIdle(25);
        basic.setMaxTotal(50);
        basic.setMaxOpenPreparedStatements(100);
        basic.setDriverClassName(Driver.class.getName());
        basic.setUsername("app");
        basic.setPassword("pass");
        basic.setUrl("jdbc:postgresql://localhost:5432/db");

        return basic;
    }

    @Bean
    @DependsOn("transactionManager")
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("data.sql"));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);

        resourceDatabasePopulator.execute(dataSource);

        return dataSourceInitializer;
    }

    @Bean
    public JwtTokenService jwtTokenService(JwtTokenRepository repository) {
        return new JwtTokenServiceImpl(repository);
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
    public Map<String, Map<String, Handler>> routes(UserController userCtrl, MediaController mediaCtrl,
                                                    AuthenticationController authCtrl) {
        return Map.of(
                "/api/users/all", Map.of(Methods.GET, userCtrl::getAll),
                "/api/users", Map.of(Methods.GET, userCtrl::getById),
                "/api/login", Map.of(Methods.POST, authCtrl::login));
    }
}

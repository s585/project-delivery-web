package tech.itpark.project_delivery_web.configuration;

import com.google.gson.Gson;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tech.itpark.framework.bodyconverter.BodyConverter;
import tech.itpark.framework.bodyconverter.GsonBodyConverter;
import tech.itpark.framework.bodyconverter.MultiPartBodyConverter;
import tech.itpark.framework.bodyconverter.StringBodyConverter;
import tech.itpark.framework.crypto.PasswordHasher;
import tech.itpark.framework.crypto.PasswordHasherDefaultImpl;
import tech.itpark.framework.crypto.TokenGenerator;
import tech.itpark.framework.crypto.TokenGeneratorDefaultImpl;
import tech.itpark.framework.filter.CustomFilter;
import tech.itpark.framework.http.Handler;
import tech.itpark.framework.http.Methods;
import tech.itpark.project_delivery_web.controller.*;
import tech.itpark.project_delivery_web.exception.BadCredentialsExceptionHandler;
import tech.itpark.project_delivery_web.exception.ExceptionHandler;
import tech.itpark.project_delivery_web.exception.GeneralExceptionHandler;
import tech.itpark.project_delivery_web.security.filter.JwtTokenStatusFilter;
import tech.itpark.project_delivery_web.security.jwt.JwtTokenFilter;

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
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;
    @Value("${datasource.driver}")
    private String driver;

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
        basic.setUrl(url);
        basic.setUsername(username);
        basic.setPassword(password);
        basic.setDriverClassName(driver);

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
                                                    AuthenticationController authCtrl, CartController cartCtrl,
                                                    DelivererController delivererCtrl, OrderController orderCtrl,
                                                    ProductController productCtrl, VendorController vendorCtrl) {
        return Map.ofEntries(
                Map.entry("/api/auth/register", Map.of(Methods.POST, userCtrl::register)),
                Map.entry("/api/auth/vendors/register", Map.of(Methods.POST, vendorCtrl::register)),
                Map.entry("/api/auth/login", Map.of(Methods.POST, authCtrl::login)),
                Map.entry("/api/auth/reset", Map.of(Methods.PUT, authCtrl::recoverPassword)),
                Map.entry("/api/users", Map.of(Methods.GET, userCtrl::getAll)),
                Map.entry("/api/users/{id}", Map.of(Methods.GET, userCtrl::getById,
                        Methods.PUT, userCtrl::update,
                        Methods.PATCH, userCtrl::setStatusActiveById,
                        Methods.DELETE, userCtrl::deleteById)),
                Map.entry("/api/products", Map.of(Methods.POST, productCtrl::save)),
                Map.entry("/api/products/vendors/{id}", Map.of(Methods.GET, productCtrl::getAllByVendorId)),
                Map.entry("/api/products/{id}", Map.of(Methods.GET, productCtrl::getById,
                        Methods.PUT, productCtrl::update,
                        Methods.DELETE, productCtrl::deleteById)),
                Map.entry("/api/vendors", Map.of(Methods.GET, vendorCtrl::getAll)),
                Map.entry("/api/vendors/{id}", Map.of(Methods.GET, vendorCtrl::getById,
                        Methods.PUT, vendorCtrl::update,
                        Methods.DELETE, vendorCtrl::deleteById)),
                Map.entry("/api/deliverers", Map.of(Methods.POST, delivererCtrl::save,
                        Methods.GET, delivererCtrl::getAll)),
                Map.entry("/api/deliverers/{id}", Map.of(Methods.GET, vendorCtrl::getById,
                        Methods.PUT, vendorCtrl::update,
                        Methods.DELETE, vendorCtrl::deleteById)),
                Map.entry("/api/carts", Map.of(Methods.POST, cartCtrl::save)),
                Map.entry("/api/carts/checkout/{id}", Map.of(Methods.POST, cartCtrl::checkout)),
                Map.entry("/api/carts/owner/{id}", Map.of(Methods.GET, cartCtrl::getByOwnerId)),
                Map.entry("/api/carts/{id}", Map.of(Methods.GET, cartCtrl::getById,
                        Methods.PUT, cartCtrl::update,
                        Methods.DELETE, cartCtrl::deleteById)),
                Map.entry("/api/orders/owner/{id}", Map.of(Methods.GET, orderCtrl::getByOwnerId)),
                Map.entry("/api/orders/vendor/{id}", Map.of(Methods.GET, orderCtrl::getByVendorId)),
                Map.entry("/api/orders/{id}", Map.of(Methods.GET, orderCtrl::getById,
                        Methods.PUT, orderCtrl::update,
                        Methods.DELETE, orderCtrl::deleteById))
                );
    }

    @Bean
    public List<CustomFilter> customFilters(JwtTokenStatusFilter jwtTokenStatusFilter,
                                            JwtTokenFilter jwtTokenFilter) {
        return List.of(jwtTokenStatusFilter, jwtTokenFilter);
    }

    @Bean
    public Map<String, ExceptionHandler> handlers(BadCredentialsExceptionHandler badCredentialsExceptionHandler,
                                                  GeneralExceptionHandler generalExceptionHandler) {
        return Map.ofEntries(
                Map.entry(BadCredentialsException.class.getSimpleName(), badCredentialsExceptionHandler),
                Map.entry(GeneralExceptionHandler.class.getSimpleName(), generalExceptionHandler));
    }
}

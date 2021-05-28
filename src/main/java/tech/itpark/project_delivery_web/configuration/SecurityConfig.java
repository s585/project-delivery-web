package tech.itpark.project_delivery_web.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.itpark.project_delivery_web.security.filter.ExceptionHandlerFilter;
import tech.itpark.project_delivery_web.security.filter.JwtTokenStatusFilter;
import tech.itpark.project_delivery_web.security.jwt.JwtConfigurer;
import tech.itpark.project_delivery_web.security.jwt.JwtTokenProvider;
import tech.itpark.project_delivery_web.service.token.JwtTokenService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                //Config for swagger
//                .antMatchers("/v2/api-docs",
//                        "/swagger-resources",
//                        "/swagger-resources/**",
//                        "/configuration/ui",
//                        "/configuration/security",
//                        "/swagger-ui.html",
//                        "/webjars/**",
//                        // -- Swagger UI v3 (OpenAPI)
//                        "/v3/api-docs/**",
//                        "/swagger-ui/**").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/...").permitAll() //TODO указать урл логаута
                .antMatchers("/**").fullyAuthenticated()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenStatusFilter(jwtTokenService), UsernamePasswordAuthenticationFilter.class)
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }
}

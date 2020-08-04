package yte.etkinlikyonetim.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yte.etkinlikyonetim.service.CustomUserDetailsManager;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
/**
    private final CustomUserDetailsManager customUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
*/
    @Override
    protected void configure(final HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll();
                //.antMatchers("/login").permitAll()
                //.antMatchers("/home/**").permitAll()
                //.antMatchers("/etkinlikler/**").permitAll()
                //.antMatchers("/admin/**").authenticated()
                //.and()
                //.formLogin()
                //.loginPage("/login");
                //.httpBasic();
    }

/**

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsManager);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
*/
}

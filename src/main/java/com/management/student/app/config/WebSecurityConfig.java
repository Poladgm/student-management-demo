package com.management.student.app.config;


import com.management.student.app.security.JwtAuthenticationFilter;
import com.management.student.app.security.TokenProvider;
import com.management.student.app.security.UnauthorizedEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final TokenProvider jwtTokenUtil;

    public WebSecurityConfig(UnauthorizedEntryPoint unauthorizedEntryPoint, TokenProvider jwtTokenUtil) {
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .headers ()
                .xssProtection ()
                .and ()
                .contentSecurityPolicy ("script-src 'self'");

        http.cors ()
                .and ()
                .csrf ().csrfTokenRepository (CookieCsrfTokenRepository.withHttpOnlyFalse ())
                .and ()
                .sessionManagement ().sessionCreationPolicy (SessionCreationPolicy.STATELESS)
                .and ().requestMatchers ()
                .antMatchers ("/student-management/V1.0/students/**", "/student-management/V1.0/courses/**")
                .and ()
                .authorizeRequests ()
                .anyRequest ().authenticated ().and ().exceptionHandling ().authenticationEntryPoint (unauthorizedEntryPoint);
        http.addFilterBefore (authenticationTokenFilterBean (), UsernamePasswordAuthenticationFilter.class);
        http.headers ().frameOptions ().disable ();

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring ().antMatchers ("/h2-console/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html/**", "/webjars/**");
    }

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder ();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean ();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationFilter (jwtTokenUtil);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService (userDetailsService).passwordEncoder (encoder ());
    }
}
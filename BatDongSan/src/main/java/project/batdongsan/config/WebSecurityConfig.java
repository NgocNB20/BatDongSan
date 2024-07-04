package project.batdongsan.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.*;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import project.batdongsan.component.ApplicationAuditAware;
import project.batdongsan.component.UserDetailsServiceApp;
import project.batdongsan.component.UserLoginFailureHandler;
import project.batdongsan.component.UserLoginSuccessHandler;
import project.batdongsan.model.entity.PersistentLogin;
import project.batdongsan.repositoty.JpaPersistentRepository;
import project.batdongsan.repositoty.RememberMeTokenRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserLoginFailureHandler loginFailureHandler;
    private final UserLoginSuccessHandler loginSuccessHandler;
    private final UserDetailsServiceApp userDetailsServiceApp;
    private final RememberMeTokenRepository rememberMeTokenRepository;

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    private AuthenticationEntryPoint authEntryPoint;

    public WebSecurityConfig(UserLoginFailureHandler loginFailureHandler, UserLoginSuccessHandler loginSuccessHandler, UserDetailsServiceApp userDetailsServiceApp, RememberMeTokenRepository rememberMeTokenRepository) {
        this.loginFailureHandler = loginFailureHandler;
        this.loginSuccessHandler = loginSuccessHandler;
        this.userDetailsServiceApp = userDetailsServiceApp;
        this.rememberMeTokenRepository = rememberMeTokenRepository;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("front/**").hasRole("USER")
                        .requestMatchers("/login","/forgot_password","/forgot","/process_send_mail","/reset_password").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .failureUrl("/login?error=true")
                        .failureHandler(loginFailureHandler)
                        .successHandler(loginSuccessHandler)
                )
                .logout((logout) -> logout
                        .logoutUrl("/perform_logout")
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler(logoutSuccessHandler())
                        .logoutRequestMatcher(new AntPathRequestMatcher("/perform_logout","GET"))
                        .permitAll())
                .rememberMe(
                        rememberKey->rememberKey
                        .rememberMeServices(rememberMeServices())
                        .userDetailsService(userDetailsServiceApp)
                                .key("yourSecretKey")
                )
                .exceptionHandling(exception->exception.authenticationEntryPoint(authEntryPoint));
//              .exceptionHandling((exception)-> exception.accessDeniedPage("/error"));
        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(RememberMeTokenRepository rememberMeTokenRepository) {
        return new JpaPersistentRepository(rememberMeTokenRepository);
    }

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices() {
        PersistentTokenBasedRememberMeServices rememberMeServices =
        new PersistentTokenBasedRememberMeServices("yourSecretKey", userDetailsServiceApp, persistentTokenRepository(rememberMeTokenRepository));
        rememberMeServices.setParameter("remember_me");
        rememberMeServices.setCookieName("remember_me_name");
        rememberMeServices.setTokenValiditySeconds(7 * 24 * 60 * 60);
        return rememberMeServices;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
            @Bean
            public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                String username = authentication.getName();
                List<PersistentLogin> persistentLogins = rememberMeTokenRepository.findByUsername(username);
                rememberMeTokenRepository.deleteAll(persistentLogins);
                response.sendRedirect("/login");
            }
        };
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userDetailsServiceApp;
//    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder encoder) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }


}

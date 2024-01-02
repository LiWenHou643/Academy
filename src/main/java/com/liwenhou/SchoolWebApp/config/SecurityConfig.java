package com.liwenhou.SchoolWebApp.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.ignoringRequestMatchers("/saveMsg")
                                            .ignoringRequestMatchers("/public/**")
                )
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/dashboard").authenticated()
                .requestMatchers("/home").permitAll()
                .requestMatchers("/holidays/**").permitAll()
                .requestMatchers("/contact").permitAll()
                .requestMatchers("/saveMsg").permitAll()
                .requestMatchers("/courses").permitAll()
                .requestMatchers("/about").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/logout").permitAll()
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/displayMessages").hasRole("ADMIN")
                .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .requestMatchers("/assets/**").permitAll());

        http.formLogin(loginConfigurer -> loginConfigurer
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .failureUrl("/login?error=true")
                .permitAll());
        http.logout(logoutConfigurer -> logoutConfigurer
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("54321")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}

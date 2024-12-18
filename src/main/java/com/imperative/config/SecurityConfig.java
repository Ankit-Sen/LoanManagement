package com.imperative.config;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration  
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

     @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests((requests) -> requests.anyRequest()
                    .authenticated());
            http.sessionManagement(session 
            -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
			http.formLogin(withDefaults());
			http.httpBasic(withDefaults());
            http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
            http.csrf(AbstractHttpConfigurer::disable);//csrf disabled to get it to work
			return http.build();
		}
        
     @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user= User.withUsername("user").password(passwordEncoder().encode("pass")).roles("USER").build();
        UserDetails admin= User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user,admin);
    }   

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

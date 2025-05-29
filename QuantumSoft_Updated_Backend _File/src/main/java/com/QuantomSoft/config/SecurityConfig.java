package com.QuantomSoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/register", "/api/admin/login","/api/contacts/save","/api/forms/form/saveForm","/api/jobs/job/saveJobs","/api/News/SaveNews","/api/admin/update/{adminId}","/api/admin/delete/{adminId}","/api/admin/getAdminById/{adminId}","/api/admin/JobPost",
                                "/api/admin/form/getAllForms","/api/admin/forms/{formId}","/api/admin/job/getAllJobs","/api/contacts/all","/api/contacts/getById/{id}","/api/contacts/Update/{id}",
                                "/api/contacts/Delete/{id}","/api/jobs/job/getAllJobs","/api/jobs/search","/api/jobs/job/updateJob/{id}","/api/jobs/job/deleteJobById/{id}",
                                "/api/News/getNewsById/{id}","/api/News/allnews","/api/News/getNewsImage/{id}","/api/News/update/{newsId}","/api/News/delete/{newsId}").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

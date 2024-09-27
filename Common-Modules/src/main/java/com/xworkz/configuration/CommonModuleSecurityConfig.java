package com.xworkz.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class CommonModuleSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                .antMatchers("/", "/login", "/index","/resource/**").permitAll() // Public access
////                .antMatchers("/admin/**").hasRole("ADMIN") // Restricted to ADMIN role
////                .antMatchers("/departmentAdmin/**").hasRole("DEPARTMENT_ADMIN") // Restricted to DEPARTMENT_ADMIN role
////                .antMatchers("/employee/**").hasRole("EMPLOYEE") // Restricted to EMPLOYEE role
////                .antMatchers("/user/**").hasRole("USER") // Restricted to USER role
////                .anyRequest().authenticated() // All other requests require authentication
////                .and()
////              .formLogin()
//////                .loginPage("/login") // Custom login page
////                .permitAll() // Allow everyone to see the login page
////              .and()
////                .logout()
////                .permitAll(); // Allow everyone to log out
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated() // All requests require authentication
//                .and()
//                .formLogin()
//                .permitAll();
////        http.authorizeRequests()
////                .antMatchers("/**").authenticated();
//
//        return http.build();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("gwidgets").password("gwidgets").roles("ADMIN");
    }


}

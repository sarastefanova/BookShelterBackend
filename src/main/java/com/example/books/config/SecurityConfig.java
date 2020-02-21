package com.example.books.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userServiceDetailsImpl")
    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private BooksAuthenticationProvider authenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception
    {
        http.cors().and()
                //starts authorizing configurations.
                .authorizeRequests()
                //ignoring the guest's urls...
                .antMatchers("/resources/**", "/error", "/user/**", "/books","/books/**","/author","/author/**").permitAll()
                //authenticate all remaining URLs.
                .anyRequest().fullyAuthenticated()
                .and()
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout", "POST"))
                //login form
                .and()
                .formLogin().loginPage("/user/login").and()
                //enable basic header authentication.
                .httpBasic().and()
                //cross-side request forgery.
                .csrf().disable();
//                .and()
//                .formLogin()
//                .loginPage("/login-page")
//                .defaultSuccessUrl("/device/")
//                .failureUrl("/login-page")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout").permitAll()
//                .logoutSuccessUrl("/login-page");

    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
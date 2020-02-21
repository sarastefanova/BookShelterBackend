package com.example.books.config;

import com.example.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class BooksAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();



        if ("".equals(username) || "".equals(password)) {
            throw new UsernameNotFoundException("Invalid credentials");
        }
        UserDetails user = userService.loadUserByUsername(username);
        if (user==null) {
            throw new UsernameNotFoundException("Username invalid");
        }

        boolean passwordsEqual = passwordEncoder.matches(password,user.getPassword());
        if (!passwordsEqual) {
            throw new UsernameNotFoundException("Password invalid");
        }

        return new UsernamePasswordAuthenticationToken(user,password,user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

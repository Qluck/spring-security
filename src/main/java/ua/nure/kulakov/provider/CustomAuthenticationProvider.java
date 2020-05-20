package ua.nure.kulakov.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ua.nure.kulakov.entity.dto.User;
import ua.nure.kulakov.service.UserService;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.getUserByUsernameAndPassword(String.valueOf(authentication.getPrincipal()),
                String.valueOf(authentication.getCredentials()));
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("USER")));
        }
        throw new AuthenticationServiceException("User doesn't exist");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

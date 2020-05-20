package ua.nure.kulakov.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.nure.kulakov.configuration.TokenResolver;
import ua.nure.kulakov.service.JwtAuthenticationService;

@Service
public class JwtAuthenticationServiceImpl implements JwtAuthenticationService {

    @Autowired
    @Qualifier("accessTokenResolverImpl")
    private TokenResolver tokenResolver;

    @Override
    public void authenticateByJwtToken(String token) {
        if (!StringUtils.isEmpty(token) && tokenResolver.isTokenValid(token)) {
            putValidTokenToContext(token);
        }
    }

    private void putValidTokenToContext(String token) {
        Authentication auth = tokenResolver.getAuthentication(token);
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            throw new RuntimeException("Token is not valid");
        }
    }
}

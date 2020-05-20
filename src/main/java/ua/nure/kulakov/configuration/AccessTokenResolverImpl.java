package ua.nure.kulakov.configuration;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AccessTokenResolverImpl extends AbstractTokenResolver implements TokenResolver {

    private static final String REQUEST_TOKEN_ATTRIBUTE_KEY = "Authorization";

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = parseToken(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private JwtUser parseToken(String token) {
        Claims claims = getClaimsBody(token);
        List<String> roles = (List<String>) claims.get("roles");

        return new JwtUser(Long.parseLong(claims.getSubject()),
                (String) claims.get("email"),
                mapToGrantedAuthorities(roles));
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(List<String> userRoles) {
        return userRoles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(REQUEST_TOKEN_ATTRIBUTE_KEY);
    }
}
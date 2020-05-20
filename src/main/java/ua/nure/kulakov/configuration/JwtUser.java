package ua.nure.kulakov.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@NoArgsConstructor
public class JwtUser implements UserDetails {

    private Long id;
    private String email;

    private Collection<? extends GrantedAuthority> roleList;

    public JwtUser(
            Long id,
            String email,
            Collection<? extends GrantedAuthority> roleList
    ) {
        this.id = id;
        this.email = email;
    }


    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
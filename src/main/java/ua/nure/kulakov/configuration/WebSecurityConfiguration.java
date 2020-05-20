package ua.nure.kulakov.configuration;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.nure.kulakov.provider.CustomAuthenticationProvider;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AccessTokenResolverImpl jwtAccessTokenResolverImpl;

    @Autowired
    private Gson gson;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    public WebSecurityConfiguration(@Qualifier("accessTokenResolverImpl") AccessTokenResolverImpl jwtAccessTokenResolverImpl) {
        this.jwtAccessTokenResolverImpl = jwtAccessTokenResolverImpl;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/sign-in")
                .loginProcessingUrl("/sign-in")
                .successHandler((request, response, authentication) -> {
                    response.setStatus(OK.value());
                    response.getWriter().write(gson.toJson(authentication));
                })
                .failureHandler((request, response, e) -> {
                    response.reset();
                    response.setStatus(UNAUTHORIZED.value());
                    response.getWriter().write(e.getMessage());
                })
                .and()
                .authorizeRequests()
                .antMatchers("/sign-in").permitAll()
                .antMatchers(HttpMethod.POST, "/admin/auth").permitAll()
                .and().csrf().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }
}

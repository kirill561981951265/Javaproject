package by.kir.spring_lr2.config;

import by.kir.spring_lr2.security.jwt.JwtTokenFilter;
import by.kir.spring_lr2.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/admin/**";
    private static final String DOCTOR_ENDPOINT = "/api/doctor/**";
    private static final String USER_ENDPOINT = "/api/user/**";
    private static final String AP_DOCTOR_ENDPOINT = "/api/appointment/doctor/**";
    private static final String AP_GET_ENDPOINT = "/api/appointment/get/**";

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(DOCTOR_ENDPOINT).hasRole("DOCTOR")
                .antMatchers(AP_DOCTOR_ENDPOINT).hasRole("DOCTOR")
                .antMatchers(USER_ENDPOINT).hasRole("USER")
                .antMatchers(AP_GET_ENDPOINT).authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(SC_UNAUTHORIZED))
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
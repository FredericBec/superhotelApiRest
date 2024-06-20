package fr.fms.superhotel.security;

import fr.fms.superhotel.dao.UserRepository;
import fr.fms.superhotel.entities.AppUser;
import fr.fms.superhotel.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String URL_HOTELS = "/hotels";
    private static final String URL_CITIES = "/cities";
    private static final String URL_HOTELS_ID = "/hotels/{id}";
    private static final String URL_CITIES_ID = "/cities/{id}";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String MANAGER_ROLE = "MANAGER";

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().cors()
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET, URL_HOTELS).permitAll()
                .antMatchers(HttpMethod.GET, URL_CITIES).permitAll()
                .antMatchers(HttpMethod.POST, URL_HOTELS).authenticated()
                .antMatchers(HttpMethod.POST, URL_HOTELS).hasAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, URL_HOTELS_ID).hasAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, URL_HOTELS_ID).hasAuthority(MANAGER_ROLE)
                .antMatchers(HttpMethod.DELETE, URL_HOTELS_ID).hasAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, "/photo/{id}").hasAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, URL_CITIES).hasAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, URL_CITIES_ID).hasAuthority(ADMIN_ROLE)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManagerBean()))
                .addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addExposedHeader(SecurityConstants.HEADER_STRING);
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

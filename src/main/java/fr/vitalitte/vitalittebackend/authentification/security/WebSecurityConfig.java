package fr.vitalitte.vitalittebackend.authentification.security;

import fr.vitalitte.vitalittebackend.authentification.jwt.AuthEntryPointJwt;
import fr.vitalitte.vitalittebackend.authentification.jwt.AuthTokenFilter;
import fr.vitalitte.vitalittebackend.user.usecase.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(eh -> eh.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(ahr -> ahr
//                                .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.POST).permitAll()
//                .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                                // pas sur ça ??? on a pas d'authentification à l'instant T
                                // .anyRequest().authenticated()
//                .requestMatchers(HttpMethod.GET,"/api/articles/**",
//                                                "/api/categories/**",
//                                                "/api/tags/**",
//                                                "/api/sponsors/**",
//                                                "/api/staffs/**",
//                                                "/api/players/**").permitAll()
//                //le User ne fait jamais ces GET en front
//                .requestMatchers(HttpMethod.GET,"/api/jobs/**",
//                                                "/api/positions/**",
//                                                "/api/teams/**").hasRole("ADMIN")
//                //ces méthodes ne sont que pour l'admin
                                .requestMatchers("/api/users/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/api/**").permitAll()
                );


        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

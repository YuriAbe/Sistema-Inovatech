package br.com.faculdadeinovatech.inovatech.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomAuthSuccessHandler successHandler;

    public SecurityConfig(CustomAuthSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Public pages
                        .requestMatchers(
                                "/",
                                "/produtos",
                                "/produtos/detalhe/**",
                                "/login",
                                "/inovatech",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/sitemap.xml",
                                "/robots.txt",
                                "/favicon.ico",
                                "/usuarios/criar",
                                "/usuarios/salvar",
                                "/usuario/formularioUsuario.html",
                                "/forgotPassword",
                                "/resetPassword",
                                "/changePassword",
                                "/updatePassword",
                                "/tokenExpired")
                        .permitAll()
                        // Admin-only pages
                        .requestMatchers(
                                "/home",
                                "/alunos/**",
                                "/cursos/**",
                                "/professores/**",
                                "/disciplinas/**",
                                "/categorias/**",
                                "/produtos/listar",
                                "/produtos/criar",
                                "/produtos/editar/**",
                                "/produtos/excluir/**",
                                "/produtos/salvar",
                                "/pedidos/criar",
                                "/pedidos/listar",
                                "/pedidos/excluir/**",
                                "/relatorios/**")
                        .hasRole("ADMIN")
                        // Cart - any authenticated user
                        .requestMatchers("/carrinho/**")
                        .authenticated()
                        // Everything else requires authentication
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler)
                        .permitAll())

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())

                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
package arquitectura.grupo19.api_gateway.config;

import arquitectura.grupo19.api_gateway.security.AuthotityConstant;
import arquitectura.grupo19.api_gateway.security.jwt.JwtFilter;
import arquitectura.grupo19.api_gateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig( TokenProvider tokenProvider ) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura la cadena de filtros de seguridad para gestionar la autenticación y autorización en la aplicación.
     *
     * @param http el objeto {@link HttpSecurity} proporcionado por Spring Security para personalizar las reglas de seguridad HTTP.
     * @return un {@link SecurityFilterChain} que contiene la configuración de seguridad de la aplicación.
     * @throws Exception si ocurre algún error al configurar la seguridad.
     */
    @Bean
    public SecurityFilterChain filterChain( final HttpSecurity http ) throws Exception {
        // Desactivar la protección CSRF (Cross-Site Request Forgery) ya que la aplicación no maneja formularios de inicio de sesión.
        http.csrf( AbstractHttpConfigurer::disable );

        // Configurar la política de sesión como STATELESS, para no mantener sesiones de usuario.
        http.sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );

        // Definir reglas de acceso para distintas rutas
        http
            .securityMatcher("/**" )
            .authorizeHttpRequests( auth -> auth
                    .requestMatchers(HttpMethod.POST, "/authenticate").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users").permitAll()
                    .requestMatchers( HttpMethod.POST,"/admins").hasAuthority( AuthotityConstant._ADMIN )//el orden va de más específica a menos específica
                    .requestMatchers( "/reports/**").hasAuthority( AuthotityConstant._MAINTENANCE )
                    .requestMatchers("/maps/**", "/trips/**", "/stops/**", "/scooters/**").hasAnyAuthority(AuthotityConstant._USER)
                    .anyRequest().authenticated()
            )
                //Habilita la autenticación básica HTTP
            .httpBasic( Customizer.withDefaults() )

                // Agrega un filtro personalizado (JwtFilter). Este filtro valida el JWT en las solicitudes entrantes para autenticar usuarios.
            .addFilterBefore( new JwtFilter( this.tokenProvider ), UsernamePasswordAuthenticationFilter.class );

        return http.build();
    }

}

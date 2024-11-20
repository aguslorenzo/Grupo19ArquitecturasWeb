package arquitectura.grupo19.api_gateway.services;

import arquitectura.grupo19.api_gateway.dto.LoginRequest;
import arquitectura.grupo19.api_gateway.dto.RegisterRequest;
import arquitectura.grupo19.api_gateway.entities.AuthUser;
import arquitectura.grupo19.api_gateway.entities.Role;
import arquitectura.grupo19.api_gateway.repositories.AuthUserRepository;
import arquitectura.grupo19.api_gateway.repositories.RoleRepository;
import arquitectura.grupo19.api_gateway.security.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthService(PasswordEncoder passwordEncoder, AuthUserRepository authUserRepository, JwtService jwtService, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authUserRepository = authUserRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user = authUserRepository.findUserEntityByUsername(request.getUsername());

        if(user == null) {
            return null;
        }

        String token = jwtService.getToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {
        Role roleFound = roleRepository.findByName(request.getRoles());

        if(roleFound == null) {
            return new AuthResponse("No existe el rol");
        }

        AuthUser user = new AuthUser(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        user.addRole(roleFound);

        authUserRepository.save(user);

        return new AuthResponse(jwtService.getToken(user));
    }
}

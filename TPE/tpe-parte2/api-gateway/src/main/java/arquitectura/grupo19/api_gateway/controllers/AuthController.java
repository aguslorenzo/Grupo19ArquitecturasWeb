package arquitectura.grupo19.api_gateway.controllers;

import arquitectura.grupo19.api_gateway.dto.LoginRequest;
import arquitectura.grupo19.api_gateway.dto.RegisterRequest;
import arquitectura.grupo19.api_gateway.security.AuthResponse;
import arquitectura.grupo19.api_gateway.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

}

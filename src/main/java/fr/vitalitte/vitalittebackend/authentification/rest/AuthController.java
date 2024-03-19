package fr.vitalitte.vitalittebackend.authentification.rest;

import fr.vitalitte.vitalittebackend.authentification.usecase.AuthentificationService;
import fr.vitalitte.vitalittebackend.payload.request.LoginRequest;
import fr.vitalitte.vitalittebackend.payload.request.SignupRequest;
import fr.vitalitte.vitalittebackend.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthentificationService authentificationService;
    public AuthController(AuthentificationService authentificationService) {
        this.authentificationService = authentificationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return this.authentificationService.login(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        this.authentificationService.registerUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("Utilisateur créé(e) avec succès !"));
    }
}

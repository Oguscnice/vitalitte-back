package fr.vitalitte.vitalittebackend.authentification.rest;

import fr.vitalitte.vitalittebackend.authentification.usecase.AuthentificationService;
import fr.vitalitte.vitalittebackend.payload.request.LoginRequest;
import fr.vitalitte.vitalittebackend.payload.request.SignupRequest;
import fr.vitalitte.vitalittebackend.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthentificationService authentificationService;

    public AuthController(AuthentificationService authentificationService) {
        this.authentificationService = authentificationService;
    }

    @GetMapping("/can-register")
    public boolean canRegister() {
        return this.authentificationService.canRegister();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return this.authentificationService.login(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        this.authentificationService.registerUser(signUpRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Utilisateur(trice) créé(e) avec succès !"));
    }
}

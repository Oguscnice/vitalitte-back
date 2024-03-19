package fr.vitalitte.vitalittebackend.authentification.usecase;

import fr.vitalitte.vitalittebackend.payload.request.LoginRequest;
import fr.vitalitte.vitalittebackend.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthentificationService {

    void registerUser(SignupRequest signUpRequest);

    ResponseEntity<?> login(LoginRequest loginRequest);
}

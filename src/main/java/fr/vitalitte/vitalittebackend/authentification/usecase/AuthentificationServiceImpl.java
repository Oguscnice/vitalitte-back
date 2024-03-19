package fr.vitalitte.vitalittebackend.authentification.usecase;

import fr.vitalitte.vitalittebackend.authentification.exception.EmailAlreadyUsedException;
import fr.vitalitte.vitalittebackend.role.exception.RoleNotFoundException;
import fr.vitalitte.vitalittebackend.authentification.jwt.JwtResponse;
import fr.vitalitte.vitalittebackend.authentification.jwt.JwtUtils;
import fr.vitalitte.vitalittebackend.payload.request.LoginRequest;
import fr.vitalitte.vitalittebackend.payload.request.SignupRequest;
import fr.vitalitte.vitalittebackend.role.models.ERole;
import fr.vitalitte.vitalittebackend.user.models.User;
import fr.vitalitte.vitalittebackend.user.persistence.UserRepository;
import fr.vitalitte.vitalittebackend.user.usecase.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthentificationServiceImpl implements AuthentificationService {

    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    AuthentificationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void registerUser(SignupRequest signUpRequest) {

        if (this.userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        Set<ERole> roles = new HashSet<ERole>();
        roles.add(ERole.ROLE_USER);

        // Create new user's account
        User newUser = User.builder()
                .firstname(signUpRequest.getFirstname())
                .lastname(signUpRequest.getLastname())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .roles(roles)
                .build();
        this.userRepository.save(newUser);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {

//        User user = userRepository.findByEmail(loginRequest.getEmail()).get();
//        if (!user.getVerified()) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Erreur vous devez etre approuvé par un administrateur avant de vous connecter !"));
//        } else {
//            System.out.println(user.getEmail());
//
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = this.jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        userDetails.getEmail(),
                        roles
                        )
                );
    }

}

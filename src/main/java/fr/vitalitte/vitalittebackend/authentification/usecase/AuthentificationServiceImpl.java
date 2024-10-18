package fr.vitalitte.vitalittebackend.authentification.usecase;

import fr.vitalitte.vitalittebackend.authentification.exception.EmailAlreadyUsedException;
import fr.vitalitte.vitalittebackend.authentification.exception.UserAlreadyExistsByLastnameAndFirstnameException;
import fr.vitalitte.vitalittebackend.authentification.jwt.JwtResponse;
import fr.vitalitte.vitalittebackend.authentification.jwt.JwtUtils;
import fr.vitalitte.vitalittebackend.common.utils.CapitalizeStringUtil;
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
    public boolean canRegister() {
        return this.userRepository.count() < 1L;
    }

    @Override
    public void registerUser(SignupRequest signUpRequest) {

        if (this.userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        String firstname = CapitalizeStringUtil.firstLetter(signUpRequest.getFirstname());
        String lastname = CapitalizeStringUtil.allLetters(signUpRequest.getLastname());

        if (this.userRepository.findByLastnameAndFirstname(lastname, firstname).isPresent()) {
            throw new UserAlreadyExistsByLastnameAndFirstnameException(lastname, firstname);
        }

        Set<ERole> roles = new HashSet<ERole>();
        roles.add(ERole.ROLE_USER);

        if (this.userRepository.count() < 1L) {
            roles.add(ERole.ROLE_ADMIN);
        }

        // Create new user's account
        User newUser = User.builder()
                .firstname(firstname)
                .lastname(lastname)
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

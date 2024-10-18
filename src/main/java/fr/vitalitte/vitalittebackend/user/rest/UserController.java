package fr.vitalitte.vitalittebackend.user.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.user.usecase.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public UserDto getProfile(@PathVariable String email) {
        return this.userService.findByEmail(email);
    }

    @GetMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> getUsers() {
        return this.userService.findAll();
    }

    @PutMapping("/update-roles/{email}")
    public ResponseEntity<MessageResponse> updateRoles(@PathVariable String email, @RequestBody UserDto userDto) {
        this.userService.updateRoles(userDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Profil mis à jour avec succès"));}

    @PutMapping("/{email}")
    public ResponseEntity<MessageResponse> updateProfile(@PathVariable String email, @RequestBody UserDto userDto) {
        this.userService.update(userDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Profil mis à jour avec succès"));}

    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable String email) {
        this.userService.deleteByEmail(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Utilisateur supprimé"));
    }
}

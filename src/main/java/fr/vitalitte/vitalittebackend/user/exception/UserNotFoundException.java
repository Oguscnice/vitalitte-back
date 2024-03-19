package fr.vitalitte.vitalittebackend.user.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(){
        super("Utilisateur non trouvé.");
    }
}

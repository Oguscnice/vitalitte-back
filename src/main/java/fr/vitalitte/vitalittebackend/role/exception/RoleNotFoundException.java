package fr.vitalitte.vitalittebackend.role.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class RoleNotFoundException extends ResourceNotFoundException {
    public RoleNotFoundException(){
        super("Role non trouvé.");
    }
}

package fr.vitalitte.vitalittebackend.role.usecase;

import fr.vitalitte.vitalittebackend.role.exception.RoleNotFoundException;
import fr.vitalitte.vitalittebackend.role.models.ERole;

public class ConvertEnumRole {
    public static String convertEnumRoleToString(ERole role){
        return switch (role) {
            case ROLE_ADMIN -> "ROLE_ADMIN";
            case ROLE_USER -> "ROLE_USER";
            default -> throw new RoleNotFoundException();
        };
    }

    public static ERole convertStringToEnumRole(String genderString){
        return switch (genderString) {
            case "ROLE_ADMIN" -> ERole.ROLE_ADMIN;
            case "ROLE_USER" -> ERole.ROLE_USER;
            default -> throw new RoleNotFoundException();
        };
    }
}

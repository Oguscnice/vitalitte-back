package fr.vitalitte.vitalittebackend.role.usecase;

import fr.vitalitte.vitalittebackend.role.exception.RoleNotFoundException;
import fr.vitalitte.vitalittebackend.role.models.ERole;

public class ConvertEnumRole {
    public static String convertEnumRoleToString(ERole role){
        switch (role){
            case ROLE_ADMIN:
                return "ROLE_ADMIN";
            case ROLE_USER:
                return "ROLE_USER";
            default:
                throw new RoleNotFoundException();
        }
    }

    public static ERole convertStringToEnumRole(String genderString){
        switch (genderString){
            case "ROLE_ADMIN":
                return ERole.ROLE_ADMIN;
            case "ROLE_USER":
                return ERole.ROLE_USER;
            default:
                throw new RoleNotFoundException();
        }
    }
}

package fr.vitalitte.vitalittebackend.materials.usecase;

import fr.vitalitte.vitalittebackend.materials.exception.MaterialTypeNotFoundException;
import fr.vitalitte.vitalittebackend.materials.models.EMaterialType;
import org.springframework.stereotype.Service;

@Service
public class ConvertEumMaterialType {
    public static String EMaterialToString(EMaterialType eMaterialType){
        switch (eMaterialType){
            case COUVERTURE:
                return "COUVERTURE";
            case RELIURE:
                return "RELIURE";
            case PAPIER:
                return "PAPIER";
            default:
                throw new MaterialTypeNotFoundException();
        }
    }

    public static EMaterialType stringToEMaterial(String genderString){
        switch (genderString){
            case "COUVERTURE":
                return EMaterialType.COUVERTURE;
            case "RELIURE":
                return EMaterialType.RELIURE;
            case "PAPIER":
                return EMaterialType.PAPIER;
            default:
                throw new MaterialTypeNotFoundException();
        }
    }
}

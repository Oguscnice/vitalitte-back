package fr.vitalitte.vitalittebackend.stationery.materialTypes.usecase;

import fr.vitalitte.vitalittebackend.stationery.materialTypes.exception.MaterialTypeNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.materialTypes.models.EMaterialType;
import org.springframework.stereotype.Service;

@Service
public class ConvertEumMaterialType {
    public static String EMaterialToString(EMaterialType eMaterialType){
        return switch (eMaterialType) {
            case COUVERTURE -> "COUVERTURE";
            case RELIURE -> "RELIURE";
            case PAPIER -> "PAPIER";
            default -> throw new MaterialTypeNotFoundException();
        };
    }

    public static EMaterialType stringToEMaterial(String genderString){
        return switch (genderString) {
            case "COUVERTURE" -> EMaterialType.COUVERTURE;
            case "RELIURE" -> EMaterialType.RELIURE;
            case "PAPIER" -> EMaterialType.PAPIER;
            default -> throw new MaterialTypeNotFoundException();
        };
    }
}

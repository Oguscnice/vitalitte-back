package fr.vitalitte.vitalittebackend.stationery.materialTypes.usecase;

import fr.vitalitte.vitalittebackend.stationery.materialTypes.exception.MaterialTypeNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.materialTypes.models.EMaterialType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertEumMaterialType {

    public static String EnumToString(EMaterialType eMaterialType) {
        return switch (eMaterialType) {
            case COUVERTURE -> "COUVERTURE";
            case RELIURE -> "RELIURE";
            case PAPIER -> "PAPIER";
            default -> throw new MaterialTypeNotFoundException();
        };
    }

    public static List<String> AllEnumsToStringArray() {
        return Arrays.stream(EMaterialType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static EMaterialType StringToEnum(String marterialType) {
        return switch (marterialType) {
            case "COUVERTURE" -> EMaterialType.COUVERTURE;
            case "RELIURE" -> EMaterialType.RELIURE;
            case "PAPIER" -> EMaterialType.PAPIER;
            default -> throw new MaterialTypeNotFoundException();
        };
    }
}

package fr.vitalitte.vitalittebackend.stationery.materialTypes.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class MaterialTypeNotFoundException extends ResourceNotFoundException {
    public MaterialTypeNotFoundException(){
        super("Type de matériel non trouvé");
    }
}

package fr.vitalitte.vitalittebackend.materials.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class MaterialNotFoundException extends ResourceNotFoundException {
    public MaterialNotFoundException() {
        super("Matériel non trouvé.");
    }
}

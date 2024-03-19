package fr.vitalitte.vitalittebackend.materials.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class SlugMaterialAlreadyExistsException extends ResourceNotFoundException {
    public SlugMaterialAlreadyExistsException() {
        super("Nom du matériel déjà existant");
    }
}

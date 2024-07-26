package fr.vitalitte.vitalittebackend.stationery.materials.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class SlugMaterialAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugMaterialAlreadyExistsException() {
        super("Nom du matériel déjà existant");
    }
}

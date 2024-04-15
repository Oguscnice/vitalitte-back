package fr.vitalitte.vitalittebackend.workshop.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class SlugWorkshopAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugWorkshopAlreadyExistsException() {
        super("Nom et date d'Atelier déjà existant");
    }
}

package fr.vitalitte.vitalittebackend.activity.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class SlugActivityAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugActivityAlreadyExistsException(){
        super("Activité déjà existante.");
    }
}

package fr.vitalitte.vitalittebackend.stationery.collection.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class SlugCollectionAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugCollectionAlreadyExistsException(){
        super("Collection déjà existante.");
    }
}

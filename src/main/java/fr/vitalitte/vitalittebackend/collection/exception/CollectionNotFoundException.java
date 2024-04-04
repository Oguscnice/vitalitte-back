package fr.vitalitte.vitalittebackend.collection.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class CollectionNotFoundException extends ResourceNotFoundException {
    public CollectionNotFoundException(){
        super("Collection non trouvée.");
    }
}

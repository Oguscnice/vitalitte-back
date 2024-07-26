package fr.vitalitte.vitalittebackend.stationery.category.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class SlugCategoryAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugCategoryAlreadyExistsException(){
        super("Catégorie déjà existante.");
    }
}

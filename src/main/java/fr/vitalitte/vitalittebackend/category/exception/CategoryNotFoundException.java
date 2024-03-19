package fr.vitalitte.vitalittebackend.category.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(){
        super("Catégorie non trouvée.");
    }
}

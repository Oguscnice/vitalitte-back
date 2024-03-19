package fr.vitalitte.vitalittebackend.notebook.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class SlugNotebookAlreadyExistsException extends ResourceNotFoundException {
    public SlugNotebookAlreadyExistsException() {
        super("Nom du carnet déjà existant");
    }
}

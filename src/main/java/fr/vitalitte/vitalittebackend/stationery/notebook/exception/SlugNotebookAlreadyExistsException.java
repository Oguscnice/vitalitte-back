package fr.vitalitte.vitalittebackend.stationery.notebook.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class SlugNotebookAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugNotebookAlreadyExistsException() {
        super("Nom du carnet déjà existant");
    }
}

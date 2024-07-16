package fr.vitalitte.vitalittebackend.stationery.notebook.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class NotebookNotFoundException extends ResourceNotFoundException {
    public NotebookNotFoundException(){
        super("Carnet non trouvé.");
    }
}

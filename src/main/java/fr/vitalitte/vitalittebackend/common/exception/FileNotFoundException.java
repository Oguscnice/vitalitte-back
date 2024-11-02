package fr.vitalitte.vitalittebackend.common.exception;

public class FileNotFoundException extends ResourceNotFoundException {
    public FileNotFoundException() {
        super("Fichier non trouvé.");
    }
}

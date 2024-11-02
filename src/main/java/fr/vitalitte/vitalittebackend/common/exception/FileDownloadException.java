package fr.vitalitte.vitalittebackend.common.exception;

public class FileDownloadException extends InvalidTypeException {
    public FileDownloadException() {
        super("Fichier non sauvegardé, une erreur s'est produite.");
    }
}

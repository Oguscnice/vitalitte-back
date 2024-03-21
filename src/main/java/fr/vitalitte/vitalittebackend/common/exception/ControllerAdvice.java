package fr.vitalitte.vitalittebackend.common.exception;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleAlreadyExistException(ResourceAlreadyExistException e) {
        LOGGER.error("Erreur interceptee", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleUrlInvalidException(InvalidTypeException e) {
        LOGGER.error("Erreur interceptee", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
    }

    /**
     * si aucune des erreurs n'a été trouvée, c'est celle-ci qui sera renvoyée
     *
     * @param e exception que l'on récupère
     * @return code statut : 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleGlobalException(Exception e) {
        LOGGER.error("Erreur interceptee", e);
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new MessageResponse("Une erreur s'est produite."));
    }
}

package fr.vitalitte.vitalittebackend.authentification.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class UserAlreadyExistsByLastnameAndFirstnameException extends ResourceAlreadyExistException {
    public UserAlreadyExistsByLastnameAndFirstnameException(String lastname, String firstname) {
        super(String.format("L'utilisateur(trice) %s %s existe déjà.", lastname, firstname));
    }
}

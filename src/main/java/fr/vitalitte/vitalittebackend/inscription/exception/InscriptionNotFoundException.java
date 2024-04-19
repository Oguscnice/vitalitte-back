package fr.vitalitte.vitalittebackend.inscription.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class InscriptionNotFoundException extends ResourceNotFoundException {
    public InscriptionNotFoundException(){
        super("Personne inscrite non trouvée.");
    }
}

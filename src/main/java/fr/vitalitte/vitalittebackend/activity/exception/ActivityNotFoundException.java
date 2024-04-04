package fr.vitalitte.vitalittebackend.activity.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class ActivityNotFoundException extends ResourceNotFoundException {
    public ActivityNotFoundException(){
        super("Activité non trouvée.");
    }
}

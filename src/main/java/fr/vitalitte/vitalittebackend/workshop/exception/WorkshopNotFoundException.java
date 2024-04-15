package fr.vitalitte.vitalittebackend.workshop.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class WorkshopNotFoundException extends ResourceNotFoundException{
    public WorkshopNotFoundException(){super ("Atelier non trouvé.");}
}

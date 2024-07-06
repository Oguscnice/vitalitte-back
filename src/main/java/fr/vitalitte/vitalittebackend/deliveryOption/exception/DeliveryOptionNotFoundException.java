package fr.vitalitte.vitalittebackend.deliveryOption.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class DeliveryOptionNotFoundException extends ResourceAlreadyExistException {
    public DeliveryOptionNotFoundException() {super("Option de Livraison non trouvée.");}
}

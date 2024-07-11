package fr.vitalitte.vitalittebackend.deliveryOption.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class SlugDeliveryOptionAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugDeliveryOptionAlreadyExistsException() {super("Option de Livraison déjà existante.");}
}

package fr.vitalitte.vitalittebackend.stationery.productType.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;

public class ProductTypeNotFoundException extends ResourceAlreadyExistException {
    public ProductTypeNotFoundException() {super("Type de produit non trouvé.");}
}

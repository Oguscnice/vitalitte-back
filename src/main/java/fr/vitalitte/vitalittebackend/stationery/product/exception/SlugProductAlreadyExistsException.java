package fr.vitalitte.vitalittebackend.stationery.product.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceAlreadyExistException;
import fr.vitalitte.vitalittebackend.common.usecase.CapitalizeStringUtil;

public class SlugProductAlreadyExistsException extends ResourceAlreadyExistException {
    public SlugProductAlreadyExistsException(String productType) {
        super(String.format("Nom du %$ déjà existant", CapitalizeStringUtil.firstLetter(productType.toLowerCase())));
    }
}

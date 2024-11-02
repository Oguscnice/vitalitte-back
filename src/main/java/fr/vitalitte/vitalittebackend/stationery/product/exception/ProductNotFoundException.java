package fr.vitalitte.vitalittebackend.stationery.product.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;
import fr.vitalitte.vitalittebackend.common.usecase.CapitalizeStringUtil;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String productType) {
        super(String.format("%s non trouvé.", CapitalizeStringUtil.firstLetter(productType.toLowerCase())));
    }
}

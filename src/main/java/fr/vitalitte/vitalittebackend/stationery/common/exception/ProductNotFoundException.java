package fr.vitalitte.vitalittebackend.stationery.common.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException() {super ("Produit introuvable.");}
}

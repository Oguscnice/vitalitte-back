package fr.vitalitte.vitalittebackend.inscription.exception;

import fr.vitalitte.vitalittebackend.common.exception.NotAvailableException;

public class InscriptionNotAvailableException extends NotAvailableException {
    public InscriptionNotAvailableException() {super("Inscription impossible, vérifier le nombre de places disponibles.");}
}

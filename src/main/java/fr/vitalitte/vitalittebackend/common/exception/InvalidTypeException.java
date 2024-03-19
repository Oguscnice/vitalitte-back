package fr.vitalitte.vitalittebackend.common.exception;

public class InvalidTypeException extends RuntimeException{
    public InvalidTypeException(String resourceType) {
        super(resourceType);
    }
}

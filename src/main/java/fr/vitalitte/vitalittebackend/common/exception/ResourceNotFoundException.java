package fr.vitalitte.vitalittebackend.common.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceType) {
        super(resourceType);
    }
}

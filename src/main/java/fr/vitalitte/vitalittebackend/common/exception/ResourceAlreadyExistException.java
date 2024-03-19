package fr.vitalitte.vitalittebackend.common.exception;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String resourceType){
        super(resourceType);
    }
}

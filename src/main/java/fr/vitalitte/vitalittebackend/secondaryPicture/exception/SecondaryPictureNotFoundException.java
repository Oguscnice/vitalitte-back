package fr.vitalitte.vitalittebackend.secondaryPicture.exception;

import fr.vitalitte.vitalittebackend.common.exception.ResourceNotFoundException;

public class SecondaryPictureNotFoundException extends ResourceNotFoundException {
    public SecondaryPictureNotFoundException(){
        super("Image Secondaire non trouvée.");
    }
}

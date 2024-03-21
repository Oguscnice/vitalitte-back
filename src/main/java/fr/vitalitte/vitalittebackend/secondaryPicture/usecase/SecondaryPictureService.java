package fr.vitalitte.vitalittebackend.secondaryPicture.usecase;

import fr.vitalitte.vitalittebackend.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.secondaryPicture.rest.SecondaryPictureDto;

import java.util.List;

public interface SecondaryPictureService {
    SecondaryPicture createSecondaryPicture(String notebookSlug, String url);
    List<SecondaryPictureDto> findAllSecondaryPictureByNotebook(Notebook notebook);

}

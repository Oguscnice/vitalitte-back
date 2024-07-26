package fr.vitalitte.vitalittebackend.stationery.secondaryPicture.usecase;

import fr.vitalitte.vitalittebackend.stationery.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.rest.SecondaryPictureDto;

import java.util.List;

public interface SecondaryPictureService {

    void createSecondaryPicture(String notebookSlug, SecondaryPictureDto secondaryPictureDto);
    List<SecondaryPictureDto> findAllSecondaryPicturesByNotebook(Notebook notebook);

}

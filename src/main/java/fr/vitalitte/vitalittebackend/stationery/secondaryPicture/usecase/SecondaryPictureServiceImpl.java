package fr.vitalitte.vitalittebackend.stationery.secondaryPicture.usecase;

import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.stationery.notebook.exception.NotebookNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.stationery.notebook.persistence.NotebookRepository;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.persistence.SecondaryPictureRepository;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.rest.SecondaryPictureDto;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class SecondaryPictureServiceImpl implements SecondaryPictureService {

    SecondaryPictureRepository secondaryPictureRepository;
    NotebookRepository notebookRepository;
    TransformUrl transformUrl;
    TransformSecondaryPicture transformSecondaryPicture;

    public SecondaryPictureServiceImpl(SecondaryPictureRepository secondaryPictureRepository, NotebookRepository notebookRepository, TransformUrl transformUrl, TransformSecondaryPicture transformSecondaryPicture) {
        this.secondaryPictureRepository = secondaryPictureRepository;
        this.notebookRepository = notebookRepository;
        this.transformUrl = transformUrl;
        this.transformSecondaryPicture = transformSecondaryPicture;
    }

    public void createSecondaryPicture(String notebookSlug, SecondaryPictureDto secondaryPictureDto) {

        Notebook notebookFound = this.notebookRepository.findBySlug(notebookSlug).orElseThrow(NotebookNotFoundException::new);

        URL picture = this.transformUrl.stringToUrl(secondaryPictureDto.getPicture());
        URL pictureThumbnail = this.transformUrl.stringToUrl(secondaryPictureDto.getPictureThumbnail());

        SecondaryPicture newPicture = SecondaryPicture.builder()
                                                      .picture(picture)
                                                      .pictureThumbnail(pictureThumbnail)
                                                      .notebook(notebookFound)
                                                      .build();

        this.secondaryPictureRepository.save(newPicture);
    }

    public List<SecondaryPictureDto> findAllSecondaryPicturesByNotebook(Notebook notebook){
        List<SecondaryPicture> pictures = this.secondaryPictureRepository.findAllByNotebook(notebook);
        return this.transformSecondaryPicture.picturesToDtos(pictures);
    }
}

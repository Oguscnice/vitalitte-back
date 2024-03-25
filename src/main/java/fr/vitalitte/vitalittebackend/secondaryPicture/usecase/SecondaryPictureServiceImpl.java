package fr.vitalitte.vitalittebackend.secondaryPicture.usecase;

import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.notebook.exception.NotebookNotFoundException;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.notebook.persistence.NotebookRepository;
import fr.vitalitte.vitalittebackend.secondaryPicture.exception.SecondaryPictureNotFoundException;
import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.secondaryPicture.persistence.SecondaryPictureRepository;
import fr.vitalitte.vitalittebackend.secondaryPicture.rest.SecondaryPictureDto;
import org.springframework.stereotype.Service;

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

    public SecondaryPicture createSecondaryPicture(String notebookSlug, String url){
        Notebook notebookFound = this.notebookRepository.findBySlug(notebookSlug).orElseThrow(NotebookNotFoundException::new);
        SecondaryPicture newPicture = SecondaryPicture.builder()
                                                      .url(this.transformUrl.stringToUrl(url))
                                                      .notebook(notebookFound)
                                                      .build();
        return this.secondaryPictureRepository.save(newPicture);
    }

    public List<SecondaryPictureDto> findAllSecondaryPictureByNotebook(Notebook notebook){
        List<SecondaryPicture> pictures = this.secondaryPictureRepository.findAllByNotebook(notebook);
        return this.transformSecondaryPicture.picturesToDtos(pictures);
    }
}

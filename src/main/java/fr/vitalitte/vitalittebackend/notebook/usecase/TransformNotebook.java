package fr.vitalitte.vitalittebackend.notebook.usecase;

import fr.vitalitte.vitalittebackend.category.usecase.TransformCategory;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.notebook.exception.NotebookNotFoundException;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.notebook.persistence.NotebookRepository;
import fr.vitalitte.vitalittebackend.notebook.rest.NotebookDto;
import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransformNotebook {
    TransformUrl transformUrl;
    TransformCategory transformCategory;
    NotebookRepository notebookRepository;

    public TransformNotebook(TransformUrl transformUrl, TransformCategory transformCategory, NotebookRepository notebookRepository) {
        this.transformUrl = transformUrl;
        this.transformCategory = transformCategory;
        this.notebookRepository = notebookRepository;
    }

    public NotebookDto notebookToDto(Notebook notebook){

        String mainPicture = this.transformUrl.urlToString(notebook.getMainPicture());

        List<SecondaryPicture> secondaryPictures = notebook.getSecondaryPictures();
        List<String> secondaryPicturesUrl = new ArrayList<>();
        for(SecondaryPicture secondaryPicture : secondaryPictures){
            secondaryPicturesUrl.add(this.transformUrl.urlToString(secondaryPicture.getUrl()));
        }

        return NotebookDto.builder()
                .name(notebook.getName())
                .slug(notebook.getSlug())
                .mainPicture(mainPicture)
                .introduction(notebook.getIntroduction())
                .price(notebook.getPrice())
                .secondaryPictures(secondaryPicturesUrl)
                .description(notebook.getDescription())
                .materials(notebook.getMaterials())
                .categoryDto(this.transformCategory.categoryToDto(notebook.getCategory()))
                .isAvailable(notebook.isAvailable())
                .build();
    }
    public List<NotebookDto> notebooksToDto(List<Notebook> notebooks) {
        return mapList(this::notebookToDto, notebooks);
    }

    public Notebook dtoToNotebook(NotebookDto notebook){
        return this.notebookRepository.findBySlug(notebook.getSlug())
                .orElseThrow(NotebookNotFoundException::new);
    }

    public List<Notebook> dtosToNotebooks(List<NotebookDto> notebooksDto) {
        return mapList(this::dtoToNotebook, notebooksDto);
    }
}

package fr.vitalitte.vitalittebackend.notebook.usecase;

import fr.vitalitte.vitalittebackend.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.category.models.Category;
import fr.vitalitte.vitalittebackend.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.category.usecase.TransformCategory;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.materials.usecase.TransformMaterial;
import fr.vitalitte.vitalittebackend.notebook.exception.NotebookNotFoundException;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.notebook.persistence.NotebookRepository;
import fr.vitalitte.vitalittebackend.notebook.rest.NotebookDto;
import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.secondaryPicture.persistence.SecondaryPictureRepository;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransformNotebook {
    TransformUrl transformUrl;
    TransformCategory transformCategory;
    TransformMaterial transformMaterial;
    NotebookRepository notebookRepository;
    CategoryRepository categoryRepository;
    SecondaryPictureRepository secondaryPictureRepository;

    public TransformNotebook(TransformUrl transformUrl, TransformCategory transformCategory, TransformMaterial transformMaterial, NotebookRepository notebookRepository, CategoryRepository categoryRepository, SecondaryPictureRepository secondaryPictureRepository) {
        this.transformUrl = transformUrl;
        this.transformCategory = transformCategory;
        this.transformMaterial = transformMaterial;
        this.notebookRepository = notebookRepository;
        this.categoryRepository = categoryRepository;
        this.secondaryPictureRepository = secondaryPictureRepository;
    }

    public NotebookDto notebookToDto(Notebook notebook){

        String mainPicture = this.transformUrl.urlToString(notebook.getMainPicture());

        CategoryDto categoryDto = null;

        if(notebook.getCategory() != null){
            Category categoryFound = this.categoryRepository.findBySlug(notebook.getCategory().getSlug()).orElseThrow(CategoryNotFoundException::new);
            categoryDto = this.transformCategory.categoryToDto(categoryFound);
        }

        List<SecondaryPicture> secondaryPictures = this.secondaryPictureRepository.findAllByNotebook(notebook);
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
                .materialsDto(this.transformMaterial.materialsToDto(notebook.getMaterials()))
                .categoryDto(categoryDto)
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

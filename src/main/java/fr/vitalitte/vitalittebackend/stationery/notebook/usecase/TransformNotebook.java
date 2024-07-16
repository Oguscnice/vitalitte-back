package fr.vitalitte.vitalittebackend.stationery.notebook.usecase;

import fr.vitalitte.vitalittebackend.stationery.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.stationery.category.usecase.TransformCategory;
import fr.vitalitte.vitalittebackend.stationery.collection.exception.CollectionNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.collection.persistence.CollectionRepository;
import fr.vitalitte.vitalittebackend.stationery.collection.rest.CollectionDto;
import fr.vitalitte.vitalittebackend.stationery.collection.usecase.TransformCollection;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.stationery.materials.usecase.TransformMaterial;
import fr.vitalitte.vitalittebackend.stationery.notebook.exception.NotebookNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.stationery.notebook.persistence.NotebookRepository;
import fr.vitalitte.vitalittebackend.stationery.notebook.rest.NotebookDto;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.persistence.SecondaryPictureRepository;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.rest.SecondaryPictureDto;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.usecase.TransformSecondaryPicture;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

import java.util.List;

@Service
public class TransformNotebook {

    TransformUrl transformUrl;
    TransformCategory transformCategory;
    TransformCollection transformCollection;
    TransformMaterial transformMaterial;
    TransformSecondaryPicture transformSecondaryPicture;
    NotebookRepository notebookRepository;
    CategoryRepository categoryRepository;
    CollectionRepository collectionRepository;
    SecondaryPictureRepository secondaryPictureRepository;

    public TransformNotebook(TransformUrl transformUrl, TransformCategory transformCategory, TransformCollection transformCollection, TransformMaterial transformMaterial, TransformSecondaryPicture transformSecondaryPicture, NotebookRepository notebookRepository, CategoryRepository categoryRepository, CollectionRepository collectionRepository, SecondaryPictureRepository secondaryPictureRepository) {
        this.transformUrl = transformUrl;
        this.transformCategory = transformCategory;
        this.transformCollection = transformCollection;
        this.transformMaterial = transformMaterial;
        this.transformSecondaryPicture = transformSecondaryPicture;
        this.notebookRepository = notebookRepository;
        this.categoryRepository = categoryRepository;
        this.collectionRepository = collectionRepository;
        this.secondaryPictureRepository = secondaryPictureRepository;
    }

    public NotebookDto notebookToDto(Notebook notebook){

        String picture = this.transformUrl.urlToString(notebook.getPicture());
        String pictureThumbnail = this.transformUrl.urlToString(notebook.getPictureThumbnail());

        CategoryDto categoryDto = null;
        if(notebook.getCategory() != null){
            Category categoryFound = this.categoryRepository.findBySlug(notebook.getCategory().getSlug()).orElseThrow(CategoryNotFoundException::new);
            categoryDto = this.transformCategory.categoryToDto(categoryFound);
        }

        CollectionDto collectionDto = null;
        if(notebook.getCollection() != null) {
            Collection collectionFound = this.collectionRepository.findBySlug(notebook.getCollection().getSlug()).orElseThrow(CollectionNotFoundException::new);
            collectionDto = this.transformCollection.collectionToDto(collectionFound);
        }

        List<SecondaryPicture> secondaryPictures = this.secondaryPictureRepository.findAllByNotebook(notebook);
        List<SecondaryPictureDto> secondaryPicturesDto = this.transformSecondaryPicture.picturesToDtos(secondaryPictures);

        return NotebookDto.builder()
                .name(notebook.getName())
                .slug(notebook.getSlug())
                .picture(picture)
                .pictureThumbnail(pictureThumbnail)
                .introduction(notebook.getIntroduction())
                .price(notebook.getPrice())
                .secondaryPicturesDto(secondaryPicturesDto)
                .description(notebook.getDescription())
                .materialsDto(this.transformMaterial.materialsToDto(notebook.getMaterials()))
                .categoryDto(categoryDto)
                .collectionDto(collectionDto)
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

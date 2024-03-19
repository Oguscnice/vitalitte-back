package fr.vitalitte.vitalittebackend.notebook.usecase;

import fr.vitalitte.vitalittebackend.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.category.models.Category;
import fr.vitalitte.vitalittebackend.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.category.usecase.TransformCategory;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.materials.exception.MaterialTypeNotFoundException;
import fr.vitalitte.vitalittebackend.materials.exception.SlugMaterialAlreadyExistsException;
import fr.vitalitte.vitalittebackend.materials.models.Material;
import fr.vitalitte.vitalittebackend.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.materials.rest.MaterialDto;
import fr.vitalitte.vitalittebackend.materials.usecase.TransformMaterial;
import fr.vitalitte.vitalittebackend.notebook.exception.SlugNotebookAlreadyExistsException;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.notebook.persistence.NotebookRepository;
import fr.vitalitte.vitalittebackend.notebook.rest.CreateNotebookBody;
import fr.vitalitte.vitalittebackend.notebook.rest.NotebookDto;
import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.secondaryPicture.persistence.SecondaryPictureRepository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotebookServiceImpl implements NotebookService {
    NotebookRepository notebookRepository;
    CategoryRepository categoryRepository;
    SecondaryPictureRepository secondaryPictureRepository;
    MaterialRepository materialRepository;
    TransformNotebook transformNotebook;
    TransformMaterial transformMaterial;
    TransformCategory transformCategory;
    TransformUrl transformUrl;

    public NotebookServiceImpl(NotebookRepository notebookRepository, CategoryRepository categoryRepository, SecondaryPictureRepository secondaryPictureRepository, MaterialRepository materialRepository, TransformNotebook transformNotebook, TransformMaterial transformMaterial, TransformCategory transformCategory, TransformUrl transformUrl) {
        this.notebookRepository = notebookRepository;
        this.categoryRepository = categoryRepository;
        this.secondaryPictureRepository = secondaryPictureRepository;
        this.materialRepository = materialRepository;
        this.transformNotebook = transformNotebook;
        this.transformMaterial = transformMaterial;
        this.transformCategory = transformCategory;
        this.transformUrl = transformUrl;
    }

    public void createNotebook(CreateNotebookBody createNotebookBody){

        String newSlug = SlugifyUtil.stringToSlug(createNotebookBody.getName());

        if (this.notebookRepository.existsBySlug(newSlug)) {
            throw new SlugNotebookAlreadyExistsException();
        }

        // vérifie que ce soit bien un String valide en URL, le convertis ou jète une erreur
        URL newUrlMainPicture = this.transformUrl.stringToUrl(createNotebookBody.getMainPicture());
//        List<URL> newUrlSecondaryPictures = this.transformUrl.stringsToUrl(createNotebookBody.getSecondaryPictures());
        final Category categoryFound = this.categoryRepository.findBySlug(createNotebookBody.getCategoryDto().getSlug())
                                            .orElseThrow(CategoryNotFoundException::new);

        final List<Material> materials = new ArrayList<>();
        for(MaterialDto materialDto : createNotebookBody.getMaterialsDto()){
            final Material materialFound = this.materialRepository.findBySlug(materialDto.getSlug())
                                                    .orElseThrow(MaterialTypeNotFoundException::new);
            materials.add(materialFound);
        }

        final List<SecondaryPicture> secondaryPictures = new ArrayList<>();
        for(String pictureUrl : createNotebookBody.getSecondaryPictures()){
            final SecondaryPicture newSecondaryPicture = SecondaryPicture.builder()
                                                                         .url(this.transformUrl.stringToUrl(pictureUrl))
                                                                         .build();
            this.secondaryPictureRepository.save(newSecondaryPicture);
            secondaryPictures.add(newSecondaryPicture);
        }

        final Notebook newNotebook = Notebook.builder()
                .name(createNotebookBody.getName())
                .slug(newSlug)
                .mainPicture(newUrlMainPicture)
                .introduction(createNotebookBody.getIntroduction())
                .price(createNotebookBody.getPrice())
                .description(createNotebookBody.getDescription())
                .category(categoryFound)
                .materials(materials)
                .secondaryPictures(secondaryPictures)
                .build();

        this.notebookRepository.save(newNotebook);

    }
//    List<NotebookDto> findAllNotebooks(){
//
//    }
//    void changeNotebookAvailabilityBySlug(String slug, boolean booleanValue){
//
//    }
//    void updateNotebookBySlug(String slug, NotebookDto notebookDtoUpdated){
//
//    }
//    void deleteNotebookBySlug(String slug){
//
//    }
}

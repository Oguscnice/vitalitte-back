package fr.vitalitte.vitalittebackend.notebook.usecase;

import fr.vitalitte.vitalittebackend.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.category.exception.SlugCategoryAlreadyExistsException;
import fr.vitalitte.vitalittebackend.category.models.Category;
import fr.vitalitte.vitalittebackend.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.category.usecase.TransformCategory;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.materials.exception.MaterialNotFoundException;
import fr.vitalitte.vitalittebackend.materials.exception.MaterialTypeNotFoundException;
import fr.vitalitte.vitalittebackend.materials.exception.SlugMaterialAlreadyExistsException;
import fr.vitalitte.vitalittebackend.materials.models.Material;
import fr.vitalitte.vitalittebackend.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.materials.rest.MaterialDto;
import fr.vitalitte.vitalittebackend.materials.usecase.TransformMaterial;
import fr.vitalitte.vitalittebackend.notebook.exception.NotebookNotFoundException;
import fr.vitalitte.vitalittebackend.notebook.exception.SlugNotebookAlreadyExistsException;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.notebook.persistence.NotebookRepository;
import fr.vitalitte.vitalittebackend.notebook.rest.CreateNotebookBody;
import fr.vitalitte.vitalittebackend.notebook.rest.NotebookController;
import fr.vitalitte.vitalittebackend.notebook.rest.NotebookDto;
import fr.vitalitte.vitalittebackend.secondaryPicture.exception.SecondaryPictureNotFoundException;
import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.secondaryPicture.persistence.SecondaryPictureRepository;
import fr.vitalitte.vitalittebackend.secondaryPicture.usecase.SecondaryPictureService;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotebookServiceImpl implements NotebookService {
    NotebookRepository notebookRepository;
    CategoryRepository categoryRepository;
    SecondaryPictureRepository secondaryPictureRepository;
    SecondaryPictureService secondaryPictureService;
    MaterialRepository materialRepository;
    TransformNotebook transformNotebook;
    TransformMaterial transformMaterial;
    TransformCategory transformCategory;
    TransformUrl transformUrl;

    public NotebookServiceImpl(NotebookRepository notebookRepository, CategoryRepository categoryRepository, SecondaryPictureRepository secondaryPictureRepository, SecondaryPictureService secondaryPictureService, MaterialRepository materialRepository, TransformNotebook transformNotebook, TransformMaterial transformMaterial, TransformCategory transformCategory, TransformUrl transformUrl) {
        this.notebookRepository = notebookRepository;
        this.categoryRepository = categoryRepository;
        this.secondaryPictureRepository = secondaryPictureRepository;
        this.secondaryPictureService = secondaryPictureService;
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

//      vérifie que ce soit bien un String valide en URL, le convertis ou jète une erreur
        URL newUrlMainPicture = this.transformUrl.stringToUrl(createNotebookBody.getMainPicture());
//      List<URL> newUrlSecondaryPictures = this.transformUrl.stringsToUrl(createNotebookBody.getSecondaryPictures());
        final Category categoryFound = this.categoryRepository.findBySlug(createNotebookBody.getCategoryDto().getSlug())
                                            .orElseThrow(CategoryNotFoundException::new);

        final List<Material> materials = new ArrayList<>();
        for(MaterialDto materialDto : createNotebookBody.getMaterialsDto()){
            final Material materialFound = this.materialRepository.findBySlug(materialDto.getSlug())
                                                    .orElseThrow(MaterialTypeNotFoundException::new);
            materials.add(materialFound);
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
                .build();

        this.notebookRepository.save(newNotebook);

        if (!createNotebookBody.getSecondaryPictures().isEmpty()) {
            for (String pictureUrl : createNotebookBody.getSecondaryPictures()) {
                this.secondaryPictureService.createSecondaryPicture(newSlug, pictureUrl);
            }
        }
    }

    public NotebookDto getNotebookBySlug(String slug){
        return this.transformNotebook.notebookToDto(this.notebookRepository.findBySlug(slug)
                                                            .orElseThrow(NotebookNotFoundException::new));
    }
    public List<NotebookDto> findAllNotebooks(){
        return this.transformNotebook.notebooksToDto(this.notebookRepository.findAll());
    }
    public void changeNotebookAvailabilityBySlug(String slug, NotebookDto notebookDto){
        Notebook notebookToUpdate = this.notebookRepository.findBySlug(slug)
                                            .orElseThrow(NotebookNotFoundException::new);
        notebookToUpdate.setAvailable(!notebookToUpdate.isAvailable());
        this.notebookRepository.save(notebookToUpdate);
    }
    public void updateNotebookBySlug(String slug, NotebookDto notebookDtoUpdated){
        Notebook notebookToUpdate = this.notebookRepository.findBySlug(slug)
                .orElseThrow(NotebookNotFoundException::new);

        String newSlug = SlugifyUtil.stringToSlug(notebookDtoUpdated.getName());

        if (this.categoryRepository.existsBySlug(newSlug) && (!slug.equals(newSlug))) {
            throw new SlugNotebookAlreadyExistsException();
        }

        List<SecondaryPicture> actualSecondaryPictures = this.secondaryPictureRepository.findAllByNotebook(notebookToUpdate);

        notebookToUpdate.setName(notebookDtoUpdated.getName());
        notebookToUpdate.setSlug(newSlug);
        notebookToUpdate.setMainPicture(this.transformUrl.stringToUrl(notebookDtoUpdated.getMainPicture()));
        notebookToUpdate.setIntroduction(notebookDtoUpdated.getIntroduction());
        notebookToUpdate.setPrice(notebookDtoUpdated.getPrice());
        notebookToUpdate.setDescription(notebookDtoUpdated.getDescription());
        notebookToUpdate.setCategory(this.transformCategory.dtoToCategory(notebookDtoUpdated.getCategoryDto()));

        List<Material> materials = new ArrayList<>();
        for(MaterialDto materialDto : notebookDtoUpdated.getMaterialsDto()){
            materials.add(this.materialRepository.findBySlug(materialDto.getSlug()).orElseThrow(MaterialNotFoundException::new));
        }
        notebookToUpdate.setMaterials(materials);

        this.notebookRepository.save(notebookToUpdate);

        List<SecondaryPicture> newSecondaryPictures = new ArrayList<>();
        for(String picture : notebookDtoUpdated.getSecondaryPictures()){
            if(this.secondaryPictureRepository.findByUrlAndNotebook(this.transformUrl.stringToUrl(picture), notebookToUpdate).isPresent()){
                SecondaryPicture secondaryPicture = this.secondaryPictureRepository.findByUrlAndNotebook(this.transformUrl.stringToUrl(picture), notebookToUpdate).orElseThrow(SecondaryPictureNotFoundException::new);
                secondaryPicture.setNotebook(notebookToUpdate);
                this.secondaryPictureRepository.save(secondaryPicture);
                newSecondaryPictures.add(secondaryPicture);
            }else{
                SecondaryPicture newSecondaryPicture = SecondaryPicture.builder()
                                                                .notebook(notebookToUpdate)
                                                                .url(this.transformUrl.stringToUrl(picture))
                                                                .build();
                this.secondaryPictureRepository.save(newSecondaryPicture);
                newSecondaryPictures.add(newSecondaryPicture);
            }
        }

        if (!actualSecondaryPictures.isEmpty()){
            for(SecondaryPicture actualPicture : actualSecondaryPictures){
                boolean exists = false;
                for(SecondaryPicture newPicture : newSecondaryPictures){
                    if (actualPicture.getUrl() == newPicture.getUrl()) {
                        exists = true;
                        break;
                    }
                }
                if(!exists){
                    this.secondaryPictureRepository.delete(actualPicture);
                }
            }
        }
    }
    public void deleteNotebookBySlug(String slug){
        Notebook notebookToDelete = this.notebookRepository.findBySlug(slug)
                .orElseThrow(NotebookNotFoundException::new);

        this.notebookRepository.delete(notebookToDelete);
    }
}

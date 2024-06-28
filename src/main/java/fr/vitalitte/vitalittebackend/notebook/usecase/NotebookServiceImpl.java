package fr.vitalitte.vitalittebackend.notebook.usecase;

import fr.vitalitte.vitalittebackend.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.category.models.Category;
import fr.vitalitte.vitalittebackend.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.category.usecase.TransformCategory;
import fr.vitalitte.vitalittebackend.collection.exception.CollectionNotFoundException;
import fr.vitalitte.vitalittebackend.collection.models.Collection;
import fr.vitalitte.vitalittebackend.collection.persistence.CollectionRepository;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.materials.exception.MaterialNotFoundException;
import fr.vitalitte.vitalittebackend.materials.models.Material;
import fr.vitalitte.vitalittebackend.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.materials.usecase.TransformMaterial;
import fr.vitalitte.vitalittebackend.notebook.exception.NotebookNotFoundException;
import fr.vitalitte.vitalittebackend.notebook.exception.SlugNotebookAlreadyExistsException;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.notebook.persistence.NotebookRepository;
import fr.vitalitte.vitalittebackend.notebook.rest.CreateNotebookBody;
import fr.vitalitte.vitalittebackend.notebook.rest.NotebookDto;
import fr.vitalitte.vitalittebackend.secondaryPicture.exception.SecondaryPictureNotFoundException;
import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.secondaryPicture.persistence.SecondaryPictureRepository;
import fr.vitalitte.vitalittebackend.secondaryPicture.rest.SecondaryPictureDto;
import fr.vitalitte.vitalittebackend.secondaryPicture.usecase.SecondaryPictureService;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotebookServiceImpl implements NotebookService {
    NotebookRepository notebookRepository;
    CategoryRepository categoryRepository;
    MaterialRepository materialRepository;
    CollectionRepository collectionRepository;
    SecondaryPictureRepository secondaryPictureRepository;
    SecondaryPictureService secondaryPictureService;
    TransformNotebook transformNotebook;
    TransformMaterial transformMaterial;
    TransformCategory transformCategory;
    TransformUrl transformUrl;

    public NotebookServiceImpl(NotebookRepository notebookRepository, CategoryRepository categoryRepository, MaterialRepository materialRepository, CollectionRepository collectionRepository, SecondaryPictureRepository secondaryPictureRepository, SecondaryPictureService secondaryPictureService, TransformNotebook transformNotebook, TransformMaterial transformMaterial, TransformCategory transformCategory, TransformUrl transformUrl) {
        this.notebookRepository = notebookRepository;
        this.categoryRepository = categoryRepository;
        this.materialRepository = materialRepository;
        this.collectionRepository = collectionRepository;
        this.secondaryPictureRepository = secondaryPictureRepository;
        this.secondaryPictureService = secondaryPictureService;
        this.transformNotebook = transformNotebook;
        this.transformMaterial = transformMaterial;
        this.transformCategory = transformCategory;
        this.transformUrl = transformUrl;
    }

    public void createNotebook(CreateNotebookBody createNotebookBody) {

        String newNotebookSlug = slugifyNotebookByName(createNotebookBody.getName());
        verifyIfSlugAlreadyExists(newNotebookSlug);

        URL picture = this.transformUrl.stringToUrl(createNotebookBody.getPicture());
        URL pictureThumbnail = this.transformUrl.stringToUrl(createNotebookBody.getPictureThumbnail());

        final Collection collectionFound = findOneCollectionBySlugOrThrow(createNotebookBody.getCollectionDto().getSlug());
        final Category categoryFound = findOneCategoryBySlugOrThrow(createNotebookBody.getCategoryDto().getSlug());

        final List<Material> materials = createNotebookBody.getMaterialsDto().stream()
                                                           .map(materialDto -> findOneMaterialBySlugOrThrow(materialDto.getSlug()))
                                                           .collect(Collectors.toList());

        final Notebook newNotebook = Notebook.builder()
                .name(createNotebookBody.getName())
                .slug(newNotebookSlug)
                .picture(picture)
                .pictureThumbnail(pictureThumbnail)
                .introduction(createNotebookBody.getIntroduction())
                .price(createNotebookBody.getPrice())
                .description(createNotebookBody.getDescription())
                .category(categoryFound)
                .collection(collectionFound)
                .materials(materials)
                .build();

        this.notebookRepository.save(newNotebook);

        createSecondaryPictures(newNotebookSlug, createNotebookBody.getSecondaryPicturesDto());
    }

    public NotebookDto getNotebookBySlug(String slug){
        return this.transformNotebook.notebookToDto(findOneNotebookBySlugOrThrow(slug));
    }

    public List<NotebookDto> findAllNotebooks(){
        return this.transformNotebook.notebooksToDto(this.notebookRepository.findAll());
    }

    public List<NotebookDto> findNotebooksByCategory(String categorySlug){
        Category categoryFound = findOneCategoryBySlugOrThrow(categorySlug);
        return this.transformNotebook.notebooksToDto(this.notebookRepository.findAllByCategory(categoryFound));
    }

    public List<NotebookDto> findNotebooksByCollection(String collectionSlug){
        Collection collectionFound = findOneCollectionBySlugOrThrow(collectionSlug);
        return this.transformNotebook.notebooksToDto(this.notebookRepository.findAllByCollection(collectionFound));
    }

    public NotebookDto changeNotebookAvailability(NotebookDto notebookDto){
        Notebook notebookToUpdate = findOneNotebookBySlugOrThrow(notebookDto.getSlug());
        notebookToUpdate.setAvailable(!notebookToUpdate.isAvailable());
        return this.transformNotebook.notebookToDto(this.notebookRepository.save(notebookToUpdate));
    }

    public void updateNotebookBySlug(String oldNotebookSlug, NotebookDto notebookDtoUpdated){

        Notebook notebookToUpdate = findOneNotebookBySlugOrThrow(oldNotebookSlug);
        List<SecondaryPicture> oldSecondaryPictures = this.secondaryPictureRepository.findAllByNotebook(notebookToUpdate);

        String newNotebookSlug = slugifyNotebookByName(notebookDtoUpdated.getName());
        if (!oldNotebookSlug.equals(newNotebookSlug)) {
            verifyIfSlugAlreadyExists(newNotebookSlug);
        }

        URL picture = this.transformUrl.stringToUrl(notebookDtoUpdated.getPicture());
        URL pictureThumbnail = this.transformUrl.stringToUrl(notebookDtoUpdated.getPictureThumbnail());

        notebookToUpdate.setName(notebookDtoUpdated.getName());
        notebookToUpdate.setSlug(newNotebookSlug);
        notebookToUpdate.setPicture(picture);
        notebookToUpdate.setPictureThumbnail(pictureThumbnail);
        notebookToUpdate.setIntroduction(notebookDtoUpdated.getIntroduction());
        notebookToUpdate.setPrice(notebookDtoUpdated.getPrice());
        notebookToUpdate.setDescription(notebookDtoUpdated.getDescription());

        Category categoryToUpdated = null;
        if (this.categoryRepository.existsBySlug(notebookDtoUpdated.getCategoryDto().getSlug())) {
            categoryToUpdated = findOneCategoryBySlugOrThrow(notebookDtoUpdated.getCategoryDto().getSlug());
        }
        notebookToUpdate.setCategory(categoryToUpdated);

        Collection collectionToUpdated = null;
        if (this.collectionRepository.existsBySlug(notebookDtoUpdated.getCollectionDto().getSlug())) {
            collectionToUpdated = findOneCollectionBySlugOrThrow(notebookDtoUpdated.getCollectionDto().getSlug());
        }
        notebookToUpdate.setCollection(collectionToUpdated);

        final List<Material> materials = notebookDtoUpdated.getMaterialsDto().stream()
                                                     .map(materialDto -> findOneMaterialBySlugOrThrow(materialDto.getSlug()))
                                                     .collect(Collectors.toList());
        notebookToUpdate.setMaterials(materials);

        this.notebookRepository.save(notebookToUpdate);

        compareOldSecPicListAndNewPicList(newNotebookSlug, oldSecondaryPictures, notebookDtoUpdated.getSecondaryPicturesDto());

    }

    public void deleteNotebookBySlug(String slug){
        Notebook notebookToDelete = findOneNotebookBySlugOrThrow(slug);
        this.notebookRepository.delete(notebookToDelete);
    }

    private String slugifyNotebookByName(String name) {
        return SlugifyUtil.stringToSlug(name);
    }

    private void verifyIfSlugAlreadyExists(String slug) {
        if (this.notebookRepository.existsBySlug(slug)) {
            throw new SlugNotebookAlreadyExistsException();
        }
    }

    private Notebook findOneNotebookBySlugOrThrow(String slug) {
        return this.notebookRepository.findBySlug(slug).orElseThrow(NotebookNotFoundException::new);
    }

    private Category findOneCategoryBySlugOrThrow(String slug) {
        return this.categoryRepository.findBySlug(slug).orElseThrow(CategoryNotFoundException::new);
    }

    private Collection findOneCollectionBySlugOrThrow(String slug) {
        return this.collectionRepository.findBySlug(slug).orElseThrow(CollectionNotFoundException::new);
    }

    private Material findOneMaterialBySlugOrThrow(String slug) {
        return this.materialRepository.findBySlug(slug).orElseThrow(MaterialNotFoundException::new);
    }

    private void createSecondaryPictures(String notebookSlug, List<SecondaryPictureDto> newSecondaryPicturesDto) {
        if (!newSecondaryPicturesDto.isEmpty()) {
            newSecondaryPicturesDto.forEach(secondaryPictureDto -> this.secondaryPictureService.createSecondaryPicture(notebookSlug, secondaryPictureDto));
        }
    }

    private void compareOldSecPicListAndNewPicList(String newNotebookSlug, List<SecondaryPicture> oldSecondaryPicturesDto, List<SecondaryPictureDto> newSecondaryPicturesDto) {

        final Notebook newNotebook = findOneNotebookBySlugOrThrow(newNotebookSlug);

        if (!newSecondaryPicturesDto.isEmpty()) {

//            Si dans l' "ancienne liste" d'image, une image existe, on met la relation Notebook à jour,
//            si elle n'existe pas dans la "nouvelle liste" on la supprime
            for (SecondaryPicture oldPicture : oldSecondaryPicturesDto) {
                boolean existsInNewList = false;
                SecondaryPicture secondaryPicture = findOneSecondaryPictureByPictureUrl(oldPicture.getPicture());

                for(SecondaryPictureDto newPicture : newSecondaryPicturesDto) {

                    if (oldPicture.getPicture().equals(newPicture.getPicture())) {
                        secondaryPicture.setNotebook(newNotebook);
                        this.secondaryPictureRepository.save(secondaryPicture);
                        existsInNewList = true;
                        break;
                    }
                }
                if (!existsInNewList) {
                    this.secondaryPictureRepository.delete(secondaryPicture);
                }
            }
//            Si dans la "nouvelle liste" d'image, une image n'existe pas dans l' "ancienne liste", on créé l'image,
            for (SecondaryPictureDto newPicture : newSecondaryPicturesDto) {
                boolean existsInOldList = false;

                for(SecondaryPicture oldPicture : oldSecondaryPicturesDto) {

                    if (oldPicture.getPicture() == this.transformUrl.stringToUrl(newPicture.getPicture())) {
                        existsInOldList = true;
                        break;
                    }
                }
                if (!existsInOldList) {
                    this.secondaryPictureService.createSecondaryPicture(newNotebook.getSlug(), newPicture);
                }
            }
        } else {
            oldSecondaryPicturesDto.forEach(secPicDto -> this.secondaryPictureRepository.delete(findOneSecondaryPictureByPictureUrl(secPicDto.getPicture())));
        }
    }

    private SecondaryPicture findOneSecondaryPictureByPictureUrl(URL pictureUrl) {
        return this.secondaryPictureRepository.findByPicture(pictureUrl).orElseThrow(SecondaryPictureNotFoundException::new);
    }
}

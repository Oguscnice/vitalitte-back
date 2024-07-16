package fr.vitalitte.vitalittebackend.stationery.materials.usecase;

import fr.vitalitte.vitalittebackend.common.models.Pagination;
import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.stationery.materialTypes.usecase.ConvertEumMaterialType;
import fr.vitalitte.vitalittebackend.stationery.materials.exception.MaterialNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.materials.exception.SlugMaterialAlreadyExistsException;
import fr.vitalitte.vitalittebackend.stationery.materialTypes.models.EMaterialType;
import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import fr.vitalitte.vitalittebackend.stationery.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.stationery.materials.rest.CreateMaterialBody;
import fr.vitalitte.vitalittebackend.stationery.materials.rest.MaterialDto;
import fr.vitalitte.vitalittebackend.stationery.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.stationery.notebook.persistence.NotebookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    MaterialRepository materialRepository;
    TransformMaterial transformMaterial;
    TransformUrl transformUrl;
    NotebookRepository notebookRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository, TransformMaterial transformMaterial, TransformUrl transformUrl, NotebookRepository notebookRepository) {
        this.materialRepository = materialRepository;
        this.transformMaterial = transformMaterial;
        this.transformUrl = transformUrl;
        this.notebookRepository = notebookRepository;
    }

    @Override
    public void createMaterial(CreateMaterialBody createMaterialBody){

        String newSlug = SlugifyUtil.stringToSlug(createMaterialBody.getName());

        if (this.materialRepository.existsBySlug(newSlug)) {
            throw new SlugMaterialAlreadyExistsException();
        }

        // transform et vérifie que ce soit bien un String valide en URL, le convertis ou jète une erreur
        URL urlPicture = this.transformUrl.stringToUrl(createMaterialBody.getPicture());
        URL urlPictureThumbnail = this.transformUrl.stringToUrl(createMaterialBody.getPictureThumbnail());

        EMaterialType materialType = ConvertEumMaterialType.stringToEMaterial(createMaterialBody.getMaterialType());
        final Material newMaterial = Material.builder()
                                             .name(createMaterialBody.getName())
                                             .slug(newSlug)
                                             .description(createMaterialBody.getDescription())
                                             .materialType(materialType)
                                             .price(createMaterialBody.getPrice())
                                             .picture(urlPicture)
                                             .pictureThumbnail(urlPictureThumbnail)
                                             .build();

        this.materialRepository.save(newMaterial);
    }

    @Override
    public List<MaterialDto> findAllMaterials(){
        return this.transformMaterial.materialsToDto(this.materialRepository.findAll());
    }

    @Override
    public List<MaterialDto> findMaterialsAvailableForCustomization(){
        return this.transformMaterial.materialsToDto(this.materialRepository.findAllMaterialsByIsAvailableForCustomization(true));
    }

    @Override
    public List<MaterialDto> getMaterialsPaginatedBySearchValue(PaginationItemBySearchValue paginationItemBySearchValue) {
        String searchValue = paginationItemBySearchValue.getSearchValue();
        Pagination pagination = paginationItemBySearchValue.getPagination();
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize());
        List<Material> materials = this.materialRepository.findAllByNameContainsIgnoreCaseOrderByName(searchValue, pageable);
        return this.transformMaterial.materialsToDto(materials);
    }

    @Override
    public long getCounterMaterialsBySearchValue(PaginationItemBySearchValue paginationItemBySearchValue) {
        String searchValue = paginationItemBySearchValue.getSearchValue();
        return this.materialRepository.countByNameContainsIgnoreCase(searchValue);
    }

    @Override
    public MaterialDto findMaterialBySlug(String materialSlug){
        return this.transformMaterial.materialToDto(this.findOneMaterialBySlugOrThrow(materialSlug));
    }

    @Override
    public void updateMaterialBySlug(String slug, MaterialDto materialDtoUpdated){

        Material materialToUpdate = this.findOneMaterialBySlugOrThrow(slug);

        String newSlug = SlugifyUtil.stringToSlug(materialDtoUpdated.getName());
        if (this.materialRepository.existsBySlug(newSlug) && (!slug.equals(newSlug))) {
            throw new SlugMaterialAlreadyExistsException();
        }

        List<Notebook> notebooks = this.notebookRepository.findAllByMaterialsContaining(materialToUpdate);
        EMaterialType materialTypeUpdated = ConvertEumMaterialType.stringToEMaterial(materialDtoUpdated.getMaterialType());

        // transform et vérifie que ce soit bien un String valide en URL, le convertis ou jète une erreur
        URL newUrlPicture = this.transformUrl.stringToUrl(materialDtoUpdated.getPicture());
        URL newUrlPictureThumbnail = this.transformUrl.stringToUrl(materialDtoUpdated.getPictureThumbnail());

        materialToUpdate.setName(materialDtoUpdated.getName());
        materialToUpdate.setSlug(newSlug);
        materialToUpdate.setPrice(materialDtoUpdated.getPrice());
        materialToUpdate.setDescription(materialDtoUpdated.getDescription());
        materialToUpdate.setPicture(newUrlPicture);
        materialToUpdate.setPictureThumbnail(newUrlPictureThumbnail);
        materialToUpdate.setMaterialType(materialTypeUpdated);

        for(Notebook notebook : notebooks){
            List<Material> materialsToUpdate = new ArrayList<>();
            List<Material> actualMaterials =  notebook.getMaterials();
            for(Material material : actualMaterials) {
                if(!material.getSlug().equals(slug)) {
                    materialsToUpdate.add(materialToUpdate);
                }
            }
            notebook.setMaterials(materialsToUpdate);
            this.notebookRepository.save(notebook);
        }
        this.materialRepository.save(materialToUpdate);
    }

    @Override
    public void changeMaterialAvailabilityForCustomization(MaterialDto materialDtoBody){

        Material materialToUpdate = this.findOneMaterialBySlugOrThrow(materialDtoBody.getSlug());
        materialToUpdate.setAvailableForCustomization(!materialToUpdate.isAvailableForCustomization());

        this.materialRepository.save(materialToUpdate);
    }

    @Override
    public void changeMaterialAvailability(MaterialDto materialDtoBody){

        Material materialToUpdate = this.findOneMaterialBySlugOrThrow(materialDtoBody.getSlug());
        materialToUpdate.setAvailable(!materialToUpdate.isAvailable());

        this.materialRepository.save(materialToUpdate);
    }

    @Override
    public void deleteMaterialBySlug(String slug){
        Material materialToDelete = this.findOneMaterialBySlugOrThrow(slug);

        List<Notebook> notebooks = this.notebookRepository.findAllByMaterialsContaining(materialToDelete);

        for(Notebook notebook : notebooks){
            List<Material> actualMaterials =  notebook.getMaterials();
            List<Material> materialsToUpdate = actualMaterials.stream()
                                               .filter(material -> !material.getSlug().equals(slug))
                                               .collect(Collectors.toList());
            notebook.setMaterials(materialsToUpdate);
            this.notebookRepository.save(notebook);
        }

        this.materialRepository.delete(materialToDelete);
    }

    private Material findOneMaterialBySlugOrThrow(String slug){
        return this.materialRepository.findBySlug(slug)
                .orElseThrow(MaterialNotFoundException::new);
    }
}

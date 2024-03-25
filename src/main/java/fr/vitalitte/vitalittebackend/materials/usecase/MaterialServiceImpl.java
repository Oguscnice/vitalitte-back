package fr.vitalitte.vitalittebackend.materials.usecase;

import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.materials.exception.MaterialNotFoundException;
import fr.vitalitte.vitalittebackend.materials.exception.SlugMaterialAlreadyExistsException;
import fr.vitalitte.vitalittebackend.materials.models.EMaterialType;
import fr.vitalitte.vitalittebackend.materials.models.Material;
import fr.vitalitte.vitalittebackend.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.materials.rest.CreateMaterialBody;
import fr.vitalitte.vitalittebackend.materials.rest.MaterialDto;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.notebook.persistence.NotebookRepository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService{
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
        URL newUrlPicture = this.transformUrl.stringToUrl(createMaterialBody.getPicture());

        EMaterialType materialType = ConvertEumMaterialType.stringToEMaterial(createMaterialBody.getMaterialType());
        final Material newMaterial = Material.builder()
                                             .name(createMaterialBody.getName())
                                             .slug(newSlug)
                                             .description(createMaterialBody.getDescription())
                                             .materialType(materialType)
                                             .price(createMaterialBody.getPrice())
                                             .picture(newUrlPicture)
                                             .build();

        this.materialRepository.save(newMaterial);
    }

    @Override
    public List<MaterialDto> findAllMaterials(){
        return this.transformMaterial.materialsToDto(this.materialRepository.findAll());
    }

    @Override
    public MaterialDto findMaterialBySlug(String materialSlug){
        return this.transformMaterial.materialToDto(this.materialRepository.findBySlug(materialSlug).orElseThrow(MaterialNotFoundException::new));
    }

    @Override
    public void updateMaterialBySlug(String slug, MaterialDto materialDtoUpdated){

        Material materialToUpdate = this.materialRepository.findBySlug(slug)
                                            .orElseThrow(MaterialNotFoundException::new);

        String newSlug = SlugifyUtil.stringToSlug(materialDtoUpdated.getName());
        if (this.materialRepository.existsBySlug(newSlug) && (!slug.equals(newSlug))) {
            throw new SlugMaterialAlreadyExistsException();
        }

        List<Notebook> notebooks = this.notebookRepository.findAllByMaterialsContaining(materialToUpdate);
        EMaterialType materialTypeUpdated = ConvertEumMaterialType.stringToEMaterial(materialDtoUpdated.getMaterialType());

        // transform et vérifie que ce soit bien un String valide en URL, le convertis ou jète une erreur
        URL newUrlPicture = this.transformUrl.stringToUrl(materialDtoUpdated.getPicture());

        materialToUpdate.setName(materialDtoUpdated.getName());
        materialToUpdate.setSlug(newSlug);
        materialToUpdate.setPrice(materialDtoUpdated.getPrice());
        materialToUpdate.setDescription(materialDtoUpdated.getDescription());
        materialToUpdate.setPicture(newUrlPicture);
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
    public void changeMAterialAvailabilityBySlug(String slug, boolean booleanValue){

        Material materialToUpdate = this.materialRepository.findBySlug(slug)
                                            .orElseThrow(MaterialNotFoundException::new);;
        materialToUpdate.setAvailable(booleanValue);

        this.materialRepository.save(materialToUpdate);
    }

    @Override
    public void deleteMaterialBySlug(String slug){
        Material materialToDelete = this.materialRepository.findBySlug(slug)
                                            .orElseThrow(MaterialNotFoundException::new);

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
}

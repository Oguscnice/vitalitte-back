package fr.vitalitte.vitalittebackend.stationery.materials.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.common.usecase.FileService;
import fr.vitalitte.vitalittebackend.common.usecase.SlugifyUtil;
import fr.vitalitte.vitalittebackend.stationery.materialTypes.models.EMaterialType;
import fr.vitalitte.vitalittebackend.stationery.materialTypes.usecase.ConvertEumMaterialType;
import fr.vitalitte.vitalittebackend.stationery.materials.exception.MaterialNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import fr.vitalitte.vitalittebackend.stationery.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.stationery.materials.rest.CreateMaterialBody;
import fr.vitalitte.vitalittebackend.stationery.materials.rest.MaterialDto;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    MaterialRepository materialRepository;
    TransformMaterial transformMaterial;
    ProductRepository productRepository;
    SlugifyUtil slugifyUtil;
    FileService fileService;


    public MaterialServiceImpl(MaterialRepository materialRepository, TransformMaterial transformMaterial, ProductRepository productRepository, SlugifyUtil slugifyUtil, FileService fileService) {
        this.materialRepository = materialRepository;
        this.transformMaterial = transformMaterial;
        this.productRepository = productRepository;
        this.slugifyUtil = slugifyUtil;
        this.fileService = fileService;
    }

    @Override
    public void createMaterial(CreateMaterialBody createMaterialBody){

        String materialSlug = slugifyUtil.stringToSlug(createMaterialBody.getName());
        slugifyUtil.verifyIfSlugAlreadyExists(materialSlug);

        fileService.createFile(createMaterialBody.getPictureDto(), true, materialSlug);

        EMaterialType materialType = ConvertEumMaterialType.StringToEnum(createMaterialBody.getMaterialType());
        final Material newMaterial = Material.builder()
                                             .name(createMaterialBody.getName())
                                             .slug(materialSlug)
                                             .description(createMaterialBody.getDescription())
                                             .materialType(materialType)
                                             .price(createMaterialBody.getPrice())
                                             .build();

        this.materialRepository.save(newMaterial);
    }

    @Override
    public List<MaterialDto> findAllMaterials() {
        //TODO: vérifier que ce soit pour l'admin uniquement
        return this.transformMaterial.materialsToDto(this.materialRepository.findAll(), true);
    }

//    @Override
//    public List<MaterialDto> findMaterialsAvailableForCustomization(){
//        return this.transformMaterial.materialsToDto(this.materialRepository.findAllMaterialsByIsAvailableForCustomizationOrderByName(true));
//    }

    @Override
    public Page<MaterialDto> getMaterialsPaginatedBySearchValue(PaginationItemBySearchValue paginationItemBySearchValue) {

        String value = paginationItemBySearchValue.getSearchValue();
        Pageable pageable = PageRequest.of(paginationItemBySearchValue.getPageableValues().getPageNumber(), paginationItemBySearchValue.getPageableValues().getPageSize());
        Page<Material> materialPage = this.materialRepository.findAllByNameContainsIgnoreCaseOrderByName(value, pageable);
        List<MaterialDto> materialDtoList = this.transformMaterial.materialsToDto(materialPage.getContent(), true);

        return new PageImpl<>(materialDtoList, pageable, materialPage.getTotalElements());
    }

    @Override
    public MaterialDto findMaterialBySlug(String materialSlug){
        return this.transformMaterial.materialToDto(this.findOneMaterialBySlugOrThrow(materialSlug), true);
    }

    @Override
    public void updateMaterialBySlug(String slug, MaterialDto materialDtoUpdated){

        Material materialToUpdate = this.findOneMaterialBySlugOrThrow(slug);

        String newMaterialSlug = slugifyUtil.stringToSlug(materialDtoUpdated.getName());
        if (!slug.equals(newMaterialSlug)) {
            slugifyUtil.verifyIfSlugAlreadyExists(newMaterialSlug);
            materialToUpdate.setSlug(newMaterialSlug);
        }

        List<Product> products = this.productRepository.findAllByMaterialsContaining(materialToUpdate);
        EMaterialType materialTypeUpdated = ConvertEumMaterialType.StringToEnum(materialDtoUpdated.getMaterialType());

        materialToUpdate.setName(materialDtoUpdated.getName());
        materialToUpdate.setPrice(materialDtoUpdated.getPrice());
        materialToUpdate.setDescription(materialDtoUpdated.getDescription());
        materialToUpdate.setMaterialType(materialTypeUpdated);
        fileService.updateFile(materialDtoUpdated.getPictureDto(), newMaterialSlug, true);

        this.materialRepository.save(materialToUpdate);
    }

    @Override
    public void changeMaterialAvailabilityForCustomization(MaterialDto materialDtoBody) {

        Material materialToUpdate = this.findOneMaterialBySlugOrThrow(materialDtoBody.getSlug());
        materialToUpdate.setAvailableForCustomization(!materialToUpdate.isAvailableForCustomization());

        this.materialRepository.save(materialToUpdate);
    }

    @Override
    public void changeMaterialAvailability(MaterialDto materialDtoBody) {

        Material materialToUpdate = this.findOneMaterialBySlugOrThrow(materialDtoBody.getSlug());
        materialToUpdate.setAvailable(!materialToUpdate.isAvailable());

        this.materialRepository.save(materialToUpdate);
    }

    @Override
    public void deleteMaterialBySlug(String slug) {
        Material materialToDelete = this.findOneMaterialBySlugOrThrow(slug);

        List<Product> products = this.productRepository.findAllByMaterialsContaining(materialToDelete);

        for (Product product : products) {
            List<Material> actualMaterials =  product.getMaterials();
            List<Material> materialsToUpdate = actualMaterials.stream()
                                               .filter(material -> !material.getSlug().equals(slug))
                                               .collect(Collectors.toList());
            product.setMaterials(materialsToUpdate);
            this.productRepository.save(product);
        }

        this.fileService.deleteAllFilesByLinkedSlug(slug);
        this.materialRepository.delete(materialToDelete);
    }

    private Material findOneMaterialBySlugOrThrow(String slug) {
        return this.materialRepository.findBySlug(slug).orElseThrow(MaterialNotFoundException::new);
    }
}

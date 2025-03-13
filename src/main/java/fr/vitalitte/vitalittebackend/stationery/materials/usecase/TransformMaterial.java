package fr.vitalitte.vitalittebackend.stationery.materials.usecase;

import fr.vitalitte.vitalittebackend.common.models.FileEntity;
import fr.vitalitte.vitalittebackend.common.persistence.FileRepository;
import fr.vitalitte.vitalittebackend.common.usecase.TransformFile;
import fr.vitalitte.vitalittebackend.stationery.materialTypes.usecase.ConvertEumMaterialType;
import fr.vitalitte.vitalittebackend.stationery.materials.exception.MaterialNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import fr.vitalitte.vitalittebackend.stationery.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.stationery.materials.rest.MaterialDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static fr.vitalitte.vitalittebackend.common.usecase.ListMapperUtil.mapList;

@Service
public class TransformMaterial {

    MaterialRepository materialRepository;
    FileRepository fileRepository;
    TransformFile transformFile;

    public TransformMaterial(FileRepository fileRepository, MaterialRepository materialRepository, TransformFile transformFile) {
        this.fileRepository = fileRepository;
        this.materialRepository = materialRepository;
        this.transformFile = transformFile;
    }

    public MaterialDto materialToDto(Material material, boolean isPriceVisible) {

        String materialType = ConvertEumMaterialType.EnumToString(material.getMaterialType());
        FileEntity file = fileRepository.findByLinkedSlugAndIsMainPictureTrue(material.getSlug());

        return MaterialDto.builder()
                .name(material.getName())
                .slug(material.getSlug())
                .description(material.getDescription())
                .materialType(materialType)
                .price(isPriceVisible ? material.getPrice() : BigDecimal.ZERO)
                .pictureDto(transformFile.fileToDto(file))
                .isAvailable(material.isAvailable())
                .isAvailableForCustomization(material.isAvailableForCustomization())
                .build();
    }

    public List<MaterialDto> materialsToDto(List<Material> materials, boolean isPriceVisible) {
        return mapList(material -> materialToDto(material, isPriceVisible), materials);
    }

    public Material DtoToMaterial(MaterialDto materialDto) {
        return this.materialRepository.findBySlug(materialDto.getSlug())
                        .orElseThrow(MaterialNotFoundException::new);
    }

    public List<Material> DtosToMaterials(List<MaterialDto> materialsDto) {
        return mapList(this::DtoToMaterial, materialsDto);
    }
}

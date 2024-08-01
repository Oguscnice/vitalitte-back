package fr.vitalitte.vitalittebackend.stationery.materials.usecase;

import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.stationery.materialTypes.usecase.ConvertEumMaterialType;
import fr.vitalitte.vitalittebackend.stationery.materials.exception.MaterialNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import fr.vitalitte.vitalittebackend.stationery.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.stationery.materials.rest.MaterialDto;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;
import java.util.List;

@Service
public class TransformMaterial {

    MaterialRepository materialRepository;
    TransformUrl transformUrl;

    public TransformMaterial(MaterialRepository materialRepository, TransformUrl transformUrl) {
        this.materialRepository = materialRepository;
        this.transformUrl = transformUrl;
    }

    public MaterialDto materialToDto(Material material) {

        String materialType = ConvertEumMaterialType.EnumToString(material.getMaterialType());
        String picture = this.transformUrl.urlToString(material.getPicture());
        String pictureThumbnail = this.transformUrl.urlToString(material.getPictureThumbnail());

        return MaterialDto.builder()
                .name(material.getName())
                .slug(material.getSlug())
                .description(material.getDescription())
                .materialType(materialType)
                .price(material.getPrice())
                .picture(picture)
                .pictureThumbnail(pictureThumbnail)
                .isAvailable(material.isAvailable())
                .isAvailableForCustomization(material.isAvailableForCustomization())
                .build();
    }

    public List<MaterialDto> materialsToDto(List<Material> materials) {
        return mapList(this::materialToDto, materials);
    }

    public Material DtoToMaterial(MaterialDto materialDto) {
        return this.materialRepository.findBySlug(materialDto.getSlug())
                        .orElseThrow(MaterialNotFoundException::new);
    }

    public List<Material> DtosToMaterials(List<MaterialDto> materialsDto) {
        return mapList(this::DtoToMaterial, materialsDto);
    }
}

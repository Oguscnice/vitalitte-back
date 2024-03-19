package fr.vitalitte.vitalittebackend.materials.usecase;

import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.materials.exception.MaterialNotFoundException;
import fr.vitalitte.vitalittebackend.materials.models.Material;
import fr.vitalitte.vitalittebackend.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.materials.rest.MaterialDto;
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

    public MaterialDto materialToDto(Material material){

        String materialType = ConvertEumMaterialType.EMaterialToString(material.getMaterialType());
        String picture = this.transformUrl.urlToString(material.getPicture());

        return MaterialDto.builder()
                .name(material.getName())
                .slug(material.getSlug())
                .description(material.getDescription())
                .materialType(materialType)
                .price(material.getPrice())
                .picture(picture)
                .isAvailable(material.isAvailable())
                .build();
    }
    public List<MaterialDto> materialsToDto(List<Material> materials) {
        return mapList(this::materialToDto, materials);
    }

    public Material DtoToMaterial(MaterialDto materialDto){
        return this.materialRepository.findBySlug(materialDto.getSlug())
                        .orElseThrow(MaterialNotFoundException::new);
    }
    public List<Material> DtosToMaterials(List<MaterialDto> materialsDto) {
        return mapList(this::DtoToMaterial, materialsDto);
    }
}

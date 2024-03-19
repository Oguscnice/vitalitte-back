package fr.vitalitte.vitalittebackend.materials.usecase;

import fr.vitalitte.vitalittebackend.materials.rest.CreateMaterialBody;
import fr.vitalitte.vitalittebackend.materials.rest.MaterialDto;

import java.util.List;

public interface MaterialService {
    void createMaterial(CreateMaterialBody createMaterialBody);
    List<MaterialDto> findAllMaterials();
    void changeMAterialAvailabilityBySlug(String slug, boolean booleanValue);
    void updateMaterialBySlug(String slug, MaterialDto materialDtoUpdated);
    void deleteMaterialBySlug(String slug);
}

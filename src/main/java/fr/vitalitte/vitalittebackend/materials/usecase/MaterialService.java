package fr.vitalitte.vitalittebackend.materials.usecase;

import fr.vitalitte.vitalittebackend.materials.rest.CreateMaterialBody;
import fr.vitalitte.vitalittebackend.materials.rest.MaterialDto;

import java.util.List;

public interface MaterialService {
    void createMaterial(CreateMaterialBody createMaterialBody);
    List<MaterialDto> findAllMaterials();
    List<MaterialDto> findMaterialsAvailableForCustomization();
    MaterialDto findMaterialBySlug(String materialSlug);
    void changeMaterialAvailabilityForCustomization(MaterialDto materialDtoBody);
    void changeMaterialAvailability(MaterialDto materialDtoBody);
    void updateMaterialBySlug(String slug, MaterialDto materialDtoUpdated);
    void deleteMaterialBySlug(String slug);
}

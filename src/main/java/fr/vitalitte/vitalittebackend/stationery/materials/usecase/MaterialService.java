package fr.vitalitte.vitalittebackend.stationery.materials.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.stationery.materials.rest.CreateMaterialBody;
import fr.vitalitte.vitalittebackend.stationery.materials.rest.MaterialDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaterialService {
    void createMaterial(CreateMaterialBody createMaterialBody);
    List<MaterialDto> findAllMaterials();
    Page<MaterialDto> getMaterialsPaginatedBySearchValue(PaginationItemBySearchValue paginationItemBySearchValue);
//    List<MaterialDto> findMaterialsAvailableForCustomization();
    MaterialDto findMaterialBySlug(String materialSlug);
    void changeMaterialAvailabilityForCustomization(MaterialDto materialDtoBody);
    void changeMaterialAvailability(MaterialDto materialDtoBody);
    void updateMaterialBySlug(String slug, MaterialDto materialDtoUpdated);
    void deleteMaterialBySlug(String slug);
}

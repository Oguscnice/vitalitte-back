package fr.vitalitte.vitalittebackend.stationery.materials.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.stationery.materials.usecase.MaterialService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("/paginated")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<MaterialDto>> getMaterialsPaginatedBySearchValue(@RequestBody PaginationItemBySearchValue paginationItemBySearchValue) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.materialService.getMaterialsPaginatedBySearchValue(paginationItemBySearchValue));
    }

    @PostMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createMaterial(@RequestBody CreateMaterialBody createMaterialBody) {
        this.materialService.createMaterial(createMaterialBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Matériel créé avec succès."));
    }

    @GetMapping("/availability-for-customization")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<MaterialDto> getAllMaterialsAvailableForCustomization() {
        return this.materialService.findMaterialsAvailableForCustomization();
    }

    @GetMapping("/{slug}")
//    @PreAuthorize("hasRole('ADMIN')")
    public MaterialDto getMaterialBySlug(@PathVariable String slug) {
        return this.materialService.findMaterialBySlug(slug);
    }

    @GetMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<MaterialDto> getAllMaterials() {
        return this.materialService.findAllMaterials();
    }

    @PutMapping("/availability-for-customization")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> changeAvailabilityForCustomizationBySlug(@RequestBody MaterialDto materialDtoBody) {
        this.materialService.changeMaterialAvailabilityForCustomization(materialDtoBody);
        return ResponseEntity.ok(new MessageResponse("Disponibilité du Matériel mise à jour avec succès."));
    }

    @PutMapping("/availability")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> changeAvailability(@RequestBody MaterialDto materialDtoBody) {
        this.materialService.changeMaterialAvailability(materialDtoBody);
        return ResponseEntity.ok(new MessageResponse("Disponibilité du Matériel mise à jour avec succès."));
    }

    @PutMapping("/{slug}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> updateMaterialBySlug(@PathVariable String slug, @RequestBody MaterialDto materialDto) {
        this.materialService.updateMaterialBySlug(slug, materialDto);
        return ResponseEntity.ok(new MessageResponse("Matériel mise à jour avec succès."));
    }

    @DeleteMapping("/{slug}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteMaterialBySlug(@PathVariable String slug) {
        this.materialService.deleteMaterialBySlug(slug);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Matériel supprimé avec succès."));
    }
}

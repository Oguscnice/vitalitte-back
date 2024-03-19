package fr.vitalitte.vitalittebackend.materials.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.materials.models.EMaterialType;
import fr.vitalitte.vitalittebackend.materials.usecase.MaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/materials")
public class MaterialController {
    MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }
    @PostMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createMaterial(@RequestBody CreateMaterialBody createMaterialBody) {
        this.materialService.createMaterial(createMaterialBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Matériel créé avec succès."));
    }
    @GetMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<MaterialDto> getAllMaterials() {
        return this.materialService.findAllMaterials();
    }
    @GetMapping("/types")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<EMaterialType> getAllMaterialsTypeEnum() {

        List<EMaterialType> types = new ArrayList<>();
        types.add(EMaterialType.COUVERTURE);
        types.add(EMaterialType.RELIURE);
        types.add(EMaterialType.PAPIER);

        return types;
    }

    @PutMapping("/availability/{slug}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> changeAvailabilityBySlug(@PathVariable String slug, @RequestBody boolean booleanValue) {
        this.materialService.changeMAterialAvailabilityBySlug(slug, booleanValue);
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

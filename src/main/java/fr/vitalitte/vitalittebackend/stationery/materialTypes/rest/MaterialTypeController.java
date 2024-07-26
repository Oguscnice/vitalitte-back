package fr.vitalitte.vitalittebackend.stationery.materialTypes.rest;

import fr.vitalitte.vitalittebackend.stationery.materialTypes.models.EMaterialType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/materialTypes")
public class MaterialTypeController {

    @GetMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<EMaterialType> getAllMaterialsTypeEnum() {

        List<EMaterialType> types = new ArrayList<>();
        types.add(EMaterialType.COUVERTURE);
        types.add(EMaterialType.RELIURE);
        types.add(EMaterialType.PAPIER);

        return types;
    }
}

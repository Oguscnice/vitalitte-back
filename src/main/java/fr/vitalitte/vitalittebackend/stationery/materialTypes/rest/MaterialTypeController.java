package fr.vitalitte.vitalittebackend.stationery.materialTypes.rest;

import fr.vitalitte.vitalittebackend.stationery.materialTypes.usecase.ConvertEumMaterialType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/material-types")
public class MaterialTypeController {

    @GetMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<String> getAllMaterialsTypeEnum() {
        return ConvertEumMaterialType.AllEnumsToStringArray();
    }
}

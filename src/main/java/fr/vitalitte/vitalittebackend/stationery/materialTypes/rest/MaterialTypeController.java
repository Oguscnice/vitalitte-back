package fr.vitalitte.vitalittebackend.stationery.materialTypes.rest;

import fr.vitalitte.vitalittebackend.authentification.usecase.JwtService;
import fr.vitalitte.vitalittebackend.stationery.materialTypes.usecase.ConvertEumMaterialType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/material-types")
public class MaterialTypeController {

    private JwtService jwtService;

    public MaterialTypeController(final JwtService jwtService) {}

    @GetMapping("")
    @PreAuthorize("@jwtService.isRoleCheckAndTokenNotExpired('ROLE_ADMIN')")
    public List<String> getAllMaterialsTypeEnum() {
        return ConvertEumMaterialType.AllEnumsToStringArray();
    }
}

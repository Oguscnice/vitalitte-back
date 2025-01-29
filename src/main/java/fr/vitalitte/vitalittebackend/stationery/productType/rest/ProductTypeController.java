package fr.vitalitte.vitalittebackend.stationery.productType.rest;

import fr.vitalitte.vitalittebackend.stationery.productType.usecase.ConvertEnumProductType;
import fr.vitalitte.vitalittebackend.stationery.productType.usecase.ProductTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-types")
public class ProductTypeController {

    ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping("/has-product")
    public List<String> getAllProductTypesEnumIfProductAvailable() {
        return this.productTypeService.getAllProductTypesEnumIfProductAvailable();
    }

    @GetMapping("")
    @PreAuthorize("@jwtService.isRoleAdminAndTokenNotExpired()")
    public List<String> getAllProductTypesEnum() {
        return this.productTypeService.getAllProductTypes();
    }
}

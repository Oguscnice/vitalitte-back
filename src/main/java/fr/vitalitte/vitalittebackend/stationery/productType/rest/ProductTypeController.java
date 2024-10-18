package fr.vitalitte.vitalittebackend.stationery.productType.rest;

import fr.vitalitte.vitalittebackend.stationery.productType.usecase.ConvertEnumProductType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-types")
public class ProductTypeController {

    @GetMapping("")
    public List<String> getAllProductTypesEnum() {
        return ConvertEnumProductType.AllEnumsToStringArray();
    }
}

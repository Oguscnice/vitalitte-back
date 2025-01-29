package fr.vitalitte.vitalittebackend.stationery.product.rest;

import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import fr.vitalitte.vitalittebackend.stationery.common.rest.CategoryDtoAndCollectionDto;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.usecase.ProductService;
import fr.vitalitte.vitalittebackend.stationery.productType.usecase.ConvertEnumProductType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    @PreAuthorize("@jwtService.isRoleAdminAndTokenNotExpired()")
    public ResponseEntity<MessageResponse> createProduct(@RequestBody CreateProductBody createProductBody) {
        this.productService.createProduct(createProductBody);
        String message = String.format("%s créé avec succès.", ConvertEnumProductType.StringToSingular(createProductBody.getProductType()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse(message));
    }

    @PostMapping("/type-{productType}/filter/category-collection")
    public ResponseEntity<List<ProductDto>> getProductFilteredByCategoryAndCollection(@PathVariable String productType, @RequestBody CategoryDtoAndCollectionDto categoryDtoAndCollectionDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productService.findAllProductsFilteredByCategoryAndCollection(productType, categoryDtoAndCollectionDto));
    }

    @GetMapping("/category/{categorySlug}")
    public ResponseEntity<List<ProductDto>> getProductByCategorySlug(@PathVariable String categorySlug) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productService.findAllProductsByCategorySlug(categorySlug));
    }

    @GetMapping("/collection/{collectionSlug}")
    public ResponseEntity<List<ProductDto>> getProductByCollectionSlug(@PathVariable String collectionSlug) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productService.findAllProductsByCollectionSlug(collectionSlug));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ProductDto> getProductBySlug(@PathVariable String slug) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productService.getProductBySlug(slug));
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productService.findAllProducts());
    }

    @PutMapping("/availability")
    @PreAuthorize("@jwtService.isRoleAdminAndTokenNotExpired()")
    public ResponseEntity<MessageResponse> updateAvailabilityProduct(@RequestBody ProductDto productDto) {
        ProductDto productDtoUpdated =  this.productService.changeProductAvailability(productDto);
        String message = String.format("%s %s est %s.",
                ConvertEnumProductType.StringToSingular(productDto.getProductType()),
                productDtoUpdated.getName(),
                productDtoUpdated.isAvailable() ? "Disponible" : "Indisponible");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse(message));
    }

    @PutMapping("")
    @PreAuthorize("@jwtService.isRoleAdminAndTokenNotExpired()")
    public ResponseEntity<MessageResponse> updateProduct(@RequestBody ProductDto productDto) {
        this.productService.updateProduct(productDto);
        String message = String.format("%s %s mis à jour avec succès.",
                ConvertEnumProductType.StringToSingular(productDto.getProductType()),
                productDto.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse(message));
    }

    @DeleteMapping("/{slug}")
    @PreAuthorize("@jwtService.isRoleAdminAndTokenNotExpired()")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable String slug) {
        Product deletedProduct = this.productService.deleteProductBySlug(slug);
        String productType = ConvertEnumProductType.EnumToString(deletedProduct.getEProductType());
        String message = String.format("%s %s supprimé avec succès.",
                ConvertEnumProductType.StringToSingular(productType),
                deletedProduct.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse(message));
    }
}

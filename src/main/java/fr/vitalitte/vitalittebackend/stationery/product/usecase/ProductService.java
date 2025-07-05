package fr.vitalitte.vitalittebackend.stationery.product.usecase;

import fr.vitalitte.vitalittebackend.stationery.common.rest.CategoryDtoAndCollectionDto;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.rest.CreateProductBody;
import fr.vitalitte.vitalittebackend.stationery.product.rest.ProductDto;

import java.util.List;

public interface ProductService {
    void createProduct(CreateProductBody createProductBody);
    ProductDto getProductBySlug(String slug, boolean isMaterialPriceVisible);
    List<ProductDto> findAllProducts();
    List<ProductDto> findAllProductsByCategorySlug(String categorySlug);
    List<ProductDto> findAllProductsByCollectionSlug(String collectionSlug);
    List<ProductDto> findAllProductsFilteredByCategoryAndCollection(String productType, CategoryDtoAndCollectionDto categoryDtoAndCollectionDto);
    ProductDto changeProductAvailability(ProductDto productDto);
    void updateProduct(ProductDto productDtoUpdated);
    Product deleteProductBySlug(String slug);
}

package fr.vitalitte.vitalittebackend.stationery.product.usecase;

import fr.vitalitte.vitalittebackend.stationery.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.stationery.category.usecase.TransformCategory;
import fr.vitalitte.vitalittebackend.stationery.collection.exception.CollectionNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.collection.persistence.CollectionRepository;
import fr.vitalitte.vitalittebackend.stationery.collection.rest.CollectionDto;
import fr.vitalitte.vitalittebackend.stationery.collection.usecase.TransformCollection;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.stationery.materials.usecase.TransformMaterial;
import fr.vitalitte.vitalittebackend.stationery.product.exception.ProductNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import fr.vitalitte.vitalittebackend.stationery.product.rest.ProductDto;
import fr.vitalitte.vitalittebackend.stationery.productType.usecase.ConvertEnumProductType;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.persistence.SecondaryPictureRepository;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.rest.SecondaryPictureDto;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.usecase.TransformSecondaryPicture;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

import java.util.List;

@Service
public class TransformProduct {

    TransformUrl transformUrl;
    TransformCategory transformCategory;
    TransformCollection transformCollection;
    TransformMaterial transformMaterial;
    TransformSecondaryPicture transformSecondaryPicture;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    CollectionRepository collectionRepository;
    SecondaryPictureRepository secondaryPictureRepository;

    public TransformProduct(TransformUrl transformUrl, TransformCategory transformCategory, TransformCollection transformCollection, TransformMaterial transformMaterial, TransformSecondaryPicture transformSecondaryPicture, ProductRepository productRepository, CategoryRepository categoryRepository, CollectionRepository collectionRepository, SecondaryPictureRepository secondaryPictureRepository) {
        this.transformUrl = transformUrl;
        this.transformCategory = transformCategory;
        this.transformCollection = transformCollection;
        this.transformMaterial = transformMaterial;
        this.transformSecondaryPicture = transformSecondaryPicture;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.collectionRepository = collectionRepository;
        this.secondaryPictureRepository = secondaryPictureRepository;
    }

    public ProductDto productToDto(Product product) {

        String picture = this.transformUrl.urlToString(product.getPicture());
        String pictureThumbnail = this.transformUrl.urlToString(product.getPictureThumbnail());
        String productType = ConvertEnumProductType.EnumToString(product.getEProductType());

        CategoryDto categoryDto = null;
        if(product.getCategory() != null){
            Category categoryFound = this.categoryRepository.findBySlug(product.getCategory().getSlug()).orElseThrow(CategoryNotFoundException::new);
            categoryDto = this.transformCategory.categoryToDto(categoryFound);
        }

        CollectionDto collectionDto = null;
        if(product.getCollection() != null) {
            Collection collectionFound = this.collectionRepository.findBySlug(product.getCollection().getSlug()).orElseThrow(CollectionNotFoundException::new);
            collectionDto = this.transformCollection.collectionToDto(collectionFound);
        }

        List<SecondaryPicture> secondaryPictures = this.secondaryPictureRepository.findAllByProduct(product);
        List<SecondaryPictureDto> secondaryPicturesDto = this.transformSecondaryPicture.picturesToDtos(secondaryPictures);

        return ProductDto.builder()
                .name(product.getName())
                .slug(product.getSlug())
                .picture(picture)
                .pictureThumbnail(pictureThumbnail)
                .introduction(product.getIntroduction())
                .price(product.getPrice())
                .secondaryPicturesDto(secondaryPicturesDto)
                .description(product.getDescription())
                .materialsDto(this.transformMaterial.materialsToDto(product.getMaterials()))
                .categoryDto(categoryDto)
                .collectionDto(collectionDto)
                .isAvailable(product.isAvailable())
                .productType(productType)
                .build();
    }
    public List<ProductDto> productsToDto(List<Product> products) {
        return mapList(this::productToDto, products);
    }

    public Product dtoToProduct(ProductDto productDto) {
        return this.productRepository.findBySlug(productDto.getSlug())
                .orElseThrow(() -> new ProductNotFoundException(productDto.getProductType()));
    }

    public List<Product> dtosToProducts(List<ProductDto> productDtos) {
        return mapList(this::dtoToProduct, productDtos);
    }
}

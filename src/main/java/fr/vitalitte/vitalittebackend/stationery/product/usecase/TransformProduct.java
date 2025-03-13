package fr.vitalitte.vitalittebackend.stationery.product.usecase;

import fr.vitalitte.vitalittebackend.common.models.FileEntity;
import fr.vitalitte.vitalittebackend.common.persistence.FileRepository;
import fr.vitalitte.vitalittebackend.common.usecase.TransformFile;
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
import fr.vitalitte.vitalittebackend.stationery.materials.usecase.TransformMaterial;
import fr.vitalitte.vitalittebackend.stationery.product.exception.ProductNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import fr.vitalitte.vitalittebackend.stationery.product.rest.ProductDto;
import fr.vitalitte.vitalittebackend.stationery.productType.usecase.ConvertEnumProductType;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.vitalitte.vitalittebackend.common.usecase.ListMapperUtil.mapList;

@Service
public class TransformProduct {

    TransformCategory transformCategory;
    TransformCollection transformCollection;
    TransformMaterial transformMaterial;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    CollectionRepository collectionRepository;
    FileRepository fileRepository;
    TransformFile transformFile;

    public TransformProduct(CategoryRepository categoryRepository, CollectionRepository collectionRepository, FileRepository fileRepository, ProductRepository productRepository, TransformCategory transformCategory, TransformCollection transformCollection, TransformFile transformFile, TransformMaterial transformMaterial) {
        this.categoryRepository = categoryRepository;
        this.collectionRepository = collectionRepository;
        this.fileRepository = fileRepository;
        this.productRepository = productRepository;
        this.transformCategory = transformCategory;
        this.transformCollection = transformCollection;
        this.transformFile = transformFile;
        this.transformMaterial = transformMaterial;
    }

    public ProductDto productToDto(Product product, boolean isMaterialPriceVisible) {

        FileEntity file = fileRepository.findByLinkedSlugAndIsMainPictureTrue(product.getSlug());
        String productType = ConvertEnumProductType.EnumToString(product.getEProductType());

        CategoryDto categoryDto = null;
        if (product.getCategory() != null) {
            Category categoryFound = categoryRepository.findBySlug(product.getCategory().getSlug()).orElseThrow(CategoryNotFoundException::new);
            categoryDto = transformCategory.categoryToDto(categoryFound);
        }

        CollectionDto collectionDto = null;
        if (product.getCollection() != null) {
            Collection collectionFound = collectionRepository.findBySlug(product.getCollection().getSlug()).orElseThrow(CollectionNotFoundException::new);
            collectionDto = transformCollection.collectionToDto(collectionFound);
        }

        List<FileEntity> secondaryPictures = fileRepository.findAllByLinkedSlugAndIsMainPictureFalse(product.getSlug());

        return ProductDto.builder()
                .name(product.getName())
                .slug(product.getSlug())
                .pictureDto(transformFile.fileToDto(file))
                .introduction(product.getIntroduction())
                .price(product.getPrice())
                .secondaryPicturesDto(transformFile.filesToDtos(secondaryPictures))
                .description(product.getDescription())
                .materialsDto(transformMaterial.materialsToDto(product.getMaterials(), isMaterialPriceVisible))
                .categoryDto(categoryDto)
                .collectionDto(collectionDto)
                .isAvailable(product.isAvailable())
                .productType(productType)
                .build();
    }

    public List<ProductDto> productsToDto(List<Product> products, boolean isMaterialPriceVisible) {
        return mapList(product -> productToDto(product, isMaterialPriceVisible), products);
    }

    public Product dtoToProduct(ProductDto productDto) {
        return this.productRepository.findBySlug(productDto.getSlug())
                .orElseThrow(() -> new ProductNotFoundException(productDto.getProductType()));
    }

    public List<Product> dtosToProducts(List<ProductDto> productDtos) {
        return mapList(this::dtoToProduct, productDtos);
    }
}

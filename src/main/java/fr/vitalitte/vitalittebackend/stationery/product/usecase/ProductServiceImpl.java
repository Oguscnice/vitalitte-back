package fr.vitalitte.vitalittebackend.stationery.product.usecase;

import fr.vitalitte.vitalittebackend.common.rest.FileDto;
import fr.vitalitte.vitalittebackend.common.usecase.FileService;
import fr.vitalitte.vitalittebackend.stationery.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.stationery.category.usecase.TransformCategory;
import fr.vitalitte.vitalittebackend.stationery.collection.exception.CollectionNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.collection.persistence.CollectionRepository;
import fr.vitalitte.vitalittebackend.common.usecase.SlugifyUtil;
import fr.vitalitte.vitalittebackend.stationery.common.rest.CategoryDtoAndCollectionDto;
import fr.vitalitte.vitalittebackend.stationery.materials.exception.MaterialNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import fr.vitalitte.vitalittebackend.stationery.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.stationery.materials.usecase.TransformMaterial;
import fr.vitalitte.vitalittebackend.stationery.product.exception.ProductNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import fr.vitalitte.vitalittebackend.stationery.product.rest.CreateProductBody;
import fr.vitalitte.vitalittebackend.stationery.product.rest.ProductDto;
import fr.vitalitte.vitalittebackend.stationery.productType.models.EProductType;
import fr.vitalitte.vitalittebackend.stationery.productType.usecase.ConvertEnumProductType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    MaterialRepository materialRepository;
    CollectionRepository collectionRepository;
    TransformProduct transformProduct;
    TransformMaterial transformMaterial;
    TransformCategory transformCategory;
    SlugifyUtil slugifyUtil;
    FileService fileService;

    public ProductServiceImpl(CategoryRepository categoryRepository, CollectionRepository collectionRepository, FileService fileService, MaterialRepository materialRepository, ProductRepository productRepository, SlugifyUtil slugifyUtil, TransformCategory transformCategory, TransformMaterial transformMaterial, TransformProduct transformProduct) {
        this.categoryRepository = categoryRepository;
        this.collectionRepository = collectionRepository;
        this.fileService = fileService;
        this.materialRepository = materialRepository;
        this.productRepository = productRepository;
        this.slugifyUtil = slugifyUtil;
        this.transformCategory = transformCategory;
        this.transformMaterial = transformMaterial;
        this.transformProduct = transformProduct;
    }

    @Override
    public void createProduct(CreateProductBody createProductBody) {

        String productSlug = slugifyUtil.stringToSlug(createProductBody.getName());
        slugifyUtil.verifyIfSlugAlreadyExists(productSlug, createProductBody.getProductType());

        fileService.createFile(createProductBody.getPictureDto(), true, productSlug);
        if (!createProductBody.getSecondaryPicturesDto().isEmpty()) {
            createProductBody.getSecondaryPicturesDto().forEach(secondaryPictureDto -> fileService.createFile(secondaryPictureDto, false, productSlug));
        }
        EProductType eProductType = ConvertEnumProductType.StringToEnum(createProductBody.getProductType());

        final Collection collectionFound = findOneCollectionBySlugOrThrow(createProductBody.getCollectionDto().getSlug());
        final Category categoryFound = findOneCategoryBySlugOrThrow(createProductBody.getCategoryDto().getSlug());

        final List<Material> materials = createProductBody.getMaterialsDto().stream()
                                                           .map(materialDto -> findOneMaterialBySlugOrThrow(materialDto.getSlug()))
                                                           .collect(Collectors.toList());

        final Product newProduct = Product.builder()
                .name(createProductBody.getName())
                .slug(productSlug)
                .price(createProductBody.getPrice())
                .description(createProductBody.getDescription())
                .introduction(createProductBody.getIntroduction())
                .category(categoryFound)
                .collection(collectionFound)
                .materials(materials)
                .eProductType(eProductType)
                .build();

        this.productRepository.save(newProduct);
    }

    @Override
    public ProductDto getProductBySlug(String slug, boolean isMaterialPriceVisible) {
        return this.transformProduct.productToDto(findOneProductBySlugOrThrow(slug), isMaterialPriceVisible);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return this.transformProduct.productsToDto(this.productRepository.findAll(), false);
    }

    @Override
    public List<ProductDto> findAllProductsByCategorySlug(String categorySlug) {
        Category category = findOneCategoryBySlugOrThrow(categorySlug);
        List<Product> products = this.productRepository.findAllByCategory(category);
        return this.transformProduct.productsToDto(products, false);
    }

    @Override
    public List<ProductDto> findAllProductsByCollectionSlug(String collectionSlug) {
        Collection collection = findOneCollectionBySlugOrThrow(collectionSlug);
        List<Product> products = this.productRepository.findAllByCollection(collection);
        return this.transformProduct.productsToDto(products, false);
    }

    @Override
    public List<ProductDto> findAllProductsFilteredByCategoryAndCollection(String productType, CategoryDtoAndCollectionDto categoryDtoAndCollectionDto) {

        Category categoryFound = null;
        if (categoryDtoAndCollectionDto.getCategoryDto() != null) {
            categoryFound = findOneCategoryBySlugOrThrow(categoryDtoAndCollectionDto.getCategoryDto().getSlug());
        }

        Collection collectionFound = null;
        if (categoryDtoAndCollectionDto.getCollectionDto() != null) {
            collectionFound = findOneCollectionBySlugOrThrow(categoryDtoAndCollectionDto.getCollectionDto().getSlug());
        }

        EProductType eProductType = ConvertEnumProductType.StringToEnum(productType);

        List<Product> products = this.productRepository.findAllByCategoryAndCollectionAndEProductType(categoryFound, collectionFound, eProductType);
        return this.transformProduct.productsToDto(products, false);
    }

    @Override
    public ProductDto changeProductAvailability(ProductDto productDto) {
        Product productToUpdate = findOneProductBySlugOrThrow(productDto.getSlug());
        productToUpdate.setAvailable(!productToUpdate.isAvailable());
        return this.transformProduct.productToDto(this.productRepository.save(productToUpdate), true);
    }

    @Override
    public void updateProduct(ProductDto productDtoUpdated) {

        Product originalProduct = findOneProductBySlugOrThrow(productDtoUpdated.getSlug());

        String newProductSlug = slugifyUtil.stringToSlug(productDtoUpdated.getName());
        if (!productDtoUpdated.getSlug().equals(newProductSlug)) {
            slugifyUtil.verifyIfSlugAlreadyExists(newProductSlug, productDtoUpdated.getProductType());
        }

        FileDto actualPictureDto = fileService.findFilePictureByLinkedSlug(originalProduct.getSlug());
        if (!productDtoUpdated.getPictureDto().getSlug().equals(actualPictureDto.getSlug())) {
            final boolean isMainPicture = true;
            fileService.updateFile(productDtoUpdated.getPictureDto(), newProductSlug, isMainPicture);
        }

        List<FileDto> actualSecondaryPicturesDto = fileService.findFilesSecondaryPicturesByLinkedSlug(originalProduct.getSlug());
        if (!actualSecondaryPicturesDto.isEmpty()) {
            fileService.compareAndUpdateFiles(actualSecondaryPicturesDto, productDtoUpdated.getSecondaryPicturesDto(), newProductSlug);
        }

        originalProduct.setName(productDtoUpdated.getName());
        originalProduct.setSlug(newProductSlug);
        originalProduct.setIntroduction(productDtoUpdated.getIntroduction());
        originalProduct.setPrice(productDtoUpdated.getPrice());
        originalProduct.setDescription(productDtoUpdated.getDescription());

        Category categoryToUpdated = null;
        if (this.categoryRepository.existsBySlug(productDtoUpdated.getCategoryDto().getSlug())) {
            categoryToUpdated = findOneCategoryBySlugOrThrow(productDtoUpdated.getCategoryDto().getSlug());
        }
        originalProduct.setCategory(categoryToUpdated);

        Collection collectionToUpdated = null;
        if (this.collectionRepository.existsBySlug(productDtoUpdated.getCollectionDto().getSlug())) {
            collectionToUpdated = findOneCollectionBySlugOrThrow(productDtoUpdated.getCollectionDto().getSlug());
        }
        originalProduct.setCollection(collectionToUpdated);

        final List<Material> materials = productDtoUpdated.getMaterialsDto().stream()
                                                     .map(materialDto -> findOneMaterialBySlugOrThrow(materialDto.getSlug()))
                                                     .collect(Collectors.toList());
        originalProduct.setMaterials(materials);

        this.productRepository.save(originalProduct);
    }

    public Product deleteProductBySlug(String slug){
        Product productToDelete = findOneProductBySlugOrThrow(slug);

        FileDto pictureDto = fileService.findFilePictureByLinkedSlug(slug);
        if (pictureDto != null) {
            fileService.deleteFileBySlug(pictureDto.getSlug());
        }
        List<FileDto> secondaryPictureList = fileService.findFilesSecondaryPicturesByLinkedSlug(slug);
        if (!secondaryPictureList.isEmpty()) {
            fileService.deleteAllFilesByLinkedSlug(slug);
        }

        this.productRepository.delete(productToDelete);
        return productToDelete;
    }

    private Product findOneProductBySlugOrThrow(String slug) {
        return this.productRepository.findBySlug(slug).orElseThrow(() -> new ProductNotFoundException("Produit"));
    }

    private Category findOneCategoryBySlugOrThrow(String slug) {
        return this.categoryRepository.findBySlug(slug).orElseThrow(CategoryNotFoundException::new);
    }

    private Collection findOneCollectionBySlugOrThrow(String slug) {
        return this.collectionRepository.findBySlug(slug).orElseThrow(CollectionNotFoundException::new);
    }

    private Material findOneMaterialBySlugOrThrow(String slug) {
        return this.materialRepository.findBySlug(slug).orElseThrow(MaterialNotFoundException::new);
    }
}

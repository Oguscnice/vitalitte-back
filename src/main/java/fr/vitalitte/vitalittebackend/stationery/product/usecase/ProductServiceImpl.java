package fr.vitalitte.vitalittebackend.stationery.product.usecase;

import fr.vitalitte.vitalittebackend.stationery.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.stationery.category.usecase.TransformCategory;
import fr.vitalitte.vitalittebackend.stationery.collection.exception.CollectionNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.collection.persistence.CollectionRepository;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.stationery.common.rest.CategoryDtoAndCollectionDto;
import fr.vitalitte.vitalittebackend.stationery.materials.exception.MaterialNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import fr.vitalitte.vitalittebackend.stationery.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.stationery.materials.usecase.TransformMaterial;
import fr.vitalitte.vitalittebackend.stationery.product.exception.ProductNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.product.exception.SlugProductAlreadyExistsException;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import fr.vitalitte.vitalittebackend.stationery.product.rest.CreateProductBody;
import fr.vitalitte.vitalittebackend.stationery.product.rest.ProductDto;
import fr.vitalitte.vitalittebackend.stationery.productType.models.EProductType;
import fr.vitalitte.vitalittebackend.stationery.productType.usecase.ConvertEnumProductType;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.exception.SecondaryPictureNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.models.SecondaryPicture;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.persistence.SecondaryPictureRepository;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.rest.SecondaryPictureDto;
import fr.vitalitte.vitalittebackend.stationery.secondaryPicture.usecase.SecondaryPictureService;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    MaterialRepository materialRepository;
    CollectionRepository collectionRepository;
    SecondaryPictureRepository secondaryPictureRepository;
    SecondaryPictureService secondaryPictureService;
    TransformProduct transformProduct;
    TransformMaterial transformMaterial;
    TransformCategory transformCategory;
    TransformUrl transformUrl;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, MaterialRepository materialRepository, CollectionRepository collectionRepository, SecondaryPictureRepository secondaryPictureRepository, SecondaryPictureService secondaryPictureService, TransformProduct transformProduct, TransformMaterial transformMaterial, TransformCategory transformCategory, TransformUrl transformUrl) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.materialRepository = materialRepository;
        this.collectionRepository = collectionRepository;
        this.secondaryPictureRepository = secondaryPictureRepository;
        this.secondaryPictureService = secondaryPictureService;
        this.transformProduct = transformProduct;
        this.transformMaterial = transformMaterial;
        this.transformCategory = transformCategory;
        this.transformUrl = transformUrl;
    }

    @Override
    public void createProduct(CreateProductBody createProductBody) {

        String productSlug = slugifyProduct(createProductBody);
        verifyIfSlugAlreadyExists(productSlug, createProductBody.getProductType());

        URL picture = this.transformUrl.stringToUrl(createProductBody.getPicture());
        URL pictureThumbnail = this.transformUrl.stringToUrl(createProductBody.getPictureThumbnail());
        EProductType eProductType = ConvertEnumProductType.StringToEnum(createProductBody.getProductType());

        final Collection collectionFound = findOneCollectionBySlugOrThrow(createProductBody.getCollectionDto().getSlug());
        final Category categoryFound = findOneCategoryBySlugOrThrow(createProductBody.getCategoryDto().getSlug());

        final List<Material> materials = createProductBody.getMaterialsDto().stream()
                                                           .map(materialDto -> findOneMaterialBySlugOrThrow(materialDto.getSlug()))
                                                           .collect(Collectors.toList());

        final Product newProduct = Product.builder()
                .name(createProductBody.getName())
                .slug(productSlug)
                .picture(picture)
                .pictureThumbnail(pictureThumbnail)
                .price(createProductBody.getPrice())
                .description(createProductBody.getDescription())
                .introduction(createProductBody.getIntroduction())
                .category(categoryFound)
                .collection(collectionFound)
                .materials(materials)
                .eProductType(eProductType)
                .build();

        this.productRepository.save(newProduct);

        createSecondaryPictures(findOneProductBySlugOrThrow(productSlug), createProductBody.getSecondaryPicturesDto());
    }

    @Override
    public ProductDto getProductBySlug(String slug) {
        return this.transformProduct.productToDto(findOneProductBySlugOrThrow(slug));
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return this.transformProduct.productsToDto(this.productRepository.findAll());
    }

    @Override
    public List<ProductDto> findAllProductsByCategorySlug(String categorySlug) {

        Category category = findOneCategoryBySlugOrThrow(categorySlug);

        List<Product> products = this.productRepository.findAllByCategory(category);
        return this.transformProduct.productsToDto(products);
    }

    @Override
    public List<ProductDto> findAllProductsByCollectionSlug(String collectionSlug) {

        Collection collection = findOneCollectionBySlugOrThrow(collectionSlug);

        List<Product> products = this.productRepository.findAllByCollection(collection);
        return this.transformProduct.productsToDto(products);
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
        return this.transformProduct.productsToDto(products);
    }

    @Override
    public ProductDto changeProductAvailability(ProductDto productDto) {
        Product productToUpdate = findOneProductBySlugOrThrow(productDto.getSlug());
        productToUpdate.setAvailable(!productToUpdate.isAvailable());
        return this.transformProduct.productToDto(this.productRepository.save(productToUpdate));
    }

    @Override
    public void updateProduct(ProductDto productDtoUpdated) {

        Product originalProduct = findOneProductBySlugOrThrow(productDtoUpdated.getSlug());
        List<SecondaryPicture> oldSecondaryPictures = this.secondaryPictureRepository.findAllByProduct(originalProduct);

        String newProductSlug = slugifyProduct(productDtoUpdated);
        if (!productDtoUpdated.getSlug().equals(newProductSlug)) {
            verifyIfSlugAlreadyExists(newProductSlug, productDtoUpdated.getProductType());
        }

        URL picture = this.transformUrl.stringToUrl(productDtoUpdated.getPicture());
        URL pictureThumbnail = this.transformUrl.stringToUrl(productDtoUpdated.getPictureThumbnail());

        originalProduct.setName(productDtoUpdated.getName());
        originalProduct.setSlug(newProductSlug);
        originalProduct.setPicture(picture);
        originalProduct.setPictureThumbnail(pictureThumbnail);
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

        compareOldSecPicListAndNewPicList(newProductSlug, oldSecondaryPictures, productDtoUpdated.getSecondaryPicturesDto());
    }

    public Product deleteProductBySlug(String slug){
        Product productToDelete = findOneProductBySlugOrThrow(slug);

        List<SecondaryPicture> secondaryPictureList = this.secondaryPictureRepository.findAllByProduct(productToDelete);
        if (!secondaryPictureList.isEmpty()) {
            this.secondaryPictureRepository.deleteAll(secondaryPictureList);
        }

        this.productRepository.delete(productToDelete);
        return productToDelete;
    }

    private String slugifyProduct(CreateProductBody createProductBody) {
        return SlugifyUtil.stringToSlug(createProductBody.getName());
    }

    private String slugifyProduct(ProductDto productDto) {
        return SlugifyUtil.stringToSlug(productDto.getName());
    }

    private void verifyIfSlugAlreadyExists(String slug, String productType) {
        if (this.productRepository.existsBySlug(slug)) {
            throw new SlugProductAlreadyExistsException(productType);
        }
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

    private void createSecondaryPictures(Product product, List<SecondaryPictureDto> newSecondaryPicturesDto) {
        if (!newSecondaryPicturesDto.isEmpty()) {
            newSecondaryPicturesDto.forEach(secondaryPictureDto -> this.secondaryPictureService.createSecondaryPicture(product, secondaryPictureDto));
        }
    }

    private void compareOldSecPicListAndNewPicList(String newProductSlug, List<SecondaryPicture> originalSecondaryPicturesDto, List<SecondaryPictureDto> newSecondaryPicturesDto) {

        final Product newProduct = findOneProductBySlugOrThrow(newProductSlug);

        if (!newSecondaryPicturesDto.isEmpty()) {

//         Si dans l' "ancienne liste" d'image, une image existe, on met la relation Product à jour,
//          si elle n'existe pas dans la "nouvelle liste" on la supprime
            for (SecondaryPicture oldPicture : originalSecondaryPicturesDto) {
                boolean existsInNewList = false;
                SecondaryPicture secondaryPicture = findOneSecondaryPictureByPictureUrl(oldPicture.getPicture());

                for (SecondaryPictureDto newPicture : newSecondaryPicturesDto) {

                    if (oldPicture.getPicture().equals(transformUrl.stringToUrl(newPicture.getPicture()))) {
                        secondaryPicture.setProduct(newProduct);
                        this.secondaryPictureRepository.save(secondaryPicture);
                        existsInNewList = true;
                        break;
                    }
                }
                if (!existsInNewList) {
                    this.secondaryPictureRepository.delete(secondaryPicture);
                }
            }

//          Si dans la "nouvelle liste" d'image, une image n'existe pas dans l' "ancienne liste", on créé l'image,
            for (SecondaryPictureDto newPicture : newSecondaryPicturesDto) {
                boolean existsInOldList = false;

                for (SecondaryPicture oldPicture : originalSecondaryPicturesDto) {

                    if (oldPicture.getPicture() == this.transformUrl.stringToUrl(newPicture.getPicture())) {
                        existsInOldList = true;
                        break;
                    }
                }
                if (!existsInOldList) {
                    this.secondaryPictureService.createSecondaryPicture(newProduct, newPicture);
                }
            }
        } else {
            originalSecondaryPicturesDto.forEach(secPicDto -> this.secondaryPictureRepository.delete(findOneSecondaryPictureByPictureUrl(secPicDto.getPicture())));
        }
    }

    private SecondaryPicture findOneSecondaryPictureByPictureUrl(URL pictureUrl) {
        return this.secondaryPictureRepository.findByPicture(pictureUrl)
                .orElseThrow(SecondaryPictureNotFoundException::new);
    }
}

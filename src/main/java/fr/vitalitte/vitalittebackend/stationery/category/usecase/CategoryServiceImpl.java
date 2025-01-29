package fr.vitalitte.vitalittebackend.stationery.category.usecase;

import fr.vitalitte.vitalittebackend.common.usecase.FileService;
import fr.vitalitte.vitalittebackend.stationery.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.common.usecase.CapitalizeStringUtil;
import fr.vitalitte.vitalittebackend.common.usecase.SlugifyUtil;
import fr.vitalitte.vitalittebackend.stationery.category.rest.CreateCategoryBody;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    TransformCategory transformCategory;
    ProductRepository productRepository;
    SlugifyUtil slugifyUtil;
    FileService fileService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, TransformCategory transformCategory, ProductRepository productRepository, SlugifyUtil slugifyUtil, FileService fileService) {
        this.categoryRepository = categoryRepository;
        this.transformCategory = transformCategory;
        this.productRepository = productRepository;
        this.slugifyUtil = slugifyUtil;
        this.fileService = fileService;
    }

    @Override
    public void createCategory(CreateCategoryBody createCategoryBody) {

        String categorySlug = slugifyUtil.stringToSlug(createCategoryBody.getName());
        slugifyUtil.verifyIfSlugAlreadyExists(categorySlug);

        fileService.createFile(createCategoryBody.getPictureDto(), true, categorySlug);

        final Category newCategory = Category.builder()
                .name(CapitalizeStringUtil.firstLetter(createCategoryBody.getName()))
                .slug(categorySlug)
                .description(createCategoryBody.getDescription())
                .build();

        this.categoryRepository.save(newCategory);
    }

    @Override
    public void updateCategory(String slug, CategoryDto category) {

        Category categoryToUpdate = findOneCategoryBySlugOrThrow(slug);

        String newCategorySlug = slugifyUtil.stringToSlug(category.getName());
        if (!slug.equals(newCategorySlug)) {
            slugifyUtil.verifyIfSlugAlreadyExists(newCategorySlug);
            categoryToUpdate.setSlug(newCategorySlug);
        }

        categoryToUpdate.setName(CapitalizeStringUtil.firstLetter(category.getName()));
        categoryToUpdate.setDescription(category.getDescription());
        fileService.updateFile(category.getPictureDto(), newCategorySlug, true);

        categoryRepository.save(categoryToUpdate);
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return transformCategory.categoriesToDtos(categories);
    }

    @Override
    public void deleteCategoryBySlug(String categorySlug) {

        Category categoryFound = findOneCategoryBySlugOrThrow(categorySlug);
        List<Product> products = productRepository.findAllByCategory(categoryFound);

        for (Product product : products) {
            product.setCategory(null);
            productRepository.save(product);
        }

        fileService.deleteAllFilesByLinkedSlug(categorySlug);
        categoryRepository.delete(categoryFound);
    }

    private Category findOneCategoryBySlugOrThrow(String categorySlug) {
        return this.categoryRepository.findBySlug(categorySlug).orElseThrow(CategoryNotFoundException::new);
    }
}

package fr.vitalitte.vitalittebackend.stationery.category.usecase;

import fr.vitalitte.vitalittebackend.stationery.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.category.exception.SlugCategoryAlreadyExistsException;
import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.common.utils.CapitalizeStringUtil;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    TransformCategory transformCategory;
    ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, TransformCategory transformCategory, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.transformCategory = transformCategory;
        this.productRepository = productRepository;
    }

    @Override
    public void createCategory(String categoryName) {

        String categorySlug = SlugifyUtil.stringToSlug(categoryName);
        verifyIfCategoryExistsBySlug(categorySlug);

        final Category newCategory = Category.builder()
                .name(CapitalizeStringUtil.firstLetter(categoryName))
                .slug(categorySlug)
                .build();

        this.categoryRepository.save(newCategory);
    }

    @Override
    public void updateCategory(String slug, CategoryDto category) {

        Category categoryToUpdate = findOneCategoryBySlugOrThrow(slug);

        String newCategorySlug = SlugifyUtil.stringToSlug(category.getName());
        verifyIfCategoryExistsBySlug(newCategorySlug);

        categoryToUpdate.setSlug(newCategorySlug);
        categoryToUpdate.setName(CapitalizeStringUtil.firstLetter(category.getName()));

        this.categoryRepository.save(categoryToUpdate);
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        return this.transformCategory.categoriesToDtos(categories);
    }

    @Override
    public void deleteCategoryBySlug(String categorySlug) {

            Category categoryFound = findOneCategoryBySlugOrThrow(categorySlug);

        List<Product> products = this.productRepository.findAllByCategory(categoryFound);

        for (Product product : products) {
            product.setCategory(null);
            this.productRepository.save(product);
        }

        this.categoryRepository.delete(categoryFound);
    }

    private Category findOneCategoryBySlugOrThrow(String categorySlug) {
        return this.categoryRepository.findBySlug(categorySlug).orElseThrow(CategoryNotFoundException::new);
    }

    private void verifyIfCategoryExistsBySlug(String slug) {
        if (this.categoryRepository.existsBySlug(slug)) {
            throw new SlugCategoryAlreadyExistsException();
        }
    }
}

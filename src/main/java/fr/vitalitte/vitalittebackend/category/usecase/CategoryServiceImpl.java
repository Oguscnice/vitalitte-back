package fr.vitalitte.vitalittebackend.category.usecase;

import fr.vitalitte.vitalittebackend.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.category.exception.SlugCategoryAlreadyExistsException;
import fr.vitalitte.vitalittebackend.category.models.Category;
import fr.vitalitte.vitalittebackend.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.common.utils.CapitalizeStringUtil;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.notebook.persistence.NotebookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    TransformCategory transformCategory;
    NotebookRepository notebookRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, TransformCategory transformCategory, NotebookRepository notebookRepository) {
        this.categoryRepository = categoryRepository;
        this.transformCategory = transformCategory;
        this.notebookRepository = notebookRepository;
    }

    @Override
    public void createCategory(String categoryName) {

        String categorySlug = SlugifyUtil.stringToSlug(categoryName);
        verifyIfCategoryExistsBySlug(categorySlug);

        final Category newCategory = Category.builder()
                .name(CapitalizeStringUtil.capitalizeFirstLetter(categoryName))
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
        categoryToUpdate.setName(CapitalizeStringUtil.capitalizeFirstLetter(category.getName()));

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

        List<Notebook> notebooks = this.notebookRepository.findAllByCategory(categoryFound);

        for (Notebook notebook : notebooks) {
            notebook.setCategory(null);
            this.notebookRepository.save(notebook);
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

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

        String newSlug = SlugifyUtil.stringToSlug(categoryName);

        if (this.categoryRepository.existsBySlug(newSlug)) {
            throw new SlugCategoryAlreadyExistsException();
        }

        final Category newCategory = Category.builder()
                .name(CapitalizeStringUtil.capitalizeFirstLetter(categoryName))
                .slug(newSlug)
                .build();

        this.categoryRepository.save(newCategory);
    }

    @Override
    public CategoryDto findCategoryBySlug(String slug){

        Category categoryFound = this.categoryRepository.findBySlug(slug)
                .orElseThrow(CategoryNotFoundException::new);

        return this.transformCategory.categoryToDto(categoryFound);
    }

    @Override
    public void updateCategory(String slug, CategoryDto category) {

        Category categoryToUpdate = this.categoryRepository.findBySlug(slug)
                                                .orElseThrow(CategoryNotFoundException::new);

        String newSlug = SlugifyUtil.stringToSlug(category.getName());

        if (this.categoryRepository.existsBySlug(newSlug)) {
            throw new SlugCategoryAlreadyExistsException();
        }

        categoryToUpdate.setSlug(newSlug);
        categoryToUpdate.setName(CapitalizeStringUtil.capitalizeFirstLetter(category.getName()));

        this.categoryRepository.save(categoryToUpdate);
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        List<Category> categoriesList = this.categoryRepository.findAll();
        return this.transformCategory.categoriesToDtos(categoriesList);
    }

    @Override
    public void deleteCategoryBySlug(String slug) {

        if(!this.categoryRepository.existsBySlug(slug)) {
            throw new CategoryNotFoundException();
        }

        Category categoryFound = this.categoryRepository.findBySlug(slug)
                                            .orElseThrow(CategoryNotFoundException::new);
        List<Notebook> notebooks = this.notebookRepository.findAllByCategorySlug(categoryFound.getSlug());

        for (Notebook notebook : notebooks) {
            notebook.setCategory(null);
            this.notebookRepository.save(notebook);
        }

        this.categoryRepository.delete(categoryFound);
    }
}

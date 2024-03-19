package fr.vitalitte.vitalittebackend.category.usecase;

import fr.vitalitte.vitalittebackend.category.rest.CategoryDto;

import java.util.List;

public interface CategoryService {
    void createCategory(String categoryName);
    CategoryDto findCategoryBySlug(String slug);
    void updateCategory(String slug, CategoryDto category);
    List<CategoryDto> findAllCategories();
    void deleteCategoryBySlug(String slug);
};

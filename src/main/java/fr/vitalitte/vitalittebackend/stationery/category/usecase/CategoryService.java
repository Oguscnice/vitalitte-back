package fr.vitalitte.vitalittebackend.stationery.category.usecase;

import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;

import java.util.List;

public interface CategoryService {
    void createCategory(String categoryName);
    void updateCategory(String slug, CategoryDto category);
    List<CategoryDto> findAllCategories();
    void deleteCategoryBySlug(String slug);
};

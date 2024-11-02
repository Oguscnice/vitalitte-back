package fr.vitalitte.vitalittebackend.stationery.category.usecase;

import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.stationery.category.rest.CreateCategoryBody;

import java.util.List;

public interface CategoryService {
    void createCategory(CreateCategoryBody createCategoryBody);
    void updateCategory(String slug, CategoryDto category);
    List<CategoryDto> findAllCategories();
    void deleteCategoryBySlug(String slug);
};

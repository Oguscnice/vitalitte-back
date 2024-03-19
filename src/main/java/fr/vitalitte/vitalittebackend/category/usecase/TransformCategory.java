package fr.vitalitte.vitalittebackend.category.usecase;

import fr.vitalitte.vitalittebackend.category.exception.CategoryNotFoundException;
import fr.vitalitte.vitalittebackend.category.models.Category;
import fr.vitalitte.vitalittebackend.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.category.rest.CategoryDto;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

import java.util.List;

@Service
public class TransformCategory {

    CategoryRepository categoryRepository;

    public TransformCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto categoryToDto(Category category) {
        return CategoryDto.builder()
                .slug(category.getSlug())
                .name(category.getName())
                .build();
    }

    public List<CategoryDto> categoriesToDtos(List<Category> categories) {
        return mapList(this::categoryToDto, categories);
    }

    public Category dtoToCategory(CategoryDto categoryDto) {
        return this.categoryRepository.findBySlug(categoryDto.getSlug())
                        .orElseThrow(CategoryNotFoundException::new);
    }

    public List<Category> dtosToCategories(List<CategoryDto> categoriesDto) {
        return mapList(this::dtoToCategory, categoriesDto);
    }
}

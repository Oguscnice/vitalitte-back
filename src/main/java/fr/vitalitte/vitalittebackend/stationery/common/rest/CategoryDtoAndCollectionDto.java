package fr.vitalitte.vitalittebackend.stationery.common.rest;

import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.stationery.collection.rest.CollectionDto;

public class CategoryDtoAndCollectionDto {

    private final CategoryDto categoryDto;
    private final CollectionDto collectionDto;

    public CategoryDtoAndCollectionDto(CategoryDto categoryDto, CollectionDto collectionDto) {
        this.categoryDto = categoryDto;
        this.collectionDto = collectionDto;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public CollectionDto getCollectionDto() {
        return collectionDto;
    }
}

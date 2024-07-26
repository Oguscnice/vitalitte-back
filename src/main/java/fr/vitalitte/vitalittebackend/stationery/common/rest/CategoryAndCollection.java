package fr.vitalitte.vitalittebackend.stationery.common.rest;

import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.stationery.collection.rest.CollectionDto;

public class CategoryAndCollection {

    private final CategoryDto category;
    private final CollectionDto collection;

    public CategoryAndCollection(CategoryDto category, CollectionDto collection) {
        this.category = category;
        this.collection = collection;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public CollectionDto getCollection() {
        return collection;
    }
}

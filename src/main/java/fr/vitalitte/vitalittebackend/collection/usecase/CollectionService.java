package fr.vitalitte.vitalittebackend.collection.usecase;

import fr.vitalitte.vitalittebackend.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.collection.rest.CollectionDto;

import java.util.List;

public interface CollectionService {
    void createCollection(String collectionName);
    void updateCollection(String slug, CollectionDto collectionDto);
    List<CollectionDto> findAllCollections();
    void deleteCollectionBySlug(String slug);
}

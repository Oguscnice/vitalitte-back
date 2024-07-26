package fr.vitalitte.vitalittebackend.stationery.collection.usecase;

import fr.vitalitte.vitalittebackend.stationery.collection.rest.CollectionDto;

import java.util.List;

public interface CollectionService {
    void createCollection(String collectionName);
    void updateCollection(String slug, CollectionDto collectionDto);
    List<CollectionDto> findAllCollections();
    void deleteCollectionBySlug(String slug);
}

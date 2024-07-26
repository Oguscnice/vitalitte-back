package fr.vitalitte.vitalittebackend.stationery.collection.usecase;

import fr.vitalitte.vitalittebackend.stationery.collection.exception.CollectionNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.collection.persistence.CollectionRepository;
import fr.vitalitte.vitalittebackend.stationery.collection.rest.CollectionDto;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;

@Service
public class TransformCollection {
    CollectionRepository collectionRepository;

    public TransformCollection(CollectionRepository collectionRepository) {this.collectionRepository = collectionRepository;}

    public CollectionDto collectionToDto(Collection collection){
        return CollectionDto.builder()
                .slug(collection.getSlug())
                .name(collection.getName())
                .build();
    }

    public List<CollectionDto> collectionsToDtos(List<Collection> collections) {
        return mapList(this::collectionToDto, collections);
    }

    public Collection dtoToCollection(CollectionDto collectionDto) {
        return this.collectionRepository.findBySlug(collectionDto.getSlug())
                .orElseThrow(CollectionNotFoundException::new);
    }

    public List<Collection> dtosToCollections(List<CollectionDto> collectionsDtos) {
        return mapList(this::dtoToCollection, collectionsDtos);
    }
}

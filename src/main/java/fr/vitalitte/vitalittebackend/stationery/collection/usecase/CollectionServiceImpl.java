package fr.vitalitte.vitalittebackend.stationery.collection.usecase;

import fr.vitalitte.vitalittebackend.stationery.category.exception.SlugCategoryAlreadyExistsException;
import fr.vitalitte.vitalittebackend.stationery.collection.exception.CollectionNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.collection.persistence.CollectionRepository;
import fr.vitalitte.vitalittebackend.stationery.collection.rest.CollectionDto;
import fr.vitalitte.vitalittebackend.common.utils.CapitalizeStringUtil;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    CollectionRepository collectionRepository;
    TransformCollection transformCollection;
    ProductRepository productRepository;

    public CollectionServiceImpl(CollectionRepository collectionRepository, TransformCollection transformCollection, ProductRepository productRepository) {
        this.collectionRepository = collectionRepository;
        this.transformCollection = transformCollection;
        this.productRepository = productRepository;
    }

    @Override
    public void createCollection(String collectionName){
        String newSlug = SlugifyUtil.stringToSlug(collectionName);

        if (this.collectionRepository.existsBySlug(newSlug)) {
            throw new SlugCategoryAlreadyExistsException();
        }

        final Collection newCollection = Collection.builder()
                .name(CapitalizeStringUtil.firstLetter(collectionName))
                .slug(newSlug)
                .build();

        this.collectionRepository.save(newCollection);
    }
    @Override
    public void updateCollection(String slug, CollectionDto collectionDto){

        Collection collectionToUpdate = this.collectionRepository.findBySlug(slug)
                .orElseThrow(CollectionNotFoundException::new);

        String newSlug = SlugifyUtil.stringToSlug(collectionDto.getName());

        if (this.collectionRepository.existsBySlug(newSlug)) {
            throw new SlugCategoryAlreadyExistsException();
        }

        collectionToUpdate.setSlug(newSlug);
        collectionToUpdate.setName(collectionDto.getName());

        this.collectionRepository.save(collectionToUpdate);
    }

    @Override
    public List<CollectionDto> findAllCollections(){
        List<Collection> collections = this.collectionRepository.findAll();
        return this.transformCollection.collectionsToDtos(collections);
    }
    @Override
    public void deleteCollectionBySlug(String slug){
        Collection collectionFound = this.collectionRepository.findBySlug(slug)
                .orElseThrow(CollectionNotFoundException::new);

        List<Product> products = this.productRepository.findAllByCollection(collectionFound);

        for (Product product : products) {
            product.setCollection(null);
            this.productRepository.save(product);
        }

        this.collectionRepository.delete(collectionFound);
    }
}

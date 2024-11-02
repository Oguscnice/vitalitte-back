package fr.vitalitte.vitalittebackend.stationery.collection.usecase;

import fr.vitalitte.vitalittebackend.stationery.category.exception.SlugCategoryAlreadyExistsException;
import fr.vitalitte.vitalittebackend.stationery.collection.exception.CollectionNotFoundException;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.collection.persistence.CollectionRepository;
import fr.vitalitte.vitalittebackend.stationery.collection.rest.CollectionDto;
import fr.vitalitte.vitalittebackend.common.usecase.CapitalizeStringUtil;
import fr.vitalitte.vitalittebackend.common.usecase.SlugifyUtil;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    CollectionRepository collectionRepository;
    TransformCollection transformCollection;
    ProductRepository productRepository;
    SlugifyUtil slugifyUtil;

    public CollectionServiceImpl(CollectionRepository collectionRepository, TransformCollection transformCollection, ProductRepository productRepository, SlugifyUtil slugifyUtil) {
        this.collectionRepository = collectionRepository;
        this.transformCollection = transformCollection;
        this.productRepository = productRepository;
        this.slugifyUtil = slugifyUtil;
    }

    @Override
    public void createCollection(String collectionName) {

        String newSlug = slugifyUtil.stringToSlug(collectionName);

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

        Collection collectionToUpdate = findOneCollectionBySlugOrThrow(slug);

        String newSlug = slugifyUtil.stringToSlug(collectionDto.getName());

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
    public void deleteCollectionBySlug(String slug) {

        Collection collectionFound = findOneCollectionBySlugOrThrow(slug);
        List<Product> products = this.productRepository.findAllByCollection(collectionFound);

        for (Product product : products) {
            product.setCollection(null);
            this.productRepository.save(product);
        }

        this.collectionRepository.delete(collectionFound);
    }

    private Collection findOneCollectionBySlugOrThrow(String collectionSlug) {
        return this.collectionRepository.findBySlug(collectionSlug).orElseThrow(CollectionNotFoundException::new);
    }
}

package fr.vitalitte.vitalittebackend.common.usecase;

import fr.vitalitte.vitalittebackend.publication.exception.SlugPublicationAlreadyExistsException;
import fr.vitalitte.vitalittebackend.publication.persistence.PublicationRepository;
import fr.vitalitte.vitalittebackend.stationery.category.exception.SlugCategoryAlreadyExistsException;
import fr.vitalitte.vitalittebackend.stationery.category.persistence.CategoryRepository;
import fr.vitalitte.vitalittebackend.stationery.materials.exception.SlugMaterialAlreadyExistsException;
import fr.vitalitte.vitalittebackend.stationery.materials.persistence.MaterialRepository;
import fr.vitalitte.vitalittebackend.stationery.product.exception.SlugProductAlreadyExistsException;
import fr.vitalitte.vitalittebackend.stationery.product.persistence.ProductRepository;
import fr.vitalitte.vitalittebackend.workshop.exception.SlugWorkshopAlreadyExistsException;
import fr.vitalitte.vitalittebackend.workshop.persistence.WorkshopRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class SlugifyUtil {

//    Tous Repo qui peut avoir un slug
    private final PublicationRepository publicationRepository;
    private final CategoryRepository categoryRepository;
    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;
    private final WorkshopRepository workshopRepository;

    public SlugifyUtil(CategoryRepository categoryRepository, PublicationRepository publicationRepository, MaterialRepository materialRepository, ProductRepository productRepository, WorkshopRepository workshopRepository) {
        this.categoryRepository = categoryRepository;
        this.publicationRepository = publicationRepository;
        this.materialRepository = materialRepository;
        this.productRepository = productRepository;
        this.workshopRepository = workshopRepository;
    }

    public String stringToSlug(String title) {
        return Normalizer.normalize(title, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^a-zA-Z0-9]+", "-")
                .toLowerCase();
    }

    public String dateToFormatDDmmYY(Date dateToConvert) {

        LocalDateTime instantActual = dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        return instantActual.format(formatter);
    }

    public void verifyIfSlugAlreadyExists(String slug) {
        if (categoryRepository.existsBySlug(slug)) {
            throw new SlugCategoryAlreadyExistsException();
        }
        if (materialRepository.existsBySlug(slug)) {
            throw new SlugMaterialAlreadyExistsException();
        }
        if (productRepository.existsBySlug(slug)) {
            throw new SlugProductAlreadyExistsException("Produit");
        }
        if (publicationRepository.existsBySlug(slug)) {
            throw new SlugPublicationAlreadyExistsException();
        }
        if (workshopRepository.existsBySlug(slug)) {
            throw new SlugWorkshopAlreadyExistsException();
        }
    }

    public void verifyIfSlugAlreadyExists(String slug, String productType) {
        if (productRepository.existsBySlug(slug)) {
            throw new SlugProductAlreadyExistsException(productType);
        }
    }
}

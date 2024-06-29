package fr.vitalitte.vitalittebackend.publication.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.publication.exception.PublicationNotFoundException;
import fr.vitalitte.vitalittebackend.publication.exception.SlugPublicationAlreadyExistsException;
import fr.vitalitte.vitalittebackend.publication.models.Publication;
import fr.vitalitte.vitalittebackend.publication.persistence.PublicationRepository;
import fr.vitalitte.vitalittebackend.publication.rest.CreatePublicationBody;
import fr.vitalitte.vitalittebackend.publication.rest.PublicationDto;
import fr.vitalitte.vitalittebackend.workshop.exception.SlugWorkshopAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
public class PublicationServiceImpl implements PublicationService {

    PublicationRepository publicationRepository;
    TransformPublication transformPublication;
    TransformUrl transformUrl;

    public PublicationServiceImpl(PublicationRepository publicationRepository, TransformPublication transformPublication, TransformUrl transformUrl) {
        this.publicationRepository = publicationRepository;
        this.transformPublication = transformPublication;
        this.transformUrl = transformUrl;
    }

    @Override
    public void createPublication(CreatePublicationBody createPublicationBody){
        String newSlug = SlugifyUtil.stringToSlug(createPublicationBody.getTitle() + '-' + SlugifyUtil.dateToFormatDDmmYY(new Date()));

        if (this.publicationRepository.existsBySlug(newSlug)){
            throw new SlugWorkshopAlreadyExistsException();
        }

        URL newPicture = this.transformUrl.stringToUrl(createPublicationBody.getPicture());

        final Publication newPublication = Publication.builder()
                .slug(newSlug)
                .title(createPublicationBody.getTitle())
                .description(createPublicationBody.getDescription())
                .picture(newPicture)
                .build();

        this.publicationRepository.save(newPublication);

    }

    @Override
    public List<PublicationDto> getPublicationsSpotlighted(boolean boolbool){
        List<Publication> publications = this.publicationRepository.findAllPublicationsByIsSpotlighted(boolbool);
        return this.transformPublication.publicationsToDtos(publications);
    }

    @Override
    public PublicationDto getPublicationBySlug(String slug){
        return this.transformPublication.publicationToDto(this.findOnePublicationBySlugOrThrow(slug));
    }

    @Override
    public List<PublicationDto> findAllPublications(){
        return this.transformPublication.publicationsToDtos(this.publicationRepository.findAll());
    }

    @Override
    public Long countPublications(PaginationItemBySearchValue paginationItemBySearchValue) {

        String value = paginationItemBySearchValue.getSearchValue();

        if (value.isBlank()) {
            return this.publicationRepository.count();
        } else {
            return this.publicationRepository.countPublicationsByTitleContainsIgnoreCaseOrDescriptionContainsIgnoreCase(value, value);
        }
    }

    @Override
    public List<PublicationDto> getPublicationsPaginated(PaginationItemBySearchValue paginationItemBySearchValue) {

        Pageable pageable = PageRequest.of(paginationItemBySearchValue.getPagination().getPage(), paginationItemBySearchValue.getPagination().getSize());
        Page<Publication> publicationPage;

        String value = paginationItemBySearchValue.getSearchValue();

        if (value.isBlank()) {
            publicationPage = this.publicationRepository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            publicationPage = this.publicationRepository.findAllByTitleContainsIgnoreCaseOrDescriptionContainsIgnoreCaseOrderByCreatedAtDesc(value, value, pageable);
        }

        return this.transformPublication.publicationsToDtos(publicationPage.getContent());
    }

    @Override
    public PublicationDto changePublicationSpotlight(PublicationDto publicationDtoUpdated){
        Publication publicationToUpdate = this.findOnePublicationBySlugOrThrow(publicationDtoUpdated.getSlug());

        publicationToUpdate.setSpotlighted(!publicationToUpdate.isSpotlighted());
        this.publicationRepository.save(publicationToUpdate);
        return this.transformPublication.publicationToDto(publicationToUpdate);
    }

    @Override
    public void updatePublicationBySlug(PublicationDto publicationDtoUpdated){
        Publication publicationToUpdate = this.findOnePublicationBySlugOrThrow(publicationDtoUpdated.getSlug());

        String newSlug = SlugifyUtil.stringToSlug(publicationDtoUpdated.getTitle() + '-' + SlugifyUtil.dateToFormatDDmmYY(publicationDtoUpdated.getCreatedAt()));
        if (this.publicationRepository.existsBySlug(newSlug) && !(publicationToUpdate.getSlug().equals(newSlug))){
            throw new SlugPublicationAlreadyExistsException();
        }

        URL newPicture = this.transformUrl.stringToUrl(publicationDtoUpdated.getPicture());

        publicationToUpdate.setTitle(publicationDtoUpdated.getTitle());
        publicationToUpdate.setSlug(newSlug);
        publicationToUpdate.setDescription(publicationDtoUpdated.getDescription());
        publicationToUpdate.setPicture(newPicture);

        this.publicationRepository.save(publicationToUpdate);
    }

    @Override
    public void deletePublicationBySlug(String slug){
        Publication publicationToDelete = this.findOnePublicationBySlugOrThrow(slug);

        this.publicationRepository.delete(publicationToDelete);
    }

    private Publication findOnePublicationBySlugOrThrow(String slug) {
        return this.publicationRepository.findBySlug(slug).orElseThrow(PublicationNotFoundException::new);
    }
}

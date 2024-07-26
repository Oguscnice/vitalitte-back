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
import org.springframework.data.domain.PageImpl;
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
    public void createPublication(CreatePublicationBody createPublicationBody) {

        String publicationSlug = slugifyPublication(createPublicationBody);

        existsBySlug(publicationSlug);

        URL picture = this.transformUrl.stringToUrl(createPublicationBody.getPicture());
        URL pictureThumbnail = this.transformUrl.stringToUrl(createPublicationBody.getPictureThumbnail());

        final Publication newPublication = Publication.builder()
                .slug(publicationSlug)
                .title(createPublicationBody.getTitle())
                .description(createPublicationBody.getDescription())
                .picture(picture)
                .pictureThumbnail(pictureThumbnail)
                .build();

        this.publicationRepository.save(newPublication);

    }

    @Override
    public List<PublicationDto> getPublicationsSpotlighted(boolean value) {
        List<Publication> publications = this.publicationRepository.findAllPublicationsByIsSpotlighted(value);
        return this.transformPublication.publicationsToDtos(publications);
    }

    @Override
    public PublicationDto getPublicationBySlug(String slug) {
        return this.transformPublication.publicationToDto(this.findOnePublicationBySlugOrThrow(slug));
    }

    @Override
    public Page<PublicationDto> getPublicationsPaginatedBySearchValue(PaginationItemBySearchValue paginationItemBySearchValue) {

        String value = paginationItemBySearchValue.getSearchValue();
        String title = value.isBlank() ? null : value;
        String description = value.isBlank() ? null : value;
        Pageable pageable = PageRequest.of(paginationItemBySearchValue.getPageableValues().getPageNumber(), paginationItemBySearchValue.getPageableValues().getPageSize());

        Page<Publication> publicationPage = this.publicationRepository.findAllByTitleOrDescriptionOrderByCreatedAtDesc(title, description, pageable);
        List<PublicationDto> publicationDtoList = this.transformPublication.publicationsToDtos(publicationPage.getContent());

        return new PageImpl<>(publicationDtoList, pageable, publicationPage.getTotalElements());
    }

    @Override
    public PublicationDto changePublicationSpotlight(PublicationDto publicationDtoUpdated) {

        Publication publicationToUpdate = this.findOnePublicationBySlugOrThrow(publicationDtoUpdated.getSlug());
        publicationToUpdate.setSpotlighted(!publicationToUpdate.isSpotlighted());
        this.publicationRepository.save(publicationToUpdate);

        return this.transformPublication.publicationToDto(publicationToUpdate);
    }

    @Override
    public void updatePublicationBySlug(PublicationDto publicationDtoUpdated) {

        Publication publicationToUpdate = this.findOnePublicationBySlugOrThrow(publicationDtoUpdated.getSlug());

        String newPublicationSlug = slugifyPublication(publicationDtoUpdated);
        existsBySlug(newPublicationSlug);
        if (!(publicationToUpdate.getSlug().equals(newPublicationSlug))){
            throw new SlugPublicationAlreadyExistsException();
        }

        URL newPicture = this.transformUrl.stringToUrl(publicationDtoUpdated.getPicture());
        URL newPictureThumbnail = this.transformUrl.stringToUrl(publicationDtoUpdated.getPictureThumbnail());

        publicationToUpdate.setTitle(publicationDtoUpdated.getTitle());
        publicationToUpdate.setSlug(newPublicationSlug);
        publicationToUpdate.setDescription(publicationDtoUpdated.getDescription());
        publicationToUpdate.setPicture(newPicture);
        publicationToUpdate.setPictureThumbnail(newPictureThumbnail);

        this.publicationRepository.save(publicationToUpdate);
    }

    @Override
    public void deletePublicationBySlug(String slug) {
        Publication publicationToDelete = this.findOnePublicationBySlugOrThrow(slug);
        this.publicationRepository.delete(publicationToDelete);
    }

    private String slugifyPublication(CreatePublicationBody createPublicationBody) {
        return slugUtil(createPublicationBody.getTitle());
    }

    private String slugifyPublication(PublicationDto publicationDto) {
        return slugUtil(publicationDto.getTitle());
    }

    private String slugUtil(String title) {
        return SlugifyUtil.stringToSlug(title + '-' + SlugifyUtil.dateToFormatDDmmYY(new Date()));
    }

    private void existsBySlug(String slug){
        if (this.publicationRepository.existsBySlug(slug)){
            throw new SlugWorkshopAlreadyExistsException();
        }
    }

    private Publication findOnePublicationBySlugOrThrow(String slug) {
        return this.publicationRepository.findBySlug(slug).orElseThrow(PublicationNotFoundException::new);
    }
}

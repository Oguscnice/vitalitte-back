package fr.vitalitte.vitalittebackend.publication.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.publication.rest.CreatePublicationBody;
import fr.vitalitte.vitalittebackend.publication.rest.PublicationDto;

import java.util.List;

public interface PublicationService {

    void createPublication(CreatePublicationBody createPublicationBody);
    List<PublicationDto> getPublicationsSpotlighted(boolean boolbool);
    PublicationDto getPublicationBySlug(String slug);
    List<PublicationDto> findAllPublications();
    Long countPublications(PaginationItemBySearchValue paginationItemBySearchValue);
    List<PublicationDto> getPublicationsPaginated(PaginationItemBySearchValue paginationItemBySearchValue);
    PublicationDto changePublicationSpotlight(PublicationDto publicationDto);
    void updatePublicationBySlug(PublicationDto publicationDto);
    void deletePublicationBySlug(String slug);

}

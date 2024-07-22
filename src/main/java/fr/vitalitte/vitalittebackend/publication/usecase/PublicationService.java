package fr.vitalitte.vitalittebackend.publication.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.publication.rest.CreatePublicationBody;
import fr.vitalitte.vitalittebackend.publication.rest.PublicationDto;

import java.util.List;
import org.springframework.data.domain.Page;

public interface PublicationService {

    void createPublication(CreatePublicationBody createPublicationBody);
    List<PublicationDto> getPublicationsSpotlighted(boolean boolbool);
    PublicationDto getPublicationBySlug(String slug);
    Page<PublicationDto> getPublicationsPaginatedBySearchValue(PaginationItemBySearchValue paginationItemBySearchValue);
    PublicationDto changePublicationSpotlight(PublicationDto publicationDto);
    void updatePublicationBySlug(PublicationDto publicationDto);
    void deletePublicationBySlug(String slug);

}

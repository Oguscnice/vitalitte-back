package fr.vitalitte.vitalittebackend.publication.usecase;

import fr.vitalitte.vitalittebackend.publication.rest.CreatePublicationBody;
import fr.vitalitte.vitalittebackend.publication.rest.PublicationDto;
import fr.vitalitte.vitalittebackend.publication.rest.PublicationPaginated;

import java.util.List;

public interface PublicationService {

    void createPublication(CreatePublicationBody createPublicationBody);
    List<PublicationDto> getPublicationsSpotlighted(boolean boolbool);
    PublicationDto getPublicationBySlug(String slug);
    List<PublicationDto> findAllPublications();
    Long countPublications(PublicationPaginated publicationPaginated);
    List<PublicationDto> getPublicationsByPageAndSize(PublicationPaginated publicationPaginated);
    PublicationDto changePublicationSpotlight(PublicationDto publicationDto);
    void updatePublicationBySlug(PublicationDto publicationDto);
    void deletePublicationBySlug(String slug);

}

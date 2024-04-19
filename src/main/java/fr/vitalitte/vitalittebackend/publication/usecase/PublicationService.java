package fr.vitalitte.vitalittebackend.publication.usecase;

import fr.vitalitte.vitalittebackend.publication.rest.CreatePublicationBody;
import fr.vitalitte.vitalittebackend.publication.rest.PublicationDto;

import java.util.List;

public interface PublicationService {
    void createPublication(CreatePublicationBody createPublicationBody);
    List<PublicationDto> getPublicationsSpotlighted(boolean boolbool);
    PublicationDto getPublicationBySlug(String slug);
    List<PublicationDto> findAllPublications();
    Long countAllPublications();
    Long countPublicationsByTitleOrDescriptionContainingValue(String value);
    List<PublicationDto> getPublicationsByPage(int pageNumber);
    List<PublicationDto> getPublicationsByTitleOrDescriptionContainingValue(int pageNumber, String value);
    PublicationDto changePublicationSpotlight(PublicationDto publicationDto);
    void updatePublicationBySlug(PublicationDto publicationDto);
    void deletePublicationBySlug(String slug);
}

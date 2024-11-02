package fr.vitalitte.vitalittebackend.publication.usecase;

import fr.vitalitte.vitalittebackend.common.usecase.TransformUrl;
import fr.vitalitte.vitalittebackend.publication.exception.PublicationNotFoundException;
import fr.vitalitte.vitalittebackend.publication.models.Publication;
import fr.vitalitte.vitalittebackend.publication.persistence.PublicationRepository;
import fr.vitalitte.vitalittebackend.publication.rest.PublicationDto;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.usecase.ListMapperUtil.mapList;

import java.util.List;

@Service
public class TransformPublication {

    TransformUrl transformUrl;
    PublicationRepository publicationRepository;

    public TransformPublication(TransformUrl transformUrl, PublicationRepository publicationRepository) {
        this.transformUrl = transformUrl;
        this.publicationRepository = publicationRepository;
    }

    public PublicationDto publicationToDto(Publication publication) {

        String picture = this.transformUrl.urlToString(publication.getPicture());
        String pictureThumbnail = this.transformUrl.urlToString(publication.getPictureThumbnail());

        return PublicationDto.builder()
                .slug(publication.getSlug())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .isSpotlighted(publication.isSpotlighted())
                .picture(picture)
                .pictureThumbnail(pictureThumbnail)
                .createdAt(publication.getCreatedAt())
                .build();
    }

    public List<PublicationDto> publicationsToDtos(List<Publication> publications) {
        return mapList(this::publicationToDto, publications);
    }

    public Publication dtoToPublication(PublicationDto publicationDto) {
        return this.publicationRepository.findBySlug(publicationDto.getSlug())
                .orElseThrow(PublicationNotFoundException::new);
    }

    public List<Publication> dtosToPublications(List<PublicationDto> publicationDtos) {
        return mapList(this::dtoToPublication, publicationDtos);
    }
}

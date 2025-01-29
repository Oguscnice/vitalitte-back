package fr.vitalitte.vitalittebackend.publication.usecase;

import fr.vitalitte.vitalittebackend.common.models.FileEntity;
import fr.vitalitte.vitalittebackend.common.persistence.FileRepository;
import fr.vitalitte.vitalittebackend.common.usecase.TransformFile;
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

    PublicationRepository publicationRepository;
    FileRepository fileRepository;
    TransformFile transformFile;

    public TransformPublication(PublicationRepository publicationRepository, FileRepository fileRepository, TransformFile transformFile) {
        this.publicationRepository = publicationRepository;
        this.fileRepository = fileRepository;
        this.transformFile = transformFile;
    }

    public PublicationDto publicationToDto(Publication publication) {

        FileEntity file = this.fileRepository.findByLinkedSlugAndIsMainPictureTrue(publication.getSlug());

        return PublicationDto.builder()
                .slug(publication.getSlug())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .isSpotlighted(publication.isSpotlighted())
                .pictureDto(transformFile.fileToDto(file))
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

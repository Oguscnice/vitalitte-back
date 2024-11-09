package fr.vitalitte.vitalittebackend.publication.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.common.usecase.FileService;
import fr.vitalitte.vitalittebackend.common.usecase.SlugifyUtil;
import fr.vitalitte.vitalittebackend.publication.exception.PublicationNotFoundException;
import fr.vitalitte.vitalittebackend.publication.exception.SlugPublicationAlreadyExistsException;
import fr.vitalitte.vitalittebackend.publication.models.Publication;
import fr.vitalitte.vitalittebackend.publication.persistence.PublicationRepository;
import fr.vitalitte.vitalittebackend.publication.rest.CreatePublicationBody;
import fr.vitalitte.vitalittebackend.publication.rest.PublicationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PublicationServiceImpl implements PublicationService {

    PublicationRepository publicationRepository;
    TransformPublication transformPublication;
    SlugifyUtil slugifyUtil;
    FileService fileService;

    public PublicationServiceImpl(PublicationRepository publicationRepository, TransformPublication transformPublication, SlugifyUtil slugifyUtil, FileService fileService) {
        this.publicationRepository = publicationRepository;
        this.transformPublication = transformPublication;
        this.slugifyUtil = slugifyUtil;
        this.fileService = fileService;
    }

    @Override
    public void createPublication(CreatePublicationBody createPublicationBody) {

        String publicationSlug = slugifyPublication(createPublicationBody);
        slugifyUtil.verifyIfSlugAlreadyExists(publicationSlug);

        fileService.createFile(createPublicationBody.getPictureDto(), true, publicationSlug);

        final Publication newPublication = Publication.builder()
                .slug(publicationSlug)
                .title(createPublicationBody.getTitle())
                .description(createPublicationBody.getDescription())
                .build();

        publicationRepository.save(newPublication);

    }

    @Override
    public List<PublicationDto> getPublicationsSpotlighted(boolean value) {
        List<Publication> publications = publicationRepository.findAllPublicationsByIsSpotlighted(value);
        return transformPublication.publicationsToDtos(publications);
    }

    @Override
    public PublicationDto getPublicationBySlug(String slug) {
        return transformPublication.publicationToDto(this.findOnePublicationBySlugOrThrow(slug));
    }

    @Override
    public Page<PublicationDto> getPublicationsPaginatedBySearchValue(PaginationItemBySearchValue paginationItemBySearchValue) {

        String value = paginationItemBySearchValue.getSearchValue();
        String title = value.isBlank() ? null : value;
        String description = value.isBlank() ? null : value;
        Pageable pageable = PageRequest.of(paginationItemBySearchValue.getPageableValues().getPageNumber(), paginationItemBySearchValue.getPageableValues().getPageSize());

        Page<Publication> publicationPage = publicationRepository.findAllByTitleOrDescriptionOrderByCreatedAtDesc(title, description, pageable);
        List<PublicationDto> publicationDtoList = transformPublication.publicationsToDtos(publicationPage.getContent());

        return new PageImpl<>(publicationDtoList, pageable, publicationPage.getTotalElements());
    }

    @Override
    public PublicationDto changePublicationSpotlight(PublicationDto publicationDtoUpdated) {

        Publication publicationToUpdate = this.findOnePublicationBySlugOrThrow(publicationDtoUpdated.getSlug());
        publicationToUpdate.setSpotlighted(!publicationToUpdate.isSpotlighted());
        publicationRepository.save(publicationToUpdate);

        return this.transformPublication.publicationToDto(publicationToUpdate);
    }

    @Override
    public void updatePublicationBySlug(PublicationDto publicationDtoUpdated) {

        Publication publicationToUpdate = this.findOnePublicationBySlugOrThrow(publicationDtoUpdated.getSlug());

        String newPublicationSlug = slugifyPublication(publicationDtoUpdated);
        if (!publicationToUpdate.getSlug().equals(newPublicationSlug)){
            slugifyUtil.verifyIfSlugAlreadyExists(newPublicationSlug);
        }
        
        publicationToUpdate.setTitle(publicationDtoUpdated.getTitle());
        publicationToUpdate.setSlug(newPublicationSlug);
        publicationToUpdate.setDescription(publicationDtoUpdated.getDescription());
        fileService.updateFile(publicationDtoUpdated.getPictureDto(), newPublicationSlug);

        publicationRepository.save(publicationToUpdate);
    }

    @Override
    public void deletePublicationBySlug(String slug) {
        Publication publicationToDelete = this.findOnePublicationBySlugOrThrow(slug);
        publicationRepository.delete(publicationToDelete);
        fileService.deleteAllFilesByLinkedSlug(slug);
    }

    private String slugifyPublication(CreatePublicationBody createPublicationBody) {
        return slugUtil(createPublicationBody.getTitle());
    }

    private String slugifyPublication(PublicationDto publicationDto) {
        return slugUtil(publicationDto.getTitle());
    }

    private String slugUtil(String title) {
        return slugifyUtil.stringToSlug(title + '-' + slugifyUtil.dateToFormatDDmmYY(new Date()));
    }

    private Publication findOnePublicationBySlugOrThrow(String slug) {
        return publicationRepository.findBySlug(slug).orElseThrow(PublicationNotFoundException::new);
    }
}

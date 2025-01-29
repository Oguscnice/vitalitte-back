package fr.vitalitte.vitalittebackend.workshop.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.common.usecase.FileService;
import fr.vitalitte.vitalittebackend.common.usecase.SlugifyUtil;
import fr.vitalitte.vitalittebackend.common.usecase.TransformUrl;
import fr.vitalitte.vitalittebackend.workshop.exception.WorkshopNotFoundException;
import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import fr.vitalitte.vitalittebackend.workshop.persistence.WorkshopRepository;
import fr.vitalitte.vitalittebackend.workshop.rest.CreateWorkshopBody;
import fr.vitalitte.vitalittebackend.workshop.rest.WorkshopDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    WorkshopRepository workshopRepository;
    TransformWorkshop transformWorkshop;
    SlugifyUtil slugifyUtil;
    FileService fileService;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository, TransformWorkshop transformWorkshop, SlugifyUtil slugifyUtil, FileService fileService) {
        this.workshopRepository = workshopRepository;
        this.transformWorkshop = transformWorkshop;
        this.slugifyUtil = slugifyUtil;
        this.fileService = fileService;
    }

    @Override
    public void createWorkshop(CreateWorkshopBody createWorkshopBody) {

        String workshopSlug = slugifyWorkshopWithTitleAndDate(createWorkshopBody.getTitle(), createWorkshopBody.getDate());
        slugifyUtil.verifyIfSlugAlreadyExists(workshopSlug);

        fileService.createFile(createWorkshopBody.getPictureDto(), true, workshopSlug);

        final Workshop newWorkshop =  Workshop.builder()
                .title(createWorkshopBody.getTitle())
                .slug(workshopSlug)
                .description(createWorkshopBody.getDescription())
                .date(createWorkshopBody.getDate())
                .address(createWorkshopBody.getAddress())
                .price(createWorkshopBody.getPrice())
                .registrations(createWorkshopBody.getRegistrations())
                .build();

        workshopRepository.save(newWorkshop);
    }

    @Override
    public WorkshopDto getWorkbookBySlug(String slug) {
        return transformWorkshop.workshopToDto(findOneWorkshopBySlugOrThrow(slug));
    }

    public List<WorkshopDto> findWorkshopsByDateToCome() {
        LocalDateTime date = LocalDateTime.now();
        return transformWorkshop.workshopsToDto(this.workshopRepository.findAllWorkshopByDateAfterOrderByDateDesc(date));
    }

    @Override
    public Page<WorkshopDto> findWorkshopsPaginatedByPastDate(PaginationItemBySearchValue paginationItemBySearchValue) {

        LocalDateTime date = LocalDateTime.now();
        String value = paginationItemBySearchValue.getSearchValue();
        Pageable pageable = PageRequest.of(paginationItemBySearchValue.getPageableValues().getPageNumber(), paginationItemBySearchValue.getPageableValues().getPageSize());

        Page<Workshop> workshopPage = workshopRepository.findWorkshopsByTitleContainsIgnoreCaseAndDateBeforeOrderByDateDesc(value, date, pageable);
        List<WorkshopDto> workshopDtoList = transformWorkshop.workshopsToDto(workshopPage.getContent());

        return new PageImpl<>(workshopDtoList, pageable, workshopPage.getTotalElements());
    }

    @Override
    public List<WorkshopDto> findWorkshopsIsAvailable(boolean value) {
        return transformWorkshop.workshopsToDto(workshopRepository.findAllWorkshopByIsAvailable(value));
    }

    @Override
    public WorkshopDto changeWorkshopAvailability(WorkshopDto workshopDtoUpdated) {
        Workshop workshopToUpdate = findOneWorkshopBySlugOrThrow(workshopDtoUpdated.getSlug());
        workshopToUpdate.setAvailable(!workshopToUpdate.isAvailable());
        workshopRepository.save(workshopToUpdate);
        return transformWorkshop.workshopToDto(workshopToUpdate);
    }

    @Override
    public void updateWorkshopBySlug(WorkshopDto workshopDtoUpdated) {
        Workshop workshopToUpdate = findOneWorkshopBySlugOrThrow(workshopDtoUpdated.getSlug());

        String newWorkbookSlug = slugifyWorkshopWithTitleAndDate(workshopDtoUpdated.getTitle(), workshopDtoUpdated.getDate());
        if (!(workshopToUpdate.getSlug().equals(newWorkbookSlug))){
            slugifyUtil.verifyIfSlugAlreadyExists(newWorkbookSlug);
        }

        workshopToUpdate.setTitle(workshopDtoUpdated.getTitle());
        workshopToUpdate.setSlug(newWorkbookSlug);
        workshopToUpdate.setDescription(workshopDtoUpdated.getDescription());
        workshopToUpdate.setDate(workshopDtoUpdated.getDate());
        workshopToUpdate.setAddress(workshopDtoUpdated.getAddress());
        workshopToUpdate.setPrice(workshopDtoUpdated.getPrice());
        workshopToUpdate.setRegistrations(workshopDtoUpdated.getRegistrations());
        fileService.updateFile(workshopDtoUpdated.getPictureDto(), newWorkbookSlug, true);

        workshopRepository.save(workshopToUpdate);
    }

    @Override
    public void deleteWorkshopBySlug(String slug){
        workshopRepository.delete(findOneWorkshopBySlugOrThrow(slug));
        fileService.deleteAllFilesByLinkedSlug(slug);
    }

    private Workshop findOneWorkshopBySlugOrThrow(String slug) {
        return workshopRepository.findBySlug(slug).orElseThrow(WorkshopNotFoundException::new);
    }

    private String slugifyWorkshopWithTitleAndDate(String title, LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String dateForSlug = date.format(formatter);
        return slugifyUtil.stringToSlug( title + '-' + dateForSlug);
    }
}

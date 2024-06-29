package fr.vitalitte.vitalittebackend.workshop.usecase;

import fr.vitalitte.vitalittebackend.common.models.Pagination;
import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.workshop.exception.SlugWorkshopAlreadyExistsException;
import fr.vitalitte.vitalittebackend.workshop.exception.WorkshopNotFoundException;
import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import fr.vitalitte.vitalittebackend.workshop.persistence.WorkshopRepository;
import fr.vitalitte.vitalittebackend.workshop.rest.CreateWorkshopBody;
import fr.vitalitte.vitalittebackend.workshop.rest.WorkshopDto;
import org.springframework.data.domain.Page;
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
    TransformUrl transformUrl;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository, TransformWorkshop transformWorkshop, TransformUrl transformUrl) {
        this.workshopRepository = workshopRepository;
        this.transformWorkshop = transformWorkshop;
        this.transformUrl = transformUrl;
    }

    @Override
    public void createWorkshop(CreateWorkshopBody createWorkshopBody){

        String workshopSlug = slugifyWorkshopWithTitleAndDate(createWorkshopBody.getTitle(), createWorkshopBody.getDate());

        existsBySlugOrThrow(workshopSlug);

        URL picture = this.transformUrl.stringToUrl(createWorkshopBody.getPicture());
        URL pictureThumbnail = this.transformUrl.stringToUrl(createWorkshopBody.getPicture());

        final Workshop newWorkshop =  Workshop.builder()
                .title(createWorkshopBody.getTitle())
                .slug(workshopSlug)
                .description(createWorkshopBody.getDescription())
                .date(createWorkshopBody.getDate())
                .address(createWorkshopBody.getAddress())
                .price(createWorkshopBody.getPrice())
                .picture(picture)
                .pictureThumbnail(pictureThumbnail)
                .registrations(createWorkshopBody.getRegistrations())
                .build();

        this.workshopRepository.save(newWorkshop);
    };

    @Override
    public WorkshopDto getWorkbookBySlug(String slug){
        return this.transformWorkshop.workshopToDto(findOneWorkshopBySlugOrThrow(slug));
    };

    @Override
    public List<WorkshopDto> findAllWorkshops(){
        return this.transformWorkshop.workshopsToDto(this.workshopRepository.findAll());
    };

    public List<WorkshopDto> findWorkshopsByDateToCome(){
        LocalDateTime date = LocalDateTime.now();
        return this.transformWorkshop.workshopsToDto(this.workshopRepository.findAllWorkshopByDateAfterOrderByDateDesc(date));
    };

    @Override
    public List<WorkshopDto> findWorkshopsPaginatedByPastDate(PaginationItemBySearchValue paginationItemBySearchValue){
        LocalDateTime date = LocalDateTime.now();
        Pageable pageable = PageRequest.of(paginationItemBySearchValue.getPagination().getPage(), paginationItemBySearchValue.getPagination().getSize());
        Page<Workshop> workshopPage = this.workshopRepository.findWorkshopsByTitleContainsIgnoreCaseAndDateBeforeOrderByDateDesc(paginationItemBySearchValue.getSearchValue(), date, pageable);
        return this.transformWorkshop.workshopsToDto(workshopPage.getContent());
    };

    @Override
    public Long getCounterWorkshopsByPastDate(){
        LocalDateTime date = LocalDateTime.now();
        return this.workshopRepository.countWorkshopsByDateBefore(date);
    };

    @Override
    public List<WorkshopDto> findWorkshopsIsAvailable(boolean value){
        return this.transformWorkshop.workshopsToDto(this.workshopRepository.findAllWorkshopByIsAvailable(value));
    };

    @Override
    public WorkshopDto changeWorkshopAvailability(WorkshopDto workshopDtoUpdated){
        Workshop workshopToUpdate = findOneWorkshopBySlugOrThrow(workshopDtoUpdated.getSlug());
        workshopToUpdate.setAvailable(!workshopToUpdate.isAvailable());
        this.workshopRepository.save(workshopToUpdate);
        return this.transformWorkshop.workshopToDto(workshopToUpdate);
    };

    @Override
    public void updateWorkshopBySlug(WorkshopDto workshopDtoUpdated){
        Workshop workshopToUpdate = findOneWorkshopBySlugOrThrow(workshopDtoUpdated.getSlug());

        String newWorkbookSlug = slugifyWorkshopWithTitleAndDate(workshopDtoUpdated.getTitle(), workshopDtoUpdated.getDate());
        if (!(workshopToUpdate.getSlug().equals(newWorkbookSlug))){
            existsBySlugOrThrow(newWorkbookSlug);
        }

        URL newPicture = this.transformUrl.stringToUrl(workshopDtoUpdated.getPicture());
        URL newPictureThumbnail = this.transformUrl.stringToUrl(workshopDtoUpdated.getPictureThumbnail());

        workshopToUpdate.setTitle(workshopDtoUpdated.getTitle());
        workshopToUpdate.setSlug(newWorkbookSlug);
        workshopToUpdate.setDescription(workshopDtoUpdated.getDescription());
        workshopToUpdate.setDate(workshopDtoUpdated.getDate());
        workshopToUpdate.setAddress(workshopDtoUpdated.getAddress());
        workshopToUpdate.setPrice(workshopDtoUpdated.getPrice());
        workshopToUpdate.setPicture(newPicture);
        workshopToUpdate.setPictureThumbnail(newPictureThumbnail);
        workshopToUpdate.setRegistrations(workshopDtoUpdated.getRegistrations());

        this.workshopRepository.save(workshopToUpdate);
    };

    @Override
    public void deleteWorkshopBySlug(String slug){
        this.workshopRepository.delete(findOneWorkshopBySlugOrThrow(slug));
    };

    private Workshop findOneWorkshopBySlugOrThrow(String slug) {
        return this.workshopRepository.findBySlug(slug).orElseThrow(WorkshopNotFoundException::new);
    }

    private String slugifyWorkshopWithTitleAndDate(String title, LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String dateForSlug = date.format(formatter);
        return SlugifyUtil.stringToSlug( title + '-' + dateForSlug);
    }

    private void existsBySlugOrThrow(String slug) {
        if (this.workshopRepository.existsBySlug(slug)){
            throw new SlugWorkshopAlreadyExistsException();
        }
    }
}

package fr.vitalitte.vitalittebackend.workshop.usecase;

import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.common.utils.TransformUrl;
import fr.vitalitte.vitalittebackend.workshop.exception.SlugWorkshopAlreadyExistsException;
import fr.vitalitte.vitalittebackend.workshop.exception.WorkshopNotFoundException;
import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import fr.vitalitte.vitalittebackend.workshop.persistence.WorkshopRepository;
import fr.vitalitte.vitalittebackend.workshop.rest.CreateWorkshopBody;
import fr.vitalitte.vitalittebackend.workshop.rest.WorkshopDto;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WorkshopServiceImpl implements WorkshopService{
    WorkshopRepository workshopRepository;
    TransformWorkshop transformWorkshop;
    TransformUrl transformUrl;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository, TransformWorkshop transformWorkshop, TransformUrl transformUrl) {
        this.workshopRepository = workshopRepository;
        this.transformWorkshop = transformWorkshop;
        this.transformUrl = transformUrl;
    }

    public void createWorkshop(CreateWorkshopBody createWorkshopBody){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String dateForSlug = createWorkshopBody.getDate().format(formatter);
        String newSlug = SlugifyUtil.stringToSlug(createWorkshopBody.getTitle() + '-' + dateForSlug);

        if (this.workshopRepository.existsBySlug(newSlug)){
            throw new SlugWorkshopAlreadyExistsException();
        }

        URL newPicture = this.transformUrl.stringToUrl(createWorkshopBody.getPicture());

        final Workshop newWorkshop =  Workshop.builder()
                .title(createWorkshopBody.getTitle())
                .slug(newSlug)
                .description(createWorkshopBody.getDescription())
                .date(createWorkshopBody.getDate())
                .address(createWorkshopBody.getAddress())
                .price(createWorkshopBody.getPrice())
                .picture(newPicture)
                .registrations(createWorkshopBody.getRegistrations())
                .build();

        this.workshopRepository.save(newWorkshop);
    };
    public WorkshopDto getWorkbookBySlug(String slug){
        return this.transformWorkshop.workshopToDto(this.workshopRepository.findBySlug(slug)
                                                            .orElseThrow(WorkshopNotFoundException::new));
    };
    public List<WorkshopDto> findAllWorkshops(){
        return this.transformWorkshop.workshopsToDto(this.workshopRepository.findAll());
    };
    public WorkshopDto changeWorkshopAvailability(WorkshopDto workshopDtoUpdated){
        Workshop workshopToUpdate = this.workshopRepository.findBySlug(workshopDtoUpdated.getSlug())
                                            .orElseThrow(WorkshopNotFoundException::new);

        workshopToUpdate.setAvailable(!workshopToUpdate.isAvailable());
        this.workshopRepository.save(workshopToUpdate);
        return this.transformWorkshop.workshopToDto(workshopToUpdate);
    };
    public void updateWorkshopBySlug(WorkshopDto workshopDtoUpdated){
        Workshop workshopToUpdate = this.workshopRepository.findBySlug(workshopDtoUpdated.getSlug())
                                            .orElseThrow(WorkshopNotFoundException::new);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String dateForSlug = workshopDtoUpdated.getDate().format(formatter);
        String newSlug = SlugifyUtil.stringToSlug(workshopDtoUpdated.getTitle() + '-' + dateForSlug);
        if (this.workshopRepository.existsBySlug(newSlug) && !(workshopToUpdate.getSlug().equals(newSlug))){
            throw new SlugWorkshopAlreadyExistsException();
        }

        URL newPicture = this.transformUrl.stringToUrl(workshopDtoUpdated.getPicture());

        workshopToUpdate.setTitle(workshopDtoUpdated.getTitle());
        workshopToUpdate.setSlug(newSlug);
        workshopToUpdate.setDescription(workshopDtoUpdated.getDescription());
        workshopToUpdate.setDate(workshopDtoUpdated.getDate());
        workshopToUpdate.setAddress(workshopDtoUpdated.getAddress());
        workshopToUpdate.setPrice(workshopDtoUpdated.getPrice());
        workshopToUpdate.setPicture(newPicture);
        workshopToUpdate.setRegistrations(workshopDtoUpdated.getRegistrations());

        this.workshopRepository.save(workshopToUpdate);
    };
    public void deleteWorkshopBySlug(String slug){
        Workshop workshopToDelete = this.workshopRepository.findBySlug(slug)
                                            .orElseThrow(WorkshopNotFoundException::new);

        this.workshopRepository.delete(workshopToDelete);
    };
}

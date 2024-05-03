package fr.vitalitte.vitalittebackend.workshop.usecase;

import fr.vitalitte.vitalittebackend.workshop.rest.CreateWorkshopBody;
import fr.vitalitte.vitalittebackend.workshop.rest.WorkshopDto;

import java.util.List;

public interface WorkshopService {
    void createWorkshop(CreateWorkshopBody createWorkshopBody);
    WorkshopDto getWorkbookBySlug(String slug);
    List<WorkshopDto> findAllWorkshops();
    List<WorkshopDto> findWorkshopsByDateToCome();
    List<WorkshopDto> findWorkshopsByPastDate(int pageNumber);
    Long getCounterWorkshopsByPastDate();
    List<WorkshopDto> findWorkshopsIsAvailable(boolean value);
    WorkshopDto changeWorkshopAvailability(WorkshopDto workshopDto);
    void updateWorkshopBySlug(WorkshopDto workshopDto);
    void deleteWorkshopBySlug(String slug);
}

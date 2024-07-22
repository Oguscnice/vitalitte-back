package fr.vitalitte.vitalittebackend.workshop.usecase;

import fr.vitalitte.vitalittebackend.common.models.PaginationItemBySearchValue;
import fr.vitalitte.vitalittebackend.workshop.rest.CreateWorkshopBody;
import fr.vitalitte.vitalittebackend.workshop.rest.WorkshopDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WorkshopService {
    void createWorkshop(CreateWorkshopBody createWorkshopBody);
    WorkshopDto getWorkbookBySlug(String slug);
    List<WorkshopDto> findWorkshopsByDateToCome();
    Page<WorkshopDto> findWorkshopsPaginatedByPastDate(PaginationItemBySearchValue paginationItemBySearchValue);
    List<WorkshopDto> findWorkshopsIsAvailable(boolean value);
    WorkshopDto changeWorkshopAvailability(WorkshopDto workshopDto);
    void updateWorkshopBySlug(WorkshopDto workshopDto);
    void deleteWorkshopBySlug(String slug);
}

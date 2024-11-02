package fr.vitalitte.vitalittebackend.workshop.usecase;

import fr.vitalitte.vitalittebackend.common.usecase.TransformUrl;
import fr.vitalitte.vitalittebackend.workshop.exception.WorkshopNotFoundException;
import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import fr.vitalitte.vitalittebackend.workshop.persistence.WorkshopRepository;
import fr.vitalitte.vitalittebackend.workshop.rest.WorkshopDto;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.usecase.ListMapperUtil.mapList;

import java.util.List;

@Service
public class TransformWorkshop {
    TransformUrl transformUrl;
    WorkshopRepository workshopRepository;

    public TransformWorkshop(TransformUrl transformUrl, WorkshopRepository workshopRepository) {
        this.transformUrl = transformUrl;
        this.workshopRepository = workshopRepository;
    }

    public WorkshopDto workshopToDto(Workshop workshop) {

        String picture = this.transformUrl.urlToString(workshop.getPicture());
        String pictureThumbnail = this.transformUrl.urlToString(workshop.getPictureThumbnail());

        return WorkshopDto.builder()
                .title(workshop.getTitle())
                .slug(workshop.getSlug())
                .description(workshop.getDescription())
                .date(workshop.getDate())
                .address(workshop.getAddress())
                .price(workshop.getPrice())
                .picture(picture)
                .pictureThumbnail(pictureThumbnail)
                .registrations(workshop.getRegistrations())
                .isAvailable(workshop.isAvailable())
                .build();
    }
    public List<WorkshopDto> workshopsToDto(List<Workshop> workshops){
        return mapList(this::workshopToDto, workshops);
    }

    public Workshop dtoToWorkshop(WorkshopDto workshopDto){
        return this.workshopRepository.findBySlug(workshopDto.getSlug())
                .orElseThrow(WorkshopNotFoundException::new);
    }
    public List<Workshop> dtosToWorkshops(List<WorkshopDto> workshopDtos){
        return mapList(this::dtoToWorkshop, workshopDtos);
    }
}

package fr.vitalitte.vitalittebackend.workshop.usecase;

import fr.vitalitte.vitalittebackend.common.models.FileEntity;
import fr.vitalitte.vitalittebackend.common.persistence.FileRepository;
import fr.vitalitte.vitalittebackend.common.usecase.TransformFile;
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

    WorkshopRepository workshopRepository;
    FileRepository fileRepository;
    TransformFile transformFile;


    public TransformWorkshop(WorkshopRepository workshopRepository, FileRepository fileRepository, TransformFile transformFile) {
        this.workshopRepository = workshopRepository;
        this.fileRepository = fileRepository;
        this.transformFile = transformFile;
    }

    public WorkshopDto workshopToDto(Workshop workshop) {

        FileEntity file = this.fileRepository.findByLinkedSlugAndIsMainPictureTrue(workshop.getSlug());

        return WorkshopDto.builder()
                .title(workshop.getTitle())
                .slug(workshop.getSlug())
                .description(workshop.getDescription())
                .date(workshop.getDate())
                .address(workshop.getAddress())
                .price(workshop.getPrice())
                .pictureDto(transformFile.fileToDto(file))
                .registrations(workshop.getRegistrations())
                .isAvailable(workshop.isAvailable())
                .build();
    }

    public List<WorkshopDto> workshopsToDto(List<Workshop> workshops) {
        return mapList(this::workshopToDto, workshops);
    }

    public Workshop dtoToWorkshop(WorkshopDto workshopDto) {
        return this.workshopRepository.findBySlug(workshopDto.getSlug())
                .orElseThrow(WorkshopNotFoundException::new);
    }
    public List<Workshop> dtosToWorkshops(List<WorkshopDto> workshopDtos) {
        return mapList(this::dtoToWorkshop, workshopDtos);
    }
}

package fr.vitalitte.vitalittebackend.inscription.usecase;

import fr.vitalitte.vitalittebackend.inscription.exception.InscriptionNotFoundException;
import fr.vitalitte.vitalittebackend.inscription.models.Inscription;
import fr.vitalitte.vitalittebackend.inscription.persistence.InscriptionRepository;
import fr.vitalitte.vitalittebackend.inscription.rest.InscriptionDto;
import fr.vitalitte.vitalittebackend.workshop.exception.WorkshopNotFoundException;
import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import fr.vitalitte.vitalittebackend.workshop.persistence.WorkshopRepository;
import fr.vitalitte.vitalittebackend.workshop.usecase.TransformWorkshop;
import org.springframework.stereotype.Service;

import static fr.vitalitte.vitalittebackend.common.usecase.ListMapperUtil.mapList;

import java.util.List;

@Service
public class TransformInscription {

    TransformWorkshop transformWorkshop;
    WorkshopRepository workshopRepository;
    InscriptionRepository inscriptionRepository;

    public TransformInscription(TransformWorkshop transformWorkshop, WorkshopRepository workshopRepository, InscriptionRepository inscriptionRepository) {
        this.transformWorkshop = transformWorkshop;
        this.workshopRepository = workshopRepository;
        this.inscriptionRepository = inscriptionRepository;
    }

    public InscriptionDto inscriptionToDto(Inscription inscription){
        Workshop workshopFound = this.workshopRepository.findBySlug(inscription.getWorkshop().getSlug())
                                        .orElseThrow(WorkshopNotFoundException::new);

        return InscriptionDto.builder()
                .slug(inscription.getSlug())
                .firstname(inscription.getFirstname())
                .lastname(inscription.getLastname())
                .phone(inscription.getPhone())
                .email(inscription.getEmail())
                .workshopDto(this.transformWorkshop.workshopToDto(workshopFound))
                .isConfirmed(inscription.isConfirmed())
                .quantity(inscription.getQuantity())
                .createdAt(inscription.getCreatedAt())
                .build();
    }
    public List<InscriptionDto> inscriptionsToDto(List<Inscription> inscriptions){
        return mapList(this::inscriptionToDto, inscriptions);
    }

    public Inscription dtoToInscription(InscriptionDto inscriptionDto){
        return this.inscriptionRepository.findBySlug(inscriptionDto.getSlug())
                        .orElseThrow(InscriptionNotFoundException::new);
    }
    public List<Inscription> dtosToInscriptions(List<InscriptionDto> inscriptionsDto){
        return mapList(this::dtoToInscription, inscriptionsDto);
    }
}

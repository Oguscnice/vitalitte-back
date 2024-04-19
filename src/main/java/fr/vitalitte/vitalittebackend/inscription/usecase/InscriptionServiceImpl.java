package fr.vitalitte.vitalittebackend.inscription.usecase;

import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.inscription.exception.InscriptionNotFoundException;
import fr.vitalitte.vitalittebackend.inscription.exception.SlugInscriptionAlreadyExistsException;
import fr.vitalitte.vitalittebackend.inscription.models.Inscription;
import fr.vitalitte.vitalittebackend.inscription.persistence.InscriptionRepository;
import fr.vitalitte.vitalittebackend.inscription.rest.CreateInscriptionBody;
import fr.vitalitte.vitalittebackend.inscription.rest.InscriptionDto;
import fr.vitalitte.vitalittebackend.workshop.exception.WorkshopNotFoundException;
import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import fr.vitalitte.vitalittebackend.workshop.persistence.WorkshopRepository;
import fr.vitalitte.vitalittebackend.workshop.usecase.TransformWorkshop;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class InscriptionServiceImpl implements InscriptionService{
    WorkshopRepository workshopRepository;
    InscriptionRepository inscriptionRepository;
    TransformWorkshop transformWorkshop;
    TransformInscription transformInscription;

    public InscriptionServiceImpl(WorkshopRepository workshopRepository, InscriptionRepository inscriptionRepository, TransformWorkshop transformWorkshop, TransformInscription transformInscription) {
        this.workshopRepository = workshopRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.transformWorkshop = transformWorkshop;
        this.transformInscription = transformInscription;
    }

    public void createInscription(CreateInscriptionBody createInscriptionBody){

        String newSlug = SlugifyUtil.stringToSlug(createInscriptionBody.getLastname() + '-' + createInscriptionBody.getWorkshopDto().getSlug());

        if (this.inscriptionRepository.existsBySlug(newSlug)) {
            throw new SlugInscriptionAlreadyExistsException();
        }

        Workshop workshop = this.workshopRepository.findBySlug(createInscriptionBody.getWorkshopDto().getSlug())
                                    .orElseThrow(WorkshopNotFoundException::new);

        final Inscription newInscription = Inscription.builder()
                .slug(newSlug)
                .firstname(createInscriptionBody.getFirstname())
                .lastname(createInscriptionBody.getLastname())
                .phone(createInscriptionBody.getPhone())
                .email(createInscriptionBody.getEmail())
                .workshop(workshop)
                .build();

        this.inscriptionRepository.save(newInscription);
    };
    public InscriptionDto findInscriptionBySlug(String slug){
        return this.transformInscription.inscriptionToDto(this.inscriptionRepository.findBySlug(slug)
                                                                    .orElseThrow(InscriptionNotFoundException::new));
    };
    public List<InscriptionDto> findAllInscriptions(){
        return this.transformInscription.inscriptionsToDto(this.inscriptionRepository.findAll());
    };
    public List<InscriptionDto> findAllInscriptionsByWorkshop(String workshopSlug){
        Workshop workshop = this.workshopRepository.findBySlug(workshopSlug)
                                    .orElseThrow(WorkshopNotFoundException::new);
        return this.transformInscription.inscriptionsToDto(this.inscriptionRepository.findAllByWorkshop(workshop));
    };
    public void deleteInscriptionBySlug(String slug){
        Inscription inscriptionToDelete = this.inscriptionRepository.findBySlug(slug)
                                                    .orElseThrow(InscriptionNotFoundException::new);
        this.inscriptionRepository.delete(inscriptionToDelete);
    };

    public Long countInscriptionsByWorkshopSlug(String workshopSlug){
        Workshop workshopFound = this.workshopRepository.findBySlug(workshopSlug)
                .orElseThrow(WorkshopNotFoundException::new);
       return this.inscriptionRepository.countInscriptionsByWorkshop(workshopFound);
    };
}

package fr.vitalitte.vitalittebackend.inscription.usecase;

import fr.vitalitte.vitalittebackend.common.utils.SlugifyUtil;
import fr.vitalitte.vitalittebackend.inscription.exception.InscriptionNotAvailableException;
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

import java.time.LocalDateTime;
import java.util.Date;
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

    @Override
    public InscriptionDto createInscription(CreateInscriptionBody createInscriptionBody){

        String newSlug = SlugifyUtil.stringToSlug(SlugifyUtil.dateToFormatDDmmYY(new Date()) + '-' + createInscriptionBody.getLastname() + '-' + createInscriptionBody.getFirstname() + '-' + createInscriptionBody.getEmail() + '-' + createInscriptionBody.getWorkshopDto().getSlug());

        if (this.inscriptionRepository.existsBySlug(newSlug)) {
            throw new SlugInscriptionAlreadyExistsException();
        }

        Workshop workshop = this.findWorkshopBySlug(createInscriptionBody.getWorkshopDto().getSlug());

        if((workshop.getRegistrations() != 0) && (workshop.getRegistrations() < this.countInscriptionsByWorkshopSlug(workshop.getSlug()))) {
            throw  new InscriptionNotAvailableException();
        }

        final Inscription newInscription = Inscription.builder()
                .slug(newSlug)
                .firstname(createInscriptionBody.getFirstname())
                .lastname(createInscriptionBody.getLastname())
                .phone(createInscriptionBody.getPhone())
                .email(createInscriptionBody.getEmail())
                .workshop(workshop)
                .quantity(createInscriptionBody.getQuantity())
                .build();

        InscriptionDto inscriptionDto = this.transformInscription.inscriptionToDto(this.inscriptionRepository.save(newInscription));

        return inscriptionDto;
    };

    @Override
    public InscriptionDto findInscription(String slug){
        return this.transformInscription.inscriptionToDto(this.findInscriptionBySlug(slug));
    };

    @Override
    public List<InscriptionDto> findAllInscriptions(){
        return this.transformInscription.inscriptionsToDto(this.inscriptionRepository.findAll());
    };

    @Override
    public List<InscriptionDto> findAllInscriptionsByWorkshop(String workshopSlug){
        Workshop workshop = this.findWorkshopBySlug(workshopSlug);
        return this.transformInscription.inscriptionsToDto(this.inscriptionRepository.findAllByWorkshop(workshop));
    };

    @Override
    public void deleteInscriptionBySlug(String slug){
        Inscription inscriptionToDelete = this.findInscriptionBySlug(slug);
        this.inscriptionRepository.delete(inscriptionToDelete);
    };

    @Override
    public String confirmInscriptionBySlug(String inscriptionSlug) {

        Inscription inscriptionToUpdated = this.findInscriptionBySlug(inscriptionSlug);
        Workshop workshop = this.findWorkshopBySlug(inscriptionToUpdated.getWorkshop().getSlug());

        inscriptionToUpdated.setConfirmed(true);
        this.inscriptionRepository.save(inscriptionToUpdated);

        return String.format("L'inscription pour %d personne(s) au nom de %s %s pour l'atelier : %s du %s, est réalisé avec succès.", inscriptionToUpdated.getQuantity(), inscriptionToUpdated.getFirstname(), inscriptionToUpdated.getLastname(), workshop.getTitle(), workshop.getDate());

    }

    @Override
    public Long countInscriptionsByWorkshopSlug(String workshopSlug){
        Workshop workshop = this.findWorkshopBySlug(workshopSlug);
        List<Inscription> inscriptions = this.inscriptionRepository.findAllByWorkshop(workshop);
        return (long) this.countInscriptionQuantityByInscriptions(inscriptions);
    };

    private Workshop findWorkshopBySlug(String slug) {
        return this.workshopRepository.findBySlug(slug)
                .orElseThrow(WorkshopNotFoundException::new);
    }

    private Inscription findInscriptionBySlug(String slug) {
        return this.inscriptionRepository.findBySlug(slug)
                .orElseThrow(InscriptionNotFoundException::new);
    }

    private int countInscriptionQuantityByInscriptions(List<Inscription> inscriptions) {
        int inscriptionCounter = 0;

        for (Inscription inscription : inscriptions) {
            inscriptionCounter = inscriptionCounter + inscription.getQuantity();
        }
        return inscriptionCounter;
    }

}

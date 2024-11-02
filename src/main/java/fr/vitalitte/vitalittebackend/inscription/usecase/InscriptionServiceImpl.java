package fr.vitalitte.vitalittebackend.inscription.usecase;

import fr.vitalitte.vitalittebackend.common.usecase.CapitalizeStringUtil;
import fr.vitalitte.vitalittebackend.common.usecase.SlugifyUtil;
import fr.vitalitte.vitalittebackend.inscription.exception.InscriptionNotAvailableException;
import fr.vitalitte.vitalittebackend.inscription.exception.InscriptionNotFoundException;
import fr.vitalitte.vitalittebackend.inscription.models.Inscription;
import fr.vitalitte.vitalittebackend.inscription.persistence.InscriptionRepository;
import fr.vitalitte.vitalittebackend.inscription.rest.CreateInscriptionBody;
import fr.vitalitte.vitalittebackend.inscription.rest.InscriptionDto;
import fr.vitalitte.vitalittebackend.workshop.exception.WorkshopNotFoundException;
import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import fr.vitalitte.vitalittebackend.workshop.persistence.WorkshopRepository;
import fr.vitalitte.vitalittebackend.workshop.usecase.TransformWorkshop;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@EnableScheduling
@Service
public class InscriptionServiceImpl implements InscriptionService {

    WorkshopRepository workshopRepository;
    InscriptionRepository inscriptionRepository;
    TransformWorkshop transformWorkshop;
    TransformInscription transformInscription;
    SlugifyUtil slugifyUtil;

    public InscriptionServiceImpl(WorkshopRepository workshopRepository, InscriptionRepository inscriptionRepository, TransformWorkshop transformWorkshop, TransformInscription transformInscription, SlugifyUtil slugifyUtil) {
        this.workshopRepository = workshopRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.transformWorkshop = transformWorkshop;
        this.transformInscription = transformInscription;
        this.slugifyUtil = slugifyUtil;
    }

    @Override
    public InscriptionDto createInscription(CreateInscriptionBody createInscriptionBody){

        String newSlug = slugifyUtil.stringToSlug(slugifyUtil.dateToFormatDDmmYY(new Date()) + '-' + createInscriptionBody.getLastname() + '-' + createInscriptionBody.getFirstname() + '-' + createInscriptionBody.getWorkshopDto().getSlug());

        while (this.inscriptionRepository.existsBySlug(newSlug)) {
            newSlug = newSlug + "-bis";
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

        return this.transformInscription.inscriptionToDto(this.inscriptionRepository.save(newInscription));
    };

    @Override
    public InscriptionDto findInscription(String slug){
        return this.transformInscription.inscriptionToDto(findInscriptionBySlug(slug));
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
        Inscription inscriptionToDelete = findInscriptionBySlug(slug);
        this.inscriptionRepository.delete(inscriptionToDelete);
    };

    @Override
    public void changeQuantityInscription(String addOrRemoveParticipant, InscriptionDto inscriptionDto) {

        int addOrRemoveValue = 0;
        if (addOrRemoveParticipant.equals("add-participant")) {
            addOrRemoveValue = 1;
        }
        if (addOrRemoveParticipant.equals("remove-participant")) {
            addOrRemoveValue = -1;
        }

        Inscription originalInscription = findInscriptionBySlug(inscriptionDto.getSlug());
        Workshop workshop = this.findWorkshopBySlug(inscriptionDto.getWorkshopDto().getSlug());
        List<Inscription> inscriptionsByWorkshop = this.inscriptionRepository.findAllByWorkshop(workshop);

        // on incrémente une valuer pour vérifier le nombre de réservations déjà en place
        int registrations = 0;
        // dans la liste des inscriptions du même Atelier
        for (Inscription inscription : inscriptionsByWorkshop) {
            registrations += inscription.getQuantity();
        }

        // Si le nombre d'inscriptions autorisées est inférieur
        // aux inscriptions déjà en cours + celles de l'inscription en cours de changement
        // on jète une erreur
        if (workshop.getRegistrations() < (registrations + inscriptionDto.getQuantity() - originalInscription.getQuantity()) + addOrRemoveValue) {
            throw new InscriptionNotAvailableException();
        }

        originalInscription.setQuantity(inscriptionDto.getQuantity() + addOrRemoveValue);
        this.inscriptionRepository.save(originalInscription);
    }

    @Override
    public String confirmInscriptionBySlug(String inscriptionSlug) {

        Inscription inscriptionToUpdated = findInscriptionBySlug(inscriptionSlug);
        Workshop workshop = this.findWorkshopBySlug(inscriptionToUpdated.getWorkshop().getSlug());

        inscriptionToUpdated.setConfirmed(true);
        this.inscriptionRepository.save(inscriptionToUpdated);

        return String.format("L'inscription pour %d personne(s) au nom de %s %s pour l'atelier : %s du %s, est réalisée avec succès.",
                inscriptionToUpdated.getQuantity(),
                CapitalizeStringUtil.firstLetter(inscriptionToUpdated.getFirstname()),
                inscriptionToUpdated.getLastname().toUpperCase(),
                workshop.getTitle(),
                workshop.getDate());
    }

    @Override
    public Long countInscriptionsByWorkshopSlug(String workshopSlug){
        Workshop workshop = this.findWorkshopBySlug(workshopSlug);
        List<Inscription> inscriptions = this.inscriptionRepository.findAllByWorkshop(workshop);
        return (long) this.countInscriptionQuantityByInscriptions(inscriptions);
    }

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

    @Scheduled(fixedDelayString= "${vitalitte-project.app.scheduled.fixed-delay.delete-unpaid-registrations}")
    public void deleteUnpaidRegistrations() {

        List<Inscription> inscriptionsUnconfirmed = this.inscriptionRepository.findAllByIsConfirmedFalse();
        // Obtenir la date et l'heure actuelle moins un jour
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);

        for (Inscription inscription : inscriptionsUnconfirmed) {
            if (inscription.getCreatedAt().toLocalDateTime().isBefore(oneDayAgo)) {
                this.inscriptionRepository.delete(inscription);
                //TODO : envoyer un mail pour annoncer que la réservation est perdue
            }
        }
    }
}

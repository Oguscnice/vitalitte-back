package fr.vitalitte.vitalittebackend.inscription.usecase;

import fr.vitalitte.vitalittebackend.inscription.rest.CreateInscriptionBody;
import fr.vitalitte.vitalittebackend.inscription.rest.InscriptionDto;

import java.util.List;

public interface InscriptionService {

    String createInscription(CreateInscriptionBody createInscriptionBody);
    InscriptionDto findInscription(String slug);
    List<InscriptionDto> findAllInscriptions();
    List<InscriptionDto> findAllInscriptionsByWorkshop(String workshopSlug);
    String confirmInscriptionBySlug(String inscriptionSlug);
    void deleteInscriptionBySlug(String slug);
    Long countInscriptionsByWorkshopSlug(String workshopSlug);

}

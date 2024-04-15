package fr.vitalitte.vitalittebackend.inscription.usecase;

import fr.vitalitte.vitalittebackend.inscription.rest.CreateInscriptionBody;
import fr.vitalitte.vitalittebackend.inscription.rest.InscriptionDto;

import java.util.List;

public interface InscriptionService {
    void createInscription(CreateInscriptionBody createInscriptionBody);
    InscriptionDto findInscriptionBySlug(String slug);
    List<InscriptionDto> findAllInscriptions();
    List<InscriptionDto> findAllInscriptionsByWorkshop(String workshopSlug);
    void deleteInscriptionBySlug(String slug);
    Long countInscriptionsByWorkshopSlug(String workshopSlug);
}

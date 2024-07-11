package fr.vitalitte.vitalittebackend.inscription.persistence;

import fr.vitalitte.vitalittebackend.inscription.models.Inscription;
import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, String> {
    boolean existsBySlug(String slug);
    Optional<Inscription> findBySlug(String slug);
    List<Inscription> findAllByWorkshop(Workshop workshop);
    List<Inscription> findAllByIsConfirmedFalse();
}

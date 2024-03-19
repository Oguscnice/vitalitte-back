package fr.vitalitte.vitalittebackend.secondaryPicture.persistence;

import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SecondaryPictureRepository extends JpaRepository<SecondaryPicture, UUID> {
}

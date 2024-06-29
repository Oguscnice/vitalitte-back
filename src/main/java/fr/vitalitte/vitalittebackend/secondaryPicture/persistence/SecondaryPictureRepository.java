package fr.vitalitte.vitalittebackend.secondaryPicture.persistence;

import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@Repository
public interface SecondaryPictureRepository extends JpaRepository<SecondaryPicture, String> {
    Optional<SecondaryPicture> findByPictureAndNotebook(URL picture, Notebook notebook);
    Optional<SecondaryPicture> findByPicture(URL pictureUrl);
    List<SecondaryPicture> findAllByNotebook(Notebook notebook);
}

package fr.vitalitte.vitalittebackend.common.persistence;

import fr.vitalitte.vitalittebackend.common.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {
    boolean existsBySlug(String slug);
    Optional<FileEntity> findFileEntityBySlug(String slug);
    boolean existsByLinkedSlugAndIsMainPictureTrue(String linkedSlug);
    FileEntity findByLinkedSlugAndIsMainPictureTrue(String linkedSlug);
    List<FileEntity> findAllByLinkedSlugAndIsMainPictureFalse(String linkedSlug);
    List<FileEntity> findAllByLinkedSlug(String linkedSlug);
    void deleteByLinkedSlug(String linkedSlug);
}

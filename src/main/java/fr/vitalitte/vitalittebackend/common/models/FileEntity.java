package fr.vitalitte.vitalittebackend.common.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
public class FileEntity {

    @Id
    private UUID id;

    private String slug;

    @NotBlank
    private String fileName;

    @NotBlank
    private String linkedSlug;

    private boolean isMainPicture;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData;

    public FileEntity() {}

    public FileEntity(UUID id, String slug, String fileName, byte[] fileData, String linkedSlug, boolean isMainPicture) {
        this.id = id;
        this.slug = slug;
        this.fileName = fileName;
        this.fileData = fileData;
        this.linkedSlug = linkedSlug;
        this.isMainPicture = isMainPicture;
    }

    public UUID getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isMainPicture() {
        return isMainPicture;
    }

    public void setMainPicture(boolean mainPicture) {
        isMainPicture = mainPicture;
    }

    public String getLinkedSlug() {
        return linkedSlug;
    }

    public void setLinkedSlug(String linkedSlug) {
        this.linkedSlug = linkedSlug;
    }

    public static FileEntityBuilder builder() {
        return new FileEntityBuilder();
    }

    public static class FileEntityBuilder {

        private String slug;
        private String fileName;
        private byte[] fileData;
        private String linkedSlug;
        private boolean isMainPicture;

        public FileEntityBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public FileEntityBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public FileEntityBuilder fileData(byte[] fileData) {
            this.fileData = fileData;
            return this;
        }

        public FileEntityBuilder linkedSlug(String linkedSlug) {
            this.linkedSlug = linkedSlug;
            return this;
        }

        public FileEntityBuilder isMainPicture(boolean isMainPicture) {
            this.isMainPicture = isMainPicture;
            return this;
        }

        public FileEntity build() {
            final UUID id = UUID.randomUUID() ;
            return new FileEntity(
                    id,
                    slug,
                    fileName,
                    fileData,
                    linkedSlug,
                    isMainPicture
            );
        }
    }
}

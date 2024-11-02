package fr.vitalitte.vitalittebackend.common.rest;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

public class FileDto {

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData;
    private String slug;
    private String fileName;

    public FileDto(byte[] fileData, String slug, String fileName) {
        this.fileData = fileData;
        this.slug = slug;
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static FileDtoBuilder builder() {
        return new FileDtoBuilder();
    }

    public static class FileDtoBuilder {

        private byte[] fileData;
        private String slug;
        private String fileName;

        public FileDtoBuilder fileData(byte[] fileData) {
            this.fileData = fileData;
            return this;
        }

        public FileDtoBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public FileDtoBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public FileDto build() {
            return new FileDto(fileData, slug, fileName);
        }
    }
}

package fr.vitalitte.vitalittebackend.publication.rest;

import fr.vitalitte.vitalittebackend.common.rest.FileDto;

import java.io.File;
import java.sql.Timestamp;

public class PublicationDto {

    private String slug;
    private String title;
    private String description;
    private FileDto pictureDto;
    private boolean isSpotlighted;
    private Timestamp createdAt;

    public PublicationDto() {}

    public PublicationDto(String slug, String title, String description, FileDto pictureDto, boolean isSpotlighted, Timestamp createdAt) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.pictureDto = pictureDto;
        this.isSpotlighted = isSpotlighted;
        this.createdAt = createdAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FileDto getPictureDto() {
        return pictureDto;
    }

    public void setPictureDto(FileDto pictureDto) {
        this.pictureDto = pictureDto;
    }

    public boolean isSpotlighted() {
        return isSpotlighted;
    }

    public void setSpotlighted(boolean spotlighted) {
        isSpotlighted = spotlighted;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public static PublicationDtoBuilder builder() {
        return new PublicationDtoBuilder();
    }

    public static class PublicationDtoBuilder {

        private String slug;
        private String title;
        private String description;
        private FileDto pictureDto;
        private boolean isSpotlighted;
        private Timestamp createdAt;

        public PublicationDtoBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public PublicationDtoBuilder title(String title){
            this.title = title;
            return this;
        }

        public PublicationDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PublicationDtoBuilder pictureDto(FileDto pictureDto) {
            this.pictureDto = pictureDto;
            return this;
        }

        public PublicationDtoBuilder isSpotlighted(boolean isSpotlighted) {
            this.isSpotlighted = isSpotlighted;
            return this;
        }

        public PublicationDtoBuilder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PublicationDto build() {
            return new PublicationDto(this.slug, this.title, this.description, this.pictureDto, this.isSpotlighted, this.createdAt);
        }
    }
}

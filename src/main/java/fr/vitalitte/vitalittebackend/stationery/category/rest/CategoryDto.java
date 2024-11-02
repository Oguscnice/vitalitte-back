package fr.vitalitte.vitalittebackend.stationery.category.rest;

import fr.vitalitte.vitalittebackend.common.rest.FileDto;

public class CategoryDto {

    private String slug;
    private String name;
    private String description;
    private FileDto pictureDto;

    public CategoryDto(String slug, String name, String description, FileDto pictureDto) {
        this.slug = slug;
        this.name = name;
        this.description = description;
        this.pictureDto = pictureDto;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static CategoryDtoBuilder builder() {
        return new CategoryDtoBuilder();
    }

    public static class CategoryDtoBuilder {

        private String slug;
        private String name;
        private String description;
        private FileDto pictureDto;

        public CategoryDtoBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public CategoryDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CategoryDtoBuilder pictureDto(FileDto pictureDto) {
            this.pictureDto = pictureDto;
            return this;
        }

        public CategoryDto build() {
            return new CategoryDto(this.slug, this.name, this.description, this.pictureDto);
        }
    }
}

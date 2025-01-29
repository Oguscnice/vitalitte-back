package fr.vitalitte.vitalittebackend.stationery.materials.rest;

import fr.vitalitte.vitalittebackend.common.rest.FileDto;

import java.math.BigDecimal;

public class MaterialDto {

    private String name;
    private String slug;
    private BigDecimal price;
    private String description;
    private FileDto pictureDto;
    private String materialType;
    private boolean isAvailable;
    private boolean isAvailableForCustomization;

    public MaterialDto(String name, String slug, BigDecimal price, String description, FileDto pictureDto,String materialType, boolean isAvailable, boolean isAvailableForCustomization) {
        this.name = name;
        this.slug = slug;
        this.price = price;
        this.description = description;
        this.pictureDto = pictureDto;
        this.materialType = materialType;
        this.isAvailable = isAvailable;
        this.isAvailableForCustomization = isAvailableForCustomization;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
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

    public boolean isAvailableForCustomization() {
        return isAvailableForCustomization;
    }

    public void setAvailableForCustomization(boolean availableForCustomization) {
        isAvailableForCustomization = availableForCustomization;
    }

    public String getMaterialType() {
        return this.materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public static MaterialDtoBuilder builder(){
        return new MaterialDtoBuilder();
    }

    public static class MaterialDtoBuilder {

        private String name;
        private String slug;
        private BigDecimal price;
        private String description;
        private FileDto pictureDto;
        private String materialType;
        private boolean isAvailable;
        private boolean isAvailableForCustomization;

        public MaterialDtoBuilder name(String name){
            this.name = name;
            return this;
        }

        public MaterialDtoBuilder slug(String slug){
            this.slug = slug;
            return this;
        }

        public MaterialDtoBuilder price(BigDecimal price){
            this.price = price;
            return this;
        }

        public MaterialDtoBuilder description(String description){
            this.description = description;
            return this;
        }

        public MaterialDtoBuilder pictureDto(FileDto pictureDto){
            this.pictureDto = pictureDto;
            return this;
        }

        public MaterialDtoBuilder materialType(String materialType){
            this.materialType = materialType;
            return this;
        }

        public MaterialDtoBuilder isAvailable(boolean isAvailable){
            this.isAvailable = isAvailable;
            return this;
        }
        public MaterialDtoBuilder isAvailableForCustomization(boolean isAvailableForCustomization){
            this.isAvailableForCustomization = isAvailableForCustomization;
            return this;
        }
        public MaterialDto build(){
            return new MaterialDto(
                    this.name,
                    this.slug,
                    this.price,
                    this.description,
                    this.pictureDto,
                    this.materialType,
                    this.isAvailable,
                    this.isAvailableForCustomization
            );
        }
    }
}

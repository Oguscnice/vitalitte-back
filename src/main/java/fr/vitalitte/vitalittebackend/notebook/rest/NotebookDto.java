package fr.vitalitte.vitalittebackend.notebook.rest;

import fr.vitalitte.vitalittebackend.category.models.Category;
import fr.vitalitte.vitalittebackend.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.materials.models.Material;
import fr.vitalitte.vitalittebackend.materials.rest.MaterialDto;

import java.math.BigDecimal;
import java.util.List;

public class NotebookDto {
    private String name;
    private String slug;
    private String mainPicture;
    private String introduction;
    private BigDecimal price;
    private List<String> secondaryPictures;
    private String description;
    private List<Material> materials;
    private CategoryDto categoryDto;
    private boolean isAvailable;

    public NotebookDto(String name, String slug, String mainPicture, String introduction, BigDecimal price, List<String> secondaryPictures, String description, List<Material> materials, CategoryDto categoryDto, boolean isAvailable) {
        this.name = name;
        this.slug = slug;
        this.mainPicture = mainPicture;
        this.introduction = introduction;
        this.price = price;
        this.secondaryPictures = secondaryPictures;
        this.description = description;
        this.materials = materials;
        this.categoryDto = categoryDto;
        this.isAvailable = isAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getSecondaryPictures() {
        return secondaryPictures;
    }

    public void setSecondaryPictures(List<String> secondaryPictures) {
        this.secondaryPictures = secondaryPictures;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public static NotebookDtoBuilder builder(){return new NotebookDtoBuilder();}
    public static class NotebookDtoBuilder{
        private String name;
        private String slug;
        private String mainPicture;
        private String introduction;
        private BigDecimal price;
        private List<String> secondaryPictures;
        private String description;
        private List<Material> materials;
        private CategoryDto categoryDto;
        private boolean isAvailable;
        public NotebookDtoBuilder name(String name){
            this.name = name;
            return this;
        }
        public NotebookDtoBuilder slug(String slug){
            this.slug = slug;
            return this;
        }
        public NotebookDtoBuilder mainPicture(String mainPicture){
            this.mainPicture = mainPicture;
            return this;
        }
        public NotebookDtoBuilder introduction(String introduction){
            this.introduction = introduction;
            return this;
        }
        public NotebookDtoBuilder price(BigDecimal price){
            this.price = price;
            return this;
        }
        public NotebookDtoBuilder secondaryPictures(List<String> secondaryPictures){
            this.secondaryPictures = secondaryPictures;
            return this;
        }
        public NotebookDtoBuilder description(String description){
            this.description = description;
            return this;
        }
        public NotebookDtoBuilder materials(List<Material> materials){
            this.materials = materials;
            return this;
        }
        public NotebookDtoBuilder categoryDto(CategoryDto categoryDto){
            this.categoryDto = categoryDto;
            return this;
        }
        public NotebookDtoBuilder isAvailable(boolean isAvailable){
            this.isAvailable = isAvailable;
            return this;
        }
        public NotebookDto build(){
            return new NotebookDto(this.name, this.slug, this.mainPicture, this.introduction, this.price, this.secondaryPictures, this.description, this.materials, this.categoryDto, this.isAvailable);
        }
    }
}

package fr.vitalitte.vitalittebackend.stationery.product.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.vitalitte.vitalittebackend.common.rest.FileDto;
import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.stationery.collection.rest.CollectionDto;
import fr.vitalitte.vitalittebackend.stationery.materials.rest.MaterialDto;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    private String name;
    private String slug;
    private FileDto pictureDto;
    private String introduction;
    private BigDecimal price;
    private List<FileDto> secondaryPicturesDto;
    private String description;
    private List<MaterialDto> materialsDto;
    private CategoryDto categoryDto;
    private CollectionDto collectionDto;
    private String productType;

    @Column(name = "isAvailable")
    private boolean isAvailable;

    public ProductDto(String name, String slug, FileDto pictureDto, String introduction, BigDecimal price, List<FileDto> secondaryPicturesDto, String description, List<MaterialDto> materialsDto, CategoryDto categoryDto, CollectionDto collectionDto, boolean isAvailable, String productType) {
        this.name = name;
        this.slug = slug;
        this.pictureDto = pictureDto;
        this.introduction = introduction;
        this.price = price;
        this.secondaryPicturesDto = secondaryPicturesDto;
        this.description = description;
        this.materialsDto = materialsDto;
        this.categoryDto = categoryDto;
        this.collectionDto = collectionDto;
        this.isAvailable = isAvailable;
        this.productType = productType;
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

    public FileDto getPictureDto() {
        return pictureDto;
    }

    public void setPictureDto(FileDto pictureDto) {
        this.pictureDto = pictureDto;
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

    public List<FileDto> getSecondaryPicturesDto() {
        return secondaryPicturesDto;
    }

    public void setSecondaryPicturesDto(List<FileDto> secondaryPicturesDto) {
        this.secondaryPicturesDto = secondaryPicturesDto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MaterialDto> getMaterialsDto() {
        return materialsDto;
    }

    public void setMaterialsDto(List<MaterialDto> materialsDto) {
        this.materialsDto = materialsDto;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public CollectionDto getCollectionDto() {
        return collectionDto;
    }

    public void setCollectionDto(CollectionDto collectionDto) {
        this.collectionDto = collectionDto;
    }

    @JsonProperty("isAvailable")
    public boolean isAvailable() {
        return isAvailable;
    }

    @JsonProperty("isAvailable")
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public static ProductDtoBuilder builder() {
        return new ProductDtoBuilder();
    }

    public static class ProductDtoBuilder {

        private String name;
        private String slug;
        private FileDto pictureDto;
        private String introduction;
        private BigDecimal price;
        private List<FileDto> secondaryPicturesDto;
        private String description;
        private List<MaterialDto> materialsDto;
        private CategoryDto categoryDto;
        private CollectionDto collectionDto;
        private boolean isAvailable;
        private String productType;

        public ProductDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductDtoBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public ProductDtoBuilder pictureDto(FileDto pictureDto) {
            this.pictureDto = pictureDto;
            return this;
        }

        public ProductDtoBuilder introduction(String introduction) {
            this.introduction = introduction;
            return this;
        }

        public ProductDtoBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductDtoBuilder secondaryPicturesDto(List<FileDto> secondaryPicturesDto) {
            this.secondaryPicturesDto = secondaryPicturesDto;
            return this;
        }

        public ProductDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductDtoBuilder materialsDto(List<MaterialDto> materialsDto) {
            this.materialsDto = materialsDto;
            return this;
        }

        public ProductDtoBuilder categoryDto(CategoryDto categoryDto) {
            this.categoryDto = categoryDto;
            return this;
        }

        public ProductDtoBuilder collectionDto(CollectionDto collectionDto) {
            this.collectionDto = collectionDto;
            return this;
        }

        public ProductDtoBuilder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public ProductDtoBuilder productType(String productType) {
            this.productType = productType;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(
                    this.name,
                    this.slug,
                    this.pictureDto,
                    this.introduction,
                    this.price,
                    this.secondaryPicturesDto,
                    this.description,
                    this.materialsDto,
                    this.categoryDto,
                    this.collectionDto,
                    this.isAvailable,
                    this.productType
            );
        }
    }
}

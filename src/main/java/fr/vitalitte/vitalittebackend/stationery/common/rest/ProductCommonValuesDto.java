package fr.vitalitte.vitalittebackend.stationery.common.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.net.URL;

public class ProductCommonValuesDto {

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank
    private String slug;

    @NotNull
    private String picture;

    private String pictureThumbnail;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal price;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "isAvailable")
    private boolean isAvailable;

    public ProductCommonValuesDto() {}

    public ProductCommonValuesDto(String name, String slug, String picture, String pictureThumbnail, BigDecimal price, String description, boolean isAvailable) {
        this.name = name;
        this.slug = slug;
        this.picture = picture;
        this.pictureThumbnail = pictureThumbnail;
        this.price = price;
        this.description = description;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureThumbnail() {
        return pictureThumbnail;
    }

    public void setPictureThumbnail(String pictureThumbnail) {
        this.pictureThumbnail = pictureThumbnail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("isAvailable")
    public boolean isAvailable() {
        return isAvailable;
    }

    @JsonProperty("isAvailable")
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public abstract static class ProductCommonValuesDtoBuilder<T extends ProductCommonValuesDtoBuilder<T>> {

        protected String name;
        protected String slug;
        protected String picture;
        protected String pictureThumbnail;
        protected BigDecimal price;
        protected String description;
        protected boolean isAvailable;

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T slug(String slug) {
            this.slug = slug;
            return self();
        }

        public T picture(String picture) {
            this.picture = picture;
            return self();
        }

        public T pictureThumbnail(String pictureThumbnail) {
            this.pictureThumbnail = pictureThumbnail;
            return self();
        }

        public T price(BigDecimal price) {
            this.price = price;
            return self();
        }

        public T description(String description) {
            this.description = description;
            return self();
        }

        public T isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return self();
        }

        protected abstract T self();

        public abstract ProductCommonValuesDto build();
    }
}

package fr.vitalitte.vitalittebackend.stationery.common.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.net.URL;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductCommonValues {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank
    private String slug;

    @NotNull
    private URL picture;

    private URL pictureThumbnail;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal price;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean isAvailable;

    public ProductCommonValues() {}

    public ProductCommonValues(UUID id, String name, String slug, URL picture, URL pictureThumbnail, BigDecimal price, String description, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.picture = picture;
        this.pictureThumbnail = pictureThumbnail;
        this.price = price;
        this.description = description;
        this.isAvailable = isAvailable;
    }

    public UUID getId() {
        return id;
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

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }

    public URL getPictureThumbnail() {
        return pictureThumbnail;
    }

    public void setPictureThumbnail(URL pictureThumbnail) {
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public abstract static class ProductCommonValuesBuilder<T extends ProductCommonValuesBuilder<T>> {

        protected final UUID id = UUID.randomUUID();
        protected String name;
        protected String slug;
        protected URL picture;
        protected URL pictureThumbnail;
        protected BigDecimal price;
        protected String description;

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T slug(String slug) {
            this.slug = slug;
            return self();
        }

        public T picture(URL picture) {
            this.picture = picture;
            return self();
        }

        public T pictureThumbnail(URL pictureThumbnail) {
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

        protected abstract T self();

        public abstract ProductCommonValues build();
    }
}

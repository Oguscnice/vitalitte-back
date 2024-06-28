package fr.vitalitte.vitalittebackend.workshop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
public class Workshop {

    @Id
    private UUID id;

    @NotNull
    private String title;

    @NotNull
    private String slug;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private String address;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=3, fraction=2)
    private BigDecimal price;

    @NotNull
    private URL picture;

    @NotNull
    private URL pictureThumbnail;

    private Long registrations;

    private boolean isAvailable;

    public Workshop() {}

    public Workshop(UUID id, String title, String slug, String description, LocalDateTime date, String address, BigDecimal price, URL picture, URL pictureThumbnail, Long registrations, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.date = date;
        this.address = address;
        this.price = price;
        this.picture = picture;
        this.pictureThumbnail = pictureThumbnail;
        this.registrations = registrations;
        this.isAvailable = isAvailable;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Long getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Long registrations) {
        this.registrations = registrations;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public static WorkshopBuilder builder() {
        return new WorkshopBuilder();
    }

    public static class WorkshopBuilder {

        private final UUID id = UUID.randomUUID();
        private String title;
        private String slug;
        private String description;
        private LocalDateTime date;
        private String address;
        private BigDecimal price;
        private URL picture;
        private URL pictureThumbnail;
        private Long registrations;

        public WorkshopBuilder title(String title) {
            this.title = title;
            return this;
        }

        public WorkshopBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public WorkshopBuilder description(String description) {
            this.description = description;
            return this;
        }

        public WorkshopBuilder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public WorkshopBuilder address(String address) {
            this.address = address;
            return this;
        }

        public WorkshopBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public WorkshopBuilder picture(URL picture) {
            this.picture = picture;
            return this;
        }

        public WorkshopBuilder pictureThumbnail(URL pictureThumbnail) {
            this.pictureThumbnail = pictureThumbnail;
            return this;
        }

        public WorkshopBuilder registrations(Long registrations) {
            this.registrations = registrations;
            return this;
        }

        public Workshop build() {
            return new Workshop(this.id, this.title, this.slug, this.description, this.date, this.address, this.price, this.picture, this.pictureThumbnail, this.registrations, true);
        }
    }
}

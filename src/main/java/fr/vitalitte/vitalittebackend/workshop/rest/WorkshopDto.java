package fr.vitalitte.vitalittebackend.workshop.rest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class WorkshopDto {

    private String title;
    private String slug;
    private String description;
    private LocalDateTime date;
    private String address;
    private BigDecimal price;
    private String picture;
    private String pictureThumbnail;
    private Long registrations;
    private boolean isAvailable;

    public WorkshopDto() {}

    public WorkshopDto(String title, String slug, String description, LocalDateTime date, String address, BigDecimal price, String picture, String pictureThumbnail, Long registrations, boolean isAvailable) {
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

    public static WorkshopDtoBuilder builder() {
        return new WorkshopDtoBuilder();
    }

    public static class WorkshopDtoBuilder {

        private String title;
        private String slug;
        private String description;
        private LocalDateTime date;
        private String address;
        private BigDecimal price;
        private String picture;
        private String pictureThumbnail;
        private Long registrations;
        private boolean isAvailable;

        public WorkshopDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public WorkshopDtoBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public WorkshopDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public WorkshopDtoBuilder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public WorkshopDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public WorkshopDtoBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public WorkshopDtoBuilder picture(String picture) {
            this.picture = picture;
            return this;
        }

        public WorkshopDtoBuilder pictureThumbnail(String pictureThumbnail) {
            this.pictureThumbnail = pictureThumbnail;
            return this;
        }

        public WorkshopDtoBuilder registrations(Long registrations) {
            this.registrations = registrations;
            return this;
        }

        public WorkshopDtoBuilder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public WorkshopDto build() {
            return new WorkshopDto(this.title, this.slug, this.description, this.date, this.address, this.price, this.picture, this.pictureThumbnail, this.registrations, this.isAvailable);
        }
    }
}

package fr.vitalitte.vitalittebackend.workshop.rest;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateWorkshopBody {

    @NotNull
    private final String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private final String description;

    @NotNull
    private final LocalDateTime date;

    @NotNull
    private final String address;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=3, fraction=2)
    private final BigDecimal price;

    @NotNull
    private final String picture;

    @NotNull
    private final String pictureThumbnail;

    private final Long registrations;

    public CreateWorkshopBody(String title, String description, LocalDateTime date, String address, BigDecimal price, String picture, String pictureThumbnail, Long registrations) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.address = address;
        this.price = price;
        this.picture = picture;
        this.pictureThumbnail = pictureThumbnail;
        this.registrations = registrations;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public String getPictureThumbnail() {
        return pictureThumbnail;
    }

    public Long getRegistrations() {
        return registrations;
    }
}

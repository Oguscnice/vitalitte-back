package fr.vitalitte.vitalittebackend.workshop.rest;

import fr.vitalitte.vitalittebackend.common.rest.FileDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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

    private final FileDto pictureDto;

    @Min(1)
    private final Long registrations;

    public CreateWorkshopBody(String title, String description, LocalDateTime date, String address, BigDecimal price, FileDto pictureDto, Long registrations) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.address = address;
        this.price = price;
        this.pictureDto = pictureDto;
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

    public FileDto getPictureDto() {
        return pictureDto;
    }

    public Long getRegistrations() {
        return registrations;
    }
}

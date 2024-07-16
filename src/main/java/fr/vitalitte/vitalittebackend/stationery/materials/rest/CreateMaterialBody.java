package fr.vitalitte.vitalittebackend.stationery.materials.rest;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateMaterialBody {

    @NotBlank
    @Size(min = 1, max = 255)
    private final String name;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private final BigDecimal price;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private final String description;

    @NotNull
    private final String picture;

    @NotNull
    private final String pictureThumbnail;

    @NotNull
    private final String materialType;

    public CreateMaterialBody(String name, BigDecimal price, String description, String picture, String pictureThumbnail, String materialType) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.picture = picture;
        this.pictureThumbnail = pictureThumbnail;
        this.materialType = materialType;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public String getPictureThumbnail() {
        return pictureThumbnail;
    }

    public String getMaterialType() {
        return materialType;
    }
}

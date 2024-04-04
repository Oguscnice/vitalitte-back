package fr.vitalitte.vitalittebackend.activity.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Activity {

    @Id
    private UUID id;
    @NotBlank
    private String slug;
    @NotBlank
    @Size(max = 255)
    private String name;
    @NotNull
    private LocalDateTime date;
    @NotBlank
    @Size(max = 1000)
    private String description;
    private Long numberOfPlaces;
    private boolean isAvailable;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal price;
    private URL mainPicture;
}

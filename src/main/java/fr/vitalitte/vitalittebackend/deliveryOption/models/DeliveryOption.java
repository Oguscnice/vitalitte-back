package fr.vitalitte.vitalittebackend.deliveryOption.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class DeliveryOption {

    @Id
    private UUID id;

    @NotBlank
    private String slug;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @Min(0)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @NotBlank
    @Size(max = 255)
    private String estimatedDeliveryTime; // Temps estimé de livraison

    private boolean isExpress; // Indicateur pour savoir si c'est une livraison express

    @Size(max = 255)
    private String carrier; // Nom du transporteur

    private boolean isAvailable; // Disponibilité de l'option de livraison

    @Size(max = 500)
    private String description; // Description de l'option de livraison

    public DeliveryOption() {}

    public DeliveryOption(UUID id, String slug, String name, BigDecimal price, String estimatedDeliveryTime, boolean isExpress, String carrier, boolean isAvailable, String description) {
        this.id = id;
        this.slug = slug;
        this.name = name;
        this.price = price;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.isExpress = isExpress;
        this.carrier = carrier;
        this.isAvailable = isAvailable;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public boolean isExpress() {
        return isExpress;
    }

    public void setExpress(boolean express) {
        isExpress = express;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static DeliveryOptionBuilder builder() {
        return new DeliveryOptionBuilder();
    }

    public static class DeliveryOptionBuilder {

        private final UUID id = UUID.randomUUID() ;
        private String slug;
        private String name;
        private BigDecimal price;
        private String estimatedDeliveryTime;
        private boolean isExpress;
        private String carrier;
        private boolean isAvailable = true;
        private String description;

        public DeliveryOptionBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public DeliveryOptionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DeliveryOptionBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public DeliveryOptionBuilder estimatedDeliveryTime(String estimatedDeliveryTime) {
            this.estimatedDeliveryTime = estimatedDeliveryTime;
            return this;
        }

        public DeliveryOptionBuilder isExpress(boolean isExpress) {
            this.isExpress = isExpress;
            return this;
        }

        public DeliveryOptionBuilder carrier(String carrier) {
            this.carrier = carrier;
            return this;
        }

        public DeliveryOptionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public DeliveryOption build() {
            return new DeliveryOption(this.id, this.slug, this.name, this.price, this.estimatedDeliveryTime, this.isExpress, this.carrier, this.isAvailable, this.description);
        }
    }
}

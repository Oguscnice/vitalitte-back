package fr.vitalitte.vitalittebackend.deliveryOption.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class DeliveryOptionDto {

    private String slug;
    private String name;
    private BigDecimal price;
    private String estimatedDeliveryTime; // Temps estimé de livraison
    @JsonProperty("isExpress")
    private boolean isExpress; // Indicateur pour savoir si c'est une livraison express
    private String carrier; // Nom du transporteur
    @JsonProperty("isAvailable")
    private boolean isAvailable; // Disponibilité de l'option de livraison
    private String description; // Description de l'option de livraison

    public DeliveryOptionDto() {}

    public DeliveryOptionDto(String slug, String name, BigDecimal price, String estimatedDeliveryTime, boolean isExpress, String carrier, boolean isAvailable, String description) {
        this.slug = slug;
        this.name = name;
        this.price = price;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.isExpress = isExpress;
        this.carrier = carrier;
        this.isAvailable = isAvailable;
        this.description = description;
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

    public static DeliveryOptionDtoBuilder builer() {
        return new DeliveryOptionDtoBuilder();
    }

    public static class DeliveryOptionDtoBuilder {

        private String slug;
        private String name;
        private BigDecimal price;
        private String estimatedDeliveryTime;
        private boolean isExpress;
        private String carrier;
        private boolean isAvailable;
        private String description;

        public DeliveryOptionDtoBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public DeliveryOptionDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DeliveryOptionDtoBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public DeliveryOptionDtoBuilder estimatedDeliveryTime(String estimatedDeliveryTime) {
            this.estimatedDeliveryTime = estimatedDeliveryTime;
            return this;
        }

        public DeliveryOptionDtoBuilder isExpress(boolean isExpress) {
            this.isExpress = isExpress;
            return this;
        }

        public DeliveryOptionDtoBuilder carrier(String carrier) {
            this.carrier = carrier;
            return this;
        }

        public DeliveryOptionDtoBuilder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public DeliveryOptionDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public DeliveryOptionDto build() {
            return new DeliveryOptionDto(this.slug, this.name, this.price, this.estimatedDeliveryTime, this.isExpress, this.carrier, this.isAvailable, this.description);
        }
    }
}

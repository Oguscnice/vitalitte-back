package fr.vitalitte.vitalittebackend.deliveryOption.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CreateDeliveryOptionBody {

    private String name;
    private BigDecimal price;
    private String estimatedDeliveryTime; // Temps estimé de livraison
    private boolean isExpress; // Indicateur pour savoir si c'est une livraison express
    private String carrier; // Nom du transporteur
    private String description; // Description de l'option de livraison

    public CreateDeliveryOptionBody() {}

    public CreateDeliveryOptionBody(String name, BigDecimal price, String estimatedDeliveryTime, boolean isExpress, String carrier, String description) {
        this.name = name;
        this.price = price;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.isExpress = isExpress;
        this.carrier = carrier;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    @JsonProperty("isExpress")
    public boolean getIsExpress() {
        return isExpress;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getDescription() {
        return description;
    }
}

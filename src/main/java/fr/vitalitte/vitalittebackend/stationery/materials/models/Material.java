package fr.vitalitte.vitalittebackend.stationery.materials.models;

import fr.vitalitte.vitalittebackend.stationery.materialTypes.models.EMaterialType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.net.URL;
import java.util.UUID;

@Entity
public class Material {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank
    private String slug;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal price;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private EMaterialType materialType;

    private boolean isAvailable;

    private boolean isAvailableForCustomization;

    public Material() {}

    public Material(UUID id, String name, String slug, BigDecimal price, String description, EMaterialType materialType, boolean isAvailable, boolean isAvailableForCustomization) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.price = price;
        this.description = description;
        this.materialType = materialType;
        this.isAvailable = isAvailable;
        this.isAvailableForCustomization = isAvailableForCustomization;
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

    public EMaterialType getMaterialType() {
        return materialType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isAvailableForCustomization() {
        return isAvailableForCustomization;
    }

    public void setAvailableForCustomization(boolean availableForCustomization) {
        isAvailableForCustomization = availableForCustomization;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setMaterialType(EMaterialType materialType) {
        this.materialType = materialType;
    }

    public static MaterialBuilder builder() {
        return new MaterialBuilder();
    }

    public static class MaterialBuilder {

        private final UUID uuid = UUID.randomUUID();
        private String name;
        private String slug;
        private BigDecimal price;
        private String description;
        private EMaterialType materialType;

        public MaterialBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MaterialBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public MaterialBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public MaterialBuilder description(String description) {
            this.description = description;
            return this;
        }

        public MaterialBuilder materialType(EMaterialType materialType) {
            this.materialType = materialType;
            return this;
        }

        public Material build() {
            return new Material(
                    this.uuid,
                    this.name,
                    this.slug,
                    this.price,
                    this.description,
                    this.materialType,
                    true,
                    true);
        }
    }
}

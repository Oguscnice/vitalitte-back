package fr.vitalitte.vitalittebackend.stationery.product.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import fr.vitalitte.vitalittebackend.stationery.materials.usecase.MaterialSerializer;
import fr.vitalitte.vitalittebackend.stationery.productType.models.EProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Product {

    //TODO : ajouter nombre de page, ajouter les formats (A1-8)

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

    private boolean isAvailable;

    private EProductType eProductType;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection")
    private Collection collection;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonSerialize(using = MaterialSerializer.class)
    @JoinTable(name = "product_materials",
               joinColumns = @JoinColumn(name = "product_slug"),
               inverseJoinColumns = @JoinColumn(name = "material_slug"))
    private List<Material> materials = new ArrayList<>();

    public Product() {}

    public Product(Category category, Collection collection, String description, EProductType eProductType, UUID id, String introduction, boolean isAvailable, List<Material> materials, String name, BigDecimal price, String slug) {
        this.category = category;
        this.collection = collection;
        this.description = description;
        this.eProductType = eProductType;
        this.id = id;
        this.introduction = introduction;
        this.isAvailable = isAvailable;
        this.materials = materials;
        this.name = name;
        this.price = price;
        this.slug = slug;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EProductType getEProductType() {
        return eProductType;
    }

    public void setEProductType(EProductType eProductType) {
        this.eProductType = eProductType;
    }

    public UUID getId() {
        return id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {

        private String name;
        private String slug;
        private BigDecimal price;
        private String description;
        private String introduction;
        private Category category;
        private Collection collection;
        private List<Material> materials;
        private EProductType eProductType;

        public ProductBuilder name(String name) {
            this.name = name;
            return  this ;
        }

        public ProductBuilder slug(String slug) {
            this.slug = slug;
            return  this ;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return  this ;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return  this ;
        }

        public ProductBuilder introduction(String introduction) {
            this.introduction = introduction;
            return this;
        }

        public ProductBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ProductBuilder collection(Collection collection) {
            this.collection = collection;
            return this;
        }

        public ProductBuilder materials(List<Material> materials) {
            this.materials = materials;
            return this;
        }

        public ProductBuilder eProductType(EProductType eProductType) {
            this.eProductType = eProductType;
            return this;
        }

        public Product build() {

            final boolean isAvailable = true;
            final UUID id = UUID.randomUUID();

            return new Product(
                    this.category,
                    this.collection,
                    this.description,
                    this.eProductType,
                    id,
                    this.introduction,
                    isAvailable,
                    this.materials,
                    this.name,
                    this.price,
                    this.slug
            );
        }
    }
}

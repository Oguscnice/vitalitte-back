package fr.vitalitte.vitalittebackend.notebook.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.vitalitte.vitalittebackend.category.models.Category;
import fr.vitalitte.vitalittebackend.materials.models.Material;
import fr.vitalitte.vitalittebackend.materials.usecase.MaterialSerializer;
import fr.vitalitte.vitalittebackend.secondaryPicture.models.SecondaryPicture;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Notebook {
    @Id
    private UUID id;
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
    @NotBlank
    private String slug;
    @NotNull
    private URL mainPicture;
    @NotBlank
    @Size(max = 500)
    private String introduction;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal price;
    @NotBlank
    @Size(max = 1000)
    private String description;
    private boolean isAvailable;
    @ManyToOne
    @JoinColumn
    private Category category;
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<SecondaryPicture> secondaryPictures;
    @ManyToMany(fetch = FetchType.LAZY)
//    @JsonSerialize(using = MaterialSerializer.class)
    @JoinTable(name = "notebook_materials",
               joinColumns = @JoinColumn(name = "notebook_id"),
               inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<Material> materials = new ArrayList<>();

    public Notebook(){}

    public Notebook(UUID id, String name, String slug, URL mainPicture, String introduction, BigDecimal price, String description, Category category, List<SecondaryPicture> secondaryPictures, List<Material> materials, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.mainPicture = mainPicture;
        this.introduction = introduction;
        this.price = price;
        this.description = description;
        this.category = category;
        this.secondaryPictures = secondaryPictures;
        this.materials = materials;
        this.isAvailable = isAvailable;
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

    public URL getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(URL mainPicture) {
        this.mainPicture = mainPicture;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public List<SecondaryPicture> getSecondaryPictures() {
        return secondaryPictures;
    }

    public void setSecondaryPictures(List<SecondaryPicture> secondaryPictures) {
        this.secondaryPictures = secondaryPictures;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static NotebookBuilder builder(){return new NotebookBuilder();}
    public static class NotebookBuilder{
        private final UUID id = UUID.randomUUID();
        private String name;
        private String slug;
        private URL mainPicture;
        private String introduction;
        private BigDecimal price;
        private String description;
        private Category category;
        private List<SecondaryPicture> secondaryPictures;
        private List<Material> materials;
        public NotebookBuilder name(String name){
            this.name = name;
            return this;
        }
        public NotebookBuilder slug(String slug){
            this.slug = slug;
            return this;
        }
        public NotebookBuilder mainPicture(URL mainPicture){
            this.mainPicture = mainPicture;
            return this;
        }
        public NotebookBuilder introduction(String introduction){
            this.introduction = introduction;
            return this;
        }
        public NotebookBuilder price(BigDecimal price){
            this.price = price;
            return this;
        }
        public NotebookBuilder description(String description){
            this.description = description;
            return this;
        }
        public NotebookBuilder category(Category category){
            this.category = category;
            return this;
        }
        public NotebookBuilder secondaryPictures(List<SecondaryPicture> secondaryPictures){
            this.secondaryPictures = secondaryPictures;
            return this;
        }
        public NotebookBuilder materials(List<Material> materials){
            this.materials = materials;
            return this;
        }
        public Notebook build(){
            return new Notebook(this.id, this.name, this.slug, this.mainPicture, this.introduction, this.price, this.description, this.category, this.secondaryPictures, this.materials, true);
        }
    }
}

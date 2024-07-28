package fr.vitalitte.vitalittebackend.stationery.notebook.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.vitalitte.vitalittebackend.stationery.category.models.Category;
import fr.vitalitte.vitalittebackend.stationery.collection.models.Collection;
import fr.vitalitte.vitalittebackend.stationery.common.models.ProductCommonValues;
import fr.vitalitte.vitalittebackend.stationery.materials.models.Material;
import fr.vitalitte.vitalittebackend.stationery.materials.usecase.MaterialSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
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
public class Notebook extends ProductCommonValues {

    //TODO : ajouter nombre de page, ajouter les formats (A1-8)

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
    @JoinTable(name = "notebook_materials",
               joinColumns = @JoinColumn(name = "notebook_slug"),
               inverseJoinColumns = @JoinColumn(name = "material_slug"))
    private List<Material> materials = new ArrayList<>();

    public Notebook(){}

    public Notebook(UUID id, String name, String slug, URL picture, URL pictureThumbnail, BigDecimal price, String description, boolean isAvailable, String introduction, Category category, Collection collection, List<Material> materials) {
        super(id, name, slug, picture, pictureThumbnail, price, description, isAvailable);
        this.introduction = introduction;
        this.category = category;
        this.collection = collection;
        this.materials = materials;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public static NotebookBuilder builder() {
        return new NotebookBuilder();
    }

    public static class NotebookBuilder extends ProductCommonValuesBuilder<NotebookBuilder> {

        private String introduction;
        private Category category;
        private Collection collection;
        private List<Material> materials;

        public NotebookBuilder introduction(String introduction) {
            this.introduction = introduction;
            return self();
        }

        public NotebookBuilder category(Category category) {
            this.category = category;
            return self();
        }

        public NotebookBuilder collection(Collection collection) {
            this.collection = collection;
            return self();
        }

        public NotebookBuilder materials(List<Material> materials) {
            this.materials = materials;
            return self();
        }

        @Override
        protected NotebookBuilder self() {
            return this;
        }

        @Override
        public Notebook build() {
            return new Notebook(id, name, slug, picture, pictureThumbnail, price, description, true, this.introduction, this.category, this.collection, this.materials);
        }
    }
}

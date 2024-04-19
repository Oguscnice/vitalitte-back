package fr.vitalitte.vitalittebackend.category.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
public class Category {
    @Id
    private UUID id;
    @NotBlank
    private String slug;
    @NotBlank
    @Size(max = 255)
    private String name;

    public Category(UUID id, String slug, String name) {
        this.id = id;
        this.slug = slug;
        this.name = name;
    }

    public Category() {}

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

    public static CategoryBuilder builder(){
        return new CategoryBuilder();
    }
    public static class CategoryBuilder {
        private final UUID id = UUID.randomUUID() ;
        private String slug;
        private String name;

        public CategoryBuilder slug(String slug){
            this.slug = slug;
            return this;
        }
        public CategoryBuilder name(String name){
            this.name = name;
            return this;
        }
        public Category build(){
            return new Category(this.id, this.slug, this.name);
        }
    }
}

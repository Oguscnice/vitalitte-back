package fr.vitalitte.vitalittebackend.stationery.collection.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
public class Collection {
    @Id
    private UUID id;
    @NotBlank
    private String slug;
    @NotBlank
    @Size(max = 255)
    private String name;

    public Collection(UUID id, String slug, String name) {
        this.id = id;
        this.slug = slug;
        this.name = name;
    }
    public Collection() {}

    public UUID getId() {return id;}
    public String getSlug() {return slug;}
    public void setSlug(String slug) {this.slug = slug;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public static CollectionBuilder builder(){
        return new CollectionBuilder();
    }

    public static class CollectionBuilder {
        private final UUID id = UUID.randomUUID() ;
        private String slug;
        private String name;

        public CollectionBuilder slug(String slug){
            this.slug = slug;
            return this;
        }
        public CollectionBuilder name(String name){
            this.name = name;
            return this;
        }
        public Collection build(){
            return new Collection(this.id, this.slug, this.name);
        }
    }
}

package fr.vitalitte.vitalittebackend.publication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.net.URL;
import java.sql.Timestamp;
import java.util.UUID;
@Entity
public class Publication {
    @Id
    private UUID id;
    @NotNull
    private String slug;
    @NotNull
    private String title;
    @NotNull
    @Size(max = 2000)
    private String description;
    @NotNull
    private URL picture;
    private boolean isSpotlighted;

    @CreationTimestamp
    private Timestamp createdAt;

    public Publication() {}

    public Publication(UUID id, String slug, String title, String description, URL picture, boolean isSpotlighted) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.isSpotlighted = isSpotlighted;
    }

    public UUID getId() {return id;}
    public String getSlug() {return slug;}
    public void setSlug(String slug) {this.slug = slug;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public URL getPicture() {return picture;}
    public void setPicture(URL picture) {this.picture = picture;}
    public boolean isSpotlighted() {return isSpotlighted;}
    public void setSpotlighted(boolean spotlighted) {isSpotlighted = spotlighted;}
    public static PublicationBuilder builder(){return new PublicationBuilder();}
    public Timestamp getCreatedAt() {return createdAt;}
    public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}

    public static class PublicationBuilder {
        private final UUID id = UUID.randomUUID();
        private String slug;
        private String title;
        private String description;
        private URL picture;
        public PublicationBuilder slug(String slug){
            this.slug = slug;
            return this;
        }
        public PublicationBuilder title(String title){
            this.title = title;
            return this;
        }
        public PublicationBuilder description(String description){
            this.description = description;
            return this;
        }
        public PublicationBuilder picture(URL picture){
            this.picture = picture;
            return this;
        }
        public Publication build(){
            return new Publication(this.id, this.slug, this.title, this.description, this.picture, false);
        }
    }
}

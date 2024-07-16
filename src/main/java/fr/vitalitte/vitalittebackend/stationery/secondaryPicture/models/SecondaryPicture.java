package fr.vitalitte.vitalittebackend.stationery.secondaryPicture.models;

import fr.vitalitte.vitalittebackend.stationery.notebook.models.Notebook;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.net.URL;
import java.util.UUID;

@Entity
public class SecondaryPicture {

    @Id
    private UUID id;

    @NotNull
    private URL picture;

    private URL pictureThumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notebook")
    private Notebook notebook;

    public SecondaryPicture(){}

    public SecondaryPicture(UUID id, URL picture, URL pictureThumbnail, Notebook notebook) {
        this.id = id;
        this.picture = picture;
        this.pictureThumbnail = pictureThumbnail;
        this.notebook = notebook;
    }

    public UUID getId() {
        return id;
    }

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }

    public URL getPictureThumbnail() {
        return pictureThumbnail;
    }

    public void setPictureThumbnail(URL pictureThumbnail) {
        this.pictureThumbnail = pictureThumbnail;
    }

    public Notebook getNotebook() {
        return notebook;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public static SecondaryPictureBuilder builder() {
        return new SecondaryPictureBuilder();
    }

    public static class SecondaryPictureBuilder {

        private final UUID id = UUID.randomUUID();
        private URL picture;
        private URL pictureThumbnail;
        private Notebook notebook;

        public SecondaryPictureBuilder picture(URL picture){
            this.picture = picture;
            return this;
        }

        public SecondaryPictureBuilder pictureThumbnail(URL pictureThumbnail){
            this.pictureThumbnail = pictureThumbnail;
            return this;
        }

        public SecondaryPictureBuilder notebook(Notebook notebook){
            this.notebook = notebook;
            return this;
        }

        public SecondaryPicture build(){
            return new SecondaryPicture(this.id, this.picture, this.pictureThumbnail, this.notebook);
        }
    }
}

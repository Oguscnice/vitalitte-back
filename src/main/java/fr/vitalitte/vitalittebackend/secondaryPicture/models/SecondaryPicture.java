package fr.vitalitte.vitalittebackend.secondaryPicture.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.vitalitte.vitalittebackend.materials.usecase.MaterialSerializer;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
import fr.vitalitte.vitalittebackend.notebook.usecase.NotebookSerializer;
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
    private URL url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notebook")
    private Notebook notebook;

    public SecondaryPicture(){}

    public SecondaryPicture(UUID id, URL url, Notebook notebook) {
        this.id = id;
        this.url = url;
        this.notebook = notebook;
    }

    public UUID getId() {
        return id;
    }
    public URL getUrl() {
        return url;
    }
    public void setUrl(URL url) {
        this.url = url;
    }
    public Notebook getNotebook() {return notebook;}
    public void setNotebook(Notebook notebook) {this.notebook = notebook;}
    public static SecondaryPictureBuilder builder(){return new SecondaryPictureBuilder();}
    public static class SecondaryPictureBuilder{
        private final UUID id = UUID.randomUUID();
        private URL url;
        private Notebook notebook;
        public SecondaryPictureBuilder url(URL url){
            this.url = url;
            return this;
        }
        public SecondaryPictureBuilder notebook(Notebook notebook){
            this.notebook = notebook;
            return this;
        }
        public SecondaryPicture build(){
            return new SecondaryPicture(this.id, this.url, this.notebook);
        }
    }
}

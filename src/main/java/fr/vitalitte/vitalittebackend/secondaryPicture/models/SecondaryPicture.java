package fr.vitalitte.vitalittebackend.secondaryPicture.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.vitalitte.vitalittebackend.notebook.models.Notebook;
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
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notebook")
    private Notebook notebook;

    public SecondaryPicture(){}

    public SecondaryPicture(UUID id, URL url) {
        this.id = id;
        this.url = url;
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

    public Notebook getNotebook() {
        return notebook;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public static SecondaryPictureBuilder builder(){return new SecondaryPictureBuilder();}
    public static class SecondaryPictureBuilder{
        private final UUID id = UUID.randomUUID();
        private URL url;
        public SecondaryPictureBuilder url(URL url){
            this.url = url;
            return this;
        }
        public SecondaryPicture build(){
            return new SecondaryPicture(this.id, this.url);
        }
    }
}

package fr.vitalitte.vitalittebackend.publication.rest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.net.URL;

public class CreatePublicationBody {

    @NotNull
    private final String title;

    @NotNull
    @Size(max = 2000)
    private final String description;

    @NotNull
    private final String picture;

    private final String pictureThumbnail;

    public CreatePublicationBody(String title, String description, String picture, String pictureThumbnail) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.pictureThumbnail = pictureThumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public String getPictureThumbnail() {
        return pictureThumbnail;
    }
}

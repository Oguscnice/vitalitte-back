package fr.vitalitte.vitalittebackend.publication.rest;

import fr.vitalitte.vitalittebackend.common.rest.FileDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.net.URL;

public class CreatePublicationBody {

    @NotBlank
    private final String title;

    @NotBlank
    @Size(max = 2000)
    private final String description;

    private final FileDto pictureDto;

    public CreatePublicationBody(String title, String description, FileDto pictureDto) {
        this.title = title;
        this.description = description;
        this.pictureDto = pictureDto;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public FileDto getPictureDto() {
        return pictureDto;
    }
}

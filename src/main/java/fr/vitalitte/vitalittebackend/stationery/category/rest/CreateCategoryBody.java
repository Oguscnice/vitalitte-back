package fr.vitalitte.vitalittebackend.stationery.category.rest;

import fr.vitalitte.vitalittebackend.common.rest.FileDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.util.Base64;

public class CreateCategoryBody {

    @NotBlank
    private final String name;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private final String description;

    private final FileDto pictureDto;

    public CreateCategoryBody(String name, FileDto pictureDto, String description) {
        this.name = name;
        this.pictureDto = pictureDto;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public FileDto getPictureDto() {
        return pictureDto;
    }

    public String getName() {
        return name;
    }

    public byte[] getFileBytes() {
        return Base64.getDecoder().decode(getPictureDto().getFileData());
    }
}

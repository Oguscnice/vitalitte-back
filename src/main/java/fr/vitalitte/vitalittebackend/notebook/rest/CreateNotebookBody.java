package fr.vitalitte.vitalittebackend.notebook.rest;

import fr.vitalitte.vitalittebackend.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.collection.rest.CollectionDto;
import fr.vitalitte.vitalittebackend.materials.rest.MaterialDto;
import fr.vitalitte.vitalittebackend.secondaryPicture.rest.SecondaryPictureDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public class CreateNotebookBody {

    @NotBlank
    @Size(min = 1, max = 255)
    private final String name;

    @NotNull
    private final String picture;

    @NotNull
    private final String pictureThumbnail;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private final String introduction;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private final BigDecimal price;

    @NotNull
    private final List<SecondaryPictureDto> secondaryPicturesDto;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private final String description;

    @NotNull
    private final List<MaterialDto> materialsDto;

    private final CategoryDto categoryDto;

    private final CollectionDto collectionDto;

    public CreateNotebookBody(String name, String picture, String pictureThumbnail, String introduction, BigDecimal price, List<SecondaryPictureDto> secondaryPicturesDto, String description, List<MaterialDto> materialsDto, CategoryDto categoryDto, CollectionDto collectionDto) {
        this.name = name;
        this.picture = picture;
        this.pictureThumbnail = pictureThumbnail;
        this.introduction = introduction;
        this.price = price;
        this.secondaryPicturesDto = secondaryPicturesDto;
        this.description = description;
        this.materialsDto = materialsDto;
        this.categoryDto = categoryDto;
        this.collectionDto = collectionDto;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getPictureThumbnail() {
        return pictureThumbnail;
    }

    public String getIntroduction() {
        return introduction;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<SecondaryPictureDto> getSecondaryPicturesDto() {
        return secondaryPicturesDto;
    }

    public String getDescription() {
        return description;
    }

    public List<MaterialDto> getMaterialsDto() {
        return materialsDto;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public CollectionDto getCollectionDto() {
        return collectionDto;
    }
}

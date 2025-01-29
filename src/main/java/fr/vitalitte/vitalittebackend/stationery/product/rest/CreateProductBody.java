package fr.vitalitte.vitalittebackend.stationery.product.rest;

import fr.vitalitte.vitalittebackend.common.rest.FileDto;
import fr.vitalitte.vitalittebackend.stationery.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.stationery.collection.rest.CollectionDto;
import fr.vitalitte.vitalittebackend.stationery.materials.rest.MaterialDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public class CreateProductBody {

    @NotBlank
    @Size(min = 1, max = 255)
    private final String name;

    @NotNull
    private final FileDto pictureDto;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private final String introduction;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private final BigDecimal price;

    @NotNull
    private final List<FileDto> secondaryPicturesDto;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private final String description;

    @NotBlank
    private String productType;

    @NotNull
    private final List<MaterialDto> materialsDto;

    private final CategoryDto categoryDto;

    private final CollectionDto collectionDto;

    public CreateProductBody(String name, FileDto pictureDto, String introduction, BigDecimal price, List<FileDto> secondaryPicturesDto, String description, List<MaterialDto> materialsDto, CategoryDto categoryDto, CollectionDto collectionDto, String productType) {
        this.name = name;
        this.pictureDto = pictureDto;
        this.introduction = introduction;
        this.price = price;
        this.secondaryPicturesDto = secondaryPicturesDto;
        this.description = description;
        this.materialsDto = materialsDto;
        this.categoryDto = categoryDto;
        this.collectionDto = collectionDto;
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public FileDto getPictureDto() {
        return pictureDto;
    }

    public String getIntroduction() {
        return introduction;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<FileDto> getSecondaryPicturesDto() {
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

    public String getProductType() {
        return productType;
    }
}

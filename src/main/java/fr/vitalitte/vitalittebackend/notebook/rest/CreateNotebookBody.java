package fr.vitalitte.vitalittebackend.notebook.rest;

import fr.vitalitte.vitalittebackend.category.models.Category;
import fr.vitalitte.vitalittebackend.category.rest.CategoryDto;
import fr.vitalitte.vitalittebackend.materials.models.Material;
import fr.vitalitte.vitalittebackend.materials.rest.MaterialDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

public class CreateNotebookBody {
    @NotBlank
    @Size(min = 1, max = 255)
    private final String name;
    @NotNull
    private final String mainPicture;
    @NotBlank
    @Size(max = 500)
    private final String introduction;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private final BigDecimal price;
    @NotNull
    private final List<String> secondaryPictures;
    @NotBlank
    @Size(max = 1000)
    private final String description;
    @NotNull
    private final List<MaterialDto> materialsDto;
    private final CategoryDto categoryDto;

    public CreateNotebookBody(String name, String mainPicture, String introduction, BigDecimal price, List<String> secondaryPictures, String description, List<MaterialDto> materialsDto, CategoryDto categoryDto) {
        this.name = name;
        this.mainPicture = mainPicture;
        this.introduction = introduction;
        this.price = price;
        this.secondaryPictures = secondaryPictures;
        this.description = description;
        this.materialsDto = materialsDto;
        this.categoryDto = categoryDto;
    }

    public String getName() {
        return name;
    }

    public String getMainPicture() {
        return mainPicture;
    }

    public String getIntroduction() {
        return introduction;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<String> getSecondaryPictures() {
        return secondaryPictures;
    }

    public String getDescription() {
        return description;
    }

    public List<MaterialDto> getMaterialsDto() {
        return materialsDto;
    }

    public CategoryDto getCategoryDto() {return categoryDto;}
}

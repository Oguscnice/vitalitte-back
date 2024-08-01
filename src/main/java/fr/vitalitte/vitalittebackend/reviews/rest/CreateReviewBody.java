package fr.vitalitte.vitalittebackend.reviews.rest;

import fr.vitalitte.vitalittebackend.stationery.product.rest.ProductDto;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateReviewBody {

    @NotBlank
    @Size(max = 5000)
    private final String content;

    @NotBlank
    @Size(max = 255)
    private final String lastname;

    @NotBlank
    @Size(max = 255)
    private final String firstname;

    @NotBlank
    @Size(max = 255)
    private final String email;

    @NotBlank
    @Size(max = 255)
    private final String title;

    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "5.0", inclusive = true)
    @Digits(integer = 1, fraction = 1)
    private final BigDecimal rating;

    @NotNull
    private ProductDto productDto;

    public CreateReviewBody(String content, String lastname, String firstname, String email, String title, BigDecimal rating, ProductDto productDto) {
        this.content = content;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.title = title;
        this.rating = rating;
        this.productDto = productDto;
    }

    public String getContent() {
        return content;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public ProductDto getProductDto() {
        return productDto;
    }
}

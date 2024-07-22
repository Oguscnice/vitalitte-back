package fr.vitalitte.vitalittebackend.reviews.rest;

import fr.vitalitte.vitalittebackend.stationery.common.models.ProductCommonValues;
import fr.vitalitte.vitalittebackend.stationery.common.rest.ProductCommonValuesDto;
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

    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "5.0", inclusive = true)
    @Digits(integer = 1, fraction = 1)
    private final BigDecimal rating;

    @NotNull
    private ProductCommonValuesDto productCommonValuesDto;

    public CreateReviewBody(String content, String lastname, String firstname, String email, BigDecimal rating, ProductCommonValuesDto productCommonValuesDto) {
        this.content = content;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.rating = rating;
        this.productCommonValuesDto = productCommonValuesDto;
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

    public BigDecimal getRating() {
        return rating;
    }

    public ProductCommonValuesDto getProductCommonValuesDto() {
        return productCommonValuesDto;
    }
}

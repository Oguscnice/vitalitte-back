package fr.vitalitte.vitalittebackend.reviews.rest;

import fr.vitalitte.vitalittebackend.stationery.common.rest.ProductCommonValuesDto;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ReviewDto {

    @NotBlank
    @Size(max = 5000)
    private String content;

    private Timestamp createdAt;

    @NotBlank
    @Size(max = 255)
    private String lastname;

    @NotBlank
    @Size(max = 255)
    private String firstname;

    @NotBlank
    @Size(max = 255)
    private String email;

    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "5.0", inclusive = true)
    @Digits(integer = 1, fraction = 1)
    private BigDecimal rating;

    @NotNull
    private ProductCommonValuesDto productCommonValuesDto;

    private String status;

    public ReviewDto() {}

    public ReviewDto(String content, Timestamp createdAt, String lastname, String firstname, String email, BigDecimal rating, ProductCommonValuesDto productCommonValuesDto, String status) {
        this.content = content;
        this.createdAt = createdAt;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.rating = rating;
        this.productCommonValuesDto = productCommonValuesDto;
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public ProductCommonValuesDto getProductCommonValuesDto() {
        return productCommonValuesDto;
    }

    public void setProductCommonValuesDto(ProductCommonValuesDto productCommonValuesDto) {
        this.productCommonValuesDto = productCommonValuesDto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static ReviewDtoBuilder builder() {
        return new ReviewDtoBuilder();
    }

    public static class ReviewDtoBuilder {

        protected String content;
        protected Timestamp createdAt;
        protected String lastname;
        protected String firstname;
        protected String email;
        protected BigDecimal rating;
        protected ProductCommonValuesDto productCommonValuesDto;
        protected String status;

        public ReviewDtoBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ReviewDtoBuilder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReviewDtoBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public ReviewDtoBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public ReviewDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ReviewDtoBuilder rating(BigDecimal rating) {
            this.rating = rating;
            return this;
        }

        public ReviewDtoBuilder productCommonValuesDto(ProductCommonValuesDto productCommonValuesDto) {
            this.productCommonValuesDto = productCommonValuesDto;
            return this;
        }

        public ReviewDtoBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ReviewDto build() {
            return new ReviewDto(this.content, this.createdAt, this.lastname, this.firstname, this.email, this.rating, this.productCommonValuesDto, this.status);
        }
    }
}

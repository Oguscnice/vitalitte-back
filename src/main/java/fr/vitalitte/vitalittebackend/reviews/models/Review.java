package fr.vitalitte.vitalittebackend.reviews.models;

import fr.vitalitte.vitalittebackend.reviewStatus.models.EReviewStatus;
import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Review {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 1, max = 5000)
    private String content;

    @CreationTimestamp
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

    @NotBlank
    @Size(max = 255)
    private String title;

    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "5.0", inclusive = true)
    @Digits(integer = 1, fraction = 1)
    private BigDecimal rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private Product product;

    private EReviewStatus status;

    public Review() {}

    public Review(UUID id, String content, String lastname, String firstname, String email, String title, BigDecimal rating, Product product, EReviewStatus status) {
        this.id = id;
        this.content = content;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.title = title;
        this.rating = rating;
        this.product = product;
        this.status = status;
    }

    public UUID getId() {
        return id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public EReviewStatus getStatus() {
        return status;
    }

    public void setStatus(EReviewStatus status) {
        this.status = status;
    }

    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }

    public static class ReviewBuilder {

        private String content;
        private String lastname;
        private String firstname;
        private String email;
        private String title;
        private BigDecimal rating;
        private Product product;
        private EReviewStatus status;

        public ReviewBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ReviewBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public ReviewBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public ReviewBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ReviewBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ReviewBuilder rating(BigDecimal rating) {
            this.rating = rating;
            return this;
        }

        public ReviewBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public ReviewBuilder status(EReviewStatus status) {
            this.status = status;
            return this;
        }

        public Review build() {

            final UUID id = UUID.randomUUID();

            return new Review(
                    id,
                    this.content,
                    this.lastname,
                    this.firstname,
                    this.email,
                    this.title,
                    this.rating,
                    this.product,
                    this.status
            );
        }
    }
}

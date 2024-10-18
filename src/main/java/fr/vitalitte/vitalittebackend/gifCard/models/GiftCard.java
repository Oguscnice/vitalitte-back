package fr.vitalitte.vitalittebackend.gifCard.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
public class GiftCard {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String code;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal rising;

    @NotNull
    private LocalDateTime expiryDate;

    private boolean isPercentage;

    private boolean isSingleUse;

    public GiftCard() {}

    public GiftCard(UUID id, String code, BigDecimal rising, LocalDateTime expiryDate, boolean isPercentage, boolean isSingleUse) {
        this.id = id;
        this.code = code;
        this.rising = rising;
        this.expiryDate = expiryDate;
        this.isPercentage = isPercentage;
        this.isSingleUse = isSingleUse;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getRising() {
        return rising;
    }

    public void setRising(BigDecimal rising) {
        this.rising = rising;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isPercentage() {
        return isPercentage;
    }

    public void setPercentage(boolean percentage) {
        isPercentage = percentage;
    }

    public boolean isSingleUse() {
        return isSingleUse;
    }

    public void setSingleUse(boolean singleUse) {
        isSingleUse = singleUse;
    }

    public static GiftCardBuilder builder() {
        return new GiftCardBuilder();
    }

    public static class GiftCardBuilder {

        private String code;
        private BigDecimal rising;
        private LocalDateTime expiryDate;
        private boolean isPercentage;
        private boolean isSingleUse;

        public GiftCardBuilder code(String code) {
            this.code = code;
            return this;
        }

        public GiftCardBuilder rising(BigDecimal rising) {
            this.rising = rising;
            return this;
        }

        public GiftCardBuilder expiryDate(LocalDateTime expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public GiftCardBuilder isPercentage(boolean isPercentage) {
            this.isPercentage = isPercentage;
            return this;
        }

        public GiftCardBuilder isSingleUse(boolean isSingleUse) {
            this.isSingleUse = isSingleUse;
            return this;
        }

        public GiftCard build() {
            final UUID id = UUID.randomUUID() ;
            return new GiftCard(
                    id,
                    this.code,
                    this.rising,
                    this.expiryDate,
                    this.isPercentage,
                    this.isSingleUse
            );
        }
    }
}


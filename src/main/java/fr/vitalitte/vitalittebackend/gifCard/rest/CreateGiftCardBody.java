package fr.vitalitte.vitalittebackend.gifCard.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateGiftCardBody {

    private String code;
    private BigDecimal rising;
    private LocalDateTime expiryDate;
    private boolean isPercentage;
    private boolean isSingleUse;

    public CreateGiftCardBody(String code, BigDecimal rising, LocalDateTime expiryDate, boolean isPercentage, boolean isSingleUse) {
        this.code = code;
        this.rising = rising;
        this.expiryDate = expiryDate;
        this.isPercentage = isPercentage;
        this.isSingleUse = isSingleUse;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getRising() {
        return rising;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public boolean isPercentage() {
        return isPercentage;
    }

    public boolean isSingleUse() {
        return isSingleUse;
    }
}

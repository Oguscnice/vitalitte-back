package fr.vitalitte.vitalittebackend.gifCard.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GiftCardDto {
    private String code;
    private BigDecimal rising;
    private LocalDateTime expiryDate;
    private boolean isPercentage;

    public GiftCardDto() {}

    public GiftCardDto(String code, BigDecimal rising, LocalDateTime expiryDate, boolean isPercentage) {
        this.code = code;
        this.rising = rising;
        this.expiryDate = expiryDate;
        this.isPercentage = isPercentage;
    }
    public String getCode() {return code;}
    public void setCode(String code) {this.code = code;}
    public BigDecimal getRising() {return rising;}
    public void setRising(BigDecimal rising) {this.rising = rising;}
    public LocalDateTime getExpiryDate() {return expiryDate;}
    public void setExpiryDate(LocalDateTime expiryDate) {this.expiryDate = expiryDate;}
    public boolean isPercentage() {return isPercentage;}
    public void setPercentage(boolean percentage) {isPercentage = percentage;}
    public static GiftCardDtoBuilder builder(){return new GiftCardDtoBuilder();}
    public static class GiftCardDtoBuilder {
        private String code;
        private BigDecimal rising;
        private LocalDateTime expiryDate;
        private boolean isPercentage;

        public GiftCardDtoBuilder code(String code){
            this.code = code;
            return this;
        }
        public GiftCardDtoBuilder rising(BigDecimal rising){
            this.rising = rising;
            return this;
        }
        public GiftCardDtoBuilder expiryDate(LocalDateTime expiryDate){
            this.expiryDate = expiryDate;
            return this;
        }
        public GiftCardDtoBuilder isPercentage(boolean isPercentage){
            this.isPercentage = isPercentage;
            return this;
        }
        public GiftCardDto build(){
            return new GiftCardDto(this.code, this.rising, this.expiryDate, this.isPercentage);
        }
    }
}

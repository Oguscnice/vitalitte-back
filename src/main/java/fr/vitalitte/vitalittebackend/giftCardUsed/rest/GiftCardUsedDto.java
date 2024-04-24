package fr.vitalitte.vitalittebackend.giftCardUsed.rest;

import fr.vitalitte.vitalittebackend.gifCard.rest.GiftCardDto;

import java.sql.Timestamp;

public class GiftCardUsedDto {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private GiftCardDto giftCardDto;
    private Timestamp createdAt;

    public GiftCardUsedDto(String firstname, String lastname, String email, String phone, GiftCardDto giftCardDto, Timestamp createdAt) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.giftCardDto = giftCardDto;
        this.createdAt = createdAt;
    }

    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}
    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public GiftCardDto getGiftCardDto() {return giftCardDto;}
    public void setGiftCardDto(GiftCardDto giftCardDto) {this.giftCardDto = giftCardDto;}
    public Timestamp getCreatedAt() {return createdAt;}
    public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}
    public static GiftCardUsedDtoBuilder builder(){return new GiftCardUsedDtoBuilder();}
    public static class GiftCardUsedDtoBuilder {
        private String firstname;
        private String lastname;
        private String email;
        private String phone;
        private GiftCardDto giftCardDto;
        private Timestamp createdAt;
        public GiftCardUsedDtoBuilder firstname(String firstname){
            this.firstname = firstname;
            return this;
        }
        public GiftCardUsedDtoBuilder lastname(String lastname){
            this.lastname = lastname;
            return this;
        }
        public GiftCardUsedDtoBuilder email(String email){
            this.email = email;
            return this;
        }
        public GiftCardUsedDtoBuilder phone(String phone){
            this.phone = phone;
            return this;
        }
        public GiftCardUsedDtoBuilder giftCardDto(GiftCardDto giftCardDto){
            this.giftCardDto = giftCardDto;
            return this;
        }
        public GiftCardUsedDtoBuilder createdAt(Timestamp createdAt){
            this.createdAt = createdAt;
            return this;
        }
        public GiftCardUsedDto build(){
            return new GiftCardUsedDto(this.firstname, this.lastname, this.email, this.phone, this.giftCardDto, this.createdAt);
        }
    }
}

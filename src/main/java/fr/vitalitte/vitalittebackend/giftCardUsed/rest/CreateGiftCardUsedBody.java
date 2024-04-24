package fr.vitalitte.vitalittebackend.giftCardUsed.rest;

import fr.vitalitte.vitalittebackend.gifCard.rest.GiftCardDto;

public class CreateGiftCardUsedBody {
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String phone;
    private final GiftCardDto giftCardDto;

    public CreateGiftCardUsedBody(String firstname, String lastname, String email, String phone, GiftCardDto giftCardDto) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.giftCardDto = giftCardDto;
    }

    public String getFirstname() {return firstname;}
    public String getLastname() {return lastname;}
    public String getEmail() {return email;}
    public String getPhone() {return phone;}
    public GiftCardDto getGiftCardDto() {return giftCardDto;}
}

package fr.vitalitte.vitalittebackend.inscription.rest;

import fr.vitalitte.vitalittebackend.workshop.rest.WorkshopDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateInscriptionBody {

    @NotBlank
    @Size(min = 1,max = 255)
    private final String firstname;

    @NotBlank
    @Size(min = 1,max = 255)
    private final String lastname;

    @NotBlank
    @Size(min = 10, max = 12)
    @Pattern(regexp = "(\\+?\\d{10,12})")
    private final String phone;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final WorkshopDto workshopDto;

    @Min(1)
    @Max(5)
    private final int quantity;

    public CreateInscriptionBody(String firstname, String lastname, String phone, String email, WorkshopDto workshopDto, int quantity) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.workshopDto = workshopDto;
        this.quantity = quantity;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public WorkshopDto getWorkshopDto() {
        return workshopDto;
    }

    public int getQuantity() {
        return quantity;
    }
}

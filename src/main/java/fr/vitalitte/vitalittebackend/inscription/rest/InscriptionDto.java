package fr.vitalitte.vitalittebackend.inscription.rest;

import fr.vitalitte.vitalittebackend.workshop.rest.WorkshopDto;

import java.sql.Timestamp;

public class InscriptionDto {

    private String slug;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private WorkshopDto workshopDto;
    private boolean isConfirmed;
    private int quantity;
    private Timestamp createdAt;

    public InscriptionDto(String slug, String firstname, String lastname, String phone, String email, WorkshopDto workshopDto, boolean isConfirmed, int quantity, Timestamp createdAt) {
        this.slug = slug;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.workshopDto = workshopDto;
        this.isConfirmed = isConfirmed;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public WorkshopDto getWorkshopDto() {
        return workshopDto;
    }

    public void setWorkshopDto(WorkshopDto workshopDto) {
        this.workshopDto = workshopDto;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public  static InscriptionDtoBuilder builder() {
        return new InscriptionDtoBuilder();
    }

    public static class InscriptionDtoBuilder {

        private String slug;
        private String firstname;
        private String lastname;
        private String phone;
        private String email;
        private WorkshopDto workshopDto;
        private boolean isConfirmed;
        private int quantity;
        private Timestamp createdAt;

        public InscriptionDtoBuilder slug(String slug){
            this.slug = slug;
            return this;
        }

        public InscriptionDtoBuilder firstname(String firstname){
            this.firstname = firstname;
            return this;
        }

        public InscriptionDtoBuilder lastname(String lastname){
            this.lastname = lastname;
            return this;
        }

        public InscriptionDtoBuilder phone(String phone){
            this.phone = phone;
            return this;
        }

        public InscriptionDtoBuilder email(String email){
            this.email = email;
            return this;
        }

        public InscriptionDtoBuilder workshopDto(WorkshopDto workshopDto){
            this.workshopDto = workshopDto;
            return this;
        }

        public InscriptionDtoBuilder isConfirmed(boolean isConfirmed){
            this.isConfirmed = isConfirmed;
            return this;
        }

        public InscriptionDtoBuilder quantity(int quantity){
            this.quantity = quantity;
            return this;
        }

        public InscriptionDtoBuilder createdAt(Timestamp createdAt){
            this.createdAt = createdAt;
            return this;
        }

        public  InscriptionDto build() {
            return new InscriptionDto(this.slug, this.firstname, this.lastname, this.phone, this.email, this.workshopDto, this.isConfirmed, this.quantity, this.createdAt);
        }
    }
}

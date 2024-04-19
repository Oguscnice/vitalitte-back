package fr.vitalitte.vitalittebackend.inscription.rest;

import fr.vitalitte.vitalittebackend.workshop.rest.WorkshopDto;

public class InscriptionDto {
    private String slug;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private WorkshopDto workshopDto;

    public InscriptionDto(String slug, String firstname, String lastname, String phone, String email, WorkshopDto workshopDto) {
        this.slug = slug;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.workshopDto = workshopDto;
    }

    public String getSlug() {return slug;}
    public void setSlug(String slug) {this.slug = slug;}
    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}
    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public WorkshopDto getWorkshopDto() {return workshopDto;}
    public void setWorkshopDto(WorkshopDto workshopDto) {this.workshopDto = workshopDto;}
    public  static InscriptionDtoBuilder builder(){ return new InscriptionDtoBuilder();}
    public static class InscriptionDtoBuilder {
        private String slug;
        private String firstname;
        private String lastname;
        private String phone;
        private String email;
        private WorkshopDto workshopDto;
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
        public  InscriptionDto build(){
            return new InscriptionDto(this.slug, this.firstname, this.lastname, this.phone, this.email, this.workshopDto);
        }
    }
}

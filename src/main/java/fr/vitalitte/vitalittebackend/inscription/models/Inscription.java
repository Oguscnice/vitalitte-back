package fr.vitalitte.vitalittebackend.inscription.models;

import fr.vitalitte.vitalittebackend.workshop.models.Workshop;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
public class Inscription {
    @Id
    private UUID id;
    @NotBlank
    private String slug;
    @NotBlank
    @Size(max = 255)
    private String firstname;
    @NotBlank
    @Size(max = 255)
    private String lastname;
    @NotBlank
    @Pattern(regexp = "(\\+33\\d{9}|\\d{10})")
    private String phone;
    @NotBlank
    @Email
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshop")
    private Workshop workshop;

    public Inscription() {}

    public Inscription(UUID id, String slug, String firstname, String lastname, String phone, String email, Workshop workshop) {
        this.id = id;
        this.slug = slug;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.workshop = workshop;
    }

    public UUID getId() {return id;}
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
    public Workshop getWorkshop() {return workshop;}
    public void setWorkshop(Workshop workshop) {this.workshop = workshop;}
    public static InscriptionBuilder builder(){return new InscriptionBuilder();}
    public static class InscriptionBuilder {
        private final UUID id = UUID.randomUUID();
        private String slug;
        private String firstname;
        private String lastname;
        private String phone;
        private String email;
        private Workshop workshop;
        public InscriptionBuilder slug(String slug){
            this.slug = slug;
            return this;
        }
        public InscriptionBuilder firstname(String firstname){
            this.firstname = firstname;
            return this;
        }
        public InscriptionBuilder lastname(String lastname){
            this.lastname = lastname;
            return this;
        }
        public InscriptionBuilder phone(String phone){
            this.phone = phone;
            return this;
        }
        public InscriptionBuilder email(String email){
            this.email = email;
            return this;
        }
        public InscriptionBuilder workshop(Workshop workshop){
            this.workshop = workshop;
            return this;
        }
        public Inscription build(){
            return new Inscription(this.id, this.slug, this.firstname, this.lastname, this.phone, this.email, this.workshop);
        }
    }
}

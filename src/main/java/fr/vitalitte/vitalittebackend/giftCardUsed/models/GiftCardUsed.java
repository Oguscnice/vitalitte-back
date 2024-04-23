package fr.vitalitte.vitalittebackend.giftCardUsed.models;

import fr.vitalitte.vitalittebackend.gifCard.models.GiftCard;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class GiftCardUsed {
    @Id
    private UUID id;
    @NotBlank
    @Size(max = 255)
    private String firstname;
    @NotBlank
    @Size(max = 255)
    private String lastname;
    @NotBlank
    @Size(max = 255)
    private String email;
    @NotBlank
    @Pattern(regexp = "(\\+33\\d{9}|\\d{10})")
    private String phone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giftcard")
    private GiftCard giftCard;
    @CreationTimestamp
    private Timestamp createdAt;
    public GiftCardUsed() {}

    public GiftCardUsed(UUID id, String firstname, String lastname, String email, String phone, GiftCard giftCard) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.giftCard = giftCard;
    }

    public UUID getId() {return id;}
    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}
    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public GiftCard getGiftCard() {return giftCard;}
    public void setGiftCard(GiftCard giftCard) {this.giftCard = giftCard;}
    public Timestamp getCreatedAt() {return createdAt;}
    public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}

    public static GiftCardUsedBuilder builder(){return new GiftCardUsedBuilder();}
    public static class GiftCardUsedBuilder {
        private final UUID id = UUID.randomUUID();
        private String firstname;
        private String lastname;
        private String email;
        private String phone;
        private GiftCard giftCard;
        public GiftCardUsedBuilder firstname(String firstname){
            this.firstname = firstname;
            return this;
        }
        public GiftCardUsedBuilder lastname(String lastname){
            this.lastname = lastname;
            return this;
        }
        public GiftCardUsedBuilder email(String email){
            this.email = email;
            return this;
        }
        public GiftCardUsedBuilder phone(String phone){
            this.phone = phone;
            return this;
        }
        public GiftCardUsedBuilder giftCard(GiftCard giftCard){
            this.giftCard = giftCard;
            return this;
        }
        public GiftCardUsed build(){
            return  new GiftCardUsed(this.id, this.firstname, this.lastname, this.email, this.phone, this.giftCard);
        }
    }
}

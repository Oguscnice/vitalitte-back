package fr.vitalitte.vitalittebackend.stationery.secondaryPicture.models;

import fr.vitalitte.vitalittebackend.stationery.product.models.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.net.URL;
import java.util.UUID;

@Entity
public class SecondaryPicture {

    @Id
    private UUID id;

    @NotNull
    private URL picture;

    private URL pictureThumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private Product product;

    public SecondaryPicture(){}

    public SecondaryPicture(UUID id, URL picture, URL pictureThumbnail, Product product) {
        this.id = id;
        this.picture = picture;
        this.pictureThumbnail = pictureThumbnail;
        this.product = product;
    }

    public UUID getId() {
        return id;
    }

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }

    public URL getPictureThumbnail() {
        return pictureThumbnail;
    }

    public void setPictureThumbnail(URL pictureThumbnail) {
        this.pictureThumbnail = pictureThumbnail;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static SecondaryPictureBuilder builder() {
        return new SecondaryPictureBuilder();
    }

    public static class SecondaryPictureBuilder {

        private final UUID id = UUID.randomUUID();
        private URL picture;
        private URL pictureThumbnail;
        private Product product;

        public SecondaryPictureBuilder picture(URL picture){
            this.picture = picture;
            return this;
        }

        public SecondaryPictureBuilder pictureThumbnail(URL pictureThumbnail){
            this.pictureThumbnail = pictureThumbnail;
            return this;
        }

        public SecondaryPictureBuilder product(Product product){
            this.product = product;
            return this;
        }

        public SecondaryPicture build(){
            return new SecondaryPicture(this.id, this.picture, this.pictureThumbnail, this.product);
        }
    }
}

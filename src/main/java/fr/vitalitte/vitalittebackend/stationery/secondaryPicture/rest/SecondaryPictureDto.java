package fr.vitalitte.vitalittebackend.stationery.secondaryPicture.rest;

public class SecondaryPictureDto {

    private String picture;
    private String pictureThumbnail;

    public SecondaryPictureDto(){}

    public SecondaryPictureDto(String picture, String pictureThumbnail) {
        this.picture = picture;
        this.pictureThumbnail = pictureThumbnail;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureThumbnail() {
        return pictureThumbnail;
    }

    public void setPictureThumbnail(String pictureThumbnail) {
        this.pictureThumbnail = pictureThumbnail;
    }

    public static SecondaryPictureDtoBuilder builder() {
        return new SecondaryPictureDtoBuilder();
    }

    public static class SecondaryPictureDtoBuilder {

        private String picture;
        private String pictureThumbnail;

        public SecondaryPictureDtoBuilder picture(String picture){
            this.picture = picture;
            return this;
        }

        public SecondaryPictureDtoBuilder pictureThumbnail(String pictureThumbnail){
            this.pictureThumbnail = pictureThumbnail;
            return this;
        }

        public SecondaryPictureDto build(){ return new SecondaryPictureDto(this.picture, this.pictureThumbnail); }
    }
}

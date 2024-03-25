package fr.vitalitte.vitalittebackend.secondaryPicture.rest;

public class SecondaryPictureDto {
    private String url;

    public SecondaryPictureDto(){}

    public SecondaryPictureDto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public static SecondaryPictureDtoBuilder builder(){return new SecondaryPictureDtoBuilder();}

    public static class SecondaryPictureDtoBuilder {
        private String url;
        public SecondaryPictureDtoBuilder url(String url){
            this.url = url;
            return this;
        }
        public SecondaryPictureDto build(){return new SecondaryPictureDto(this.url);}
    }
}

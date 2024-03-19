package fr.vitalitte.vitalittebackend.category.rest;

public class CategoryDto {
    private String slug;
    private String name;
    public String getSlug() {
        return slug;
    }
    public void setSlug(String slug) {
        this.slug = slug;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public CategoryDto() {}
    public CategoryDto(String slug, String name) {
        this.slug = slug;
        this.name = name;
    }
    public static CategoryDtoBuilder builder(){return new CategoryDtoBuilder();}
    public static class CategoryDtoBuilder {
        private String slug;
        private String name;
        public CategoryDtoBuilder slug(String slug){
            this.slug = slug;
            return this;
        }
        public CategoryDtoBuilder name(String name){
            this.name = name;
            return this;
        }
        public CategoryDto build(){return new CategoryDto(this.slug, this.name);}
    }
}

package fr.vitalitte.vitalittebackend.collection.rest;

import fr.vitalitte.vitalittebackend.category.rest.CategoryDto;

public class CollectionDto {
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

    public CollectionDto() {}

    public CollectionDto(String slug, String name) {
        this.slug = slug;
        this.name = name;
    }

    public static CollectionDtoBuilder builder(){return new CollectionDtoBuilder();}
    public static class CollectionDtoBuilder{
        private String slug;
        private String name;
        public CollectionDtoBuilder slug(String slug){
            this.slug = slug;
            return this;
        }
        public CollectionDtoBuilder name(String name){
            this.name = name;
            return this;
        }
        public CollectionDto build(){return new CollectionDto(this.slug, this.name);}
    }
}

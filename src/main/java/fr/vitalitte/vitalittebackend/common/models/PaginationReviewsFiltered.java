package fr.vitalitte.vitalittebackend.common.models;

import fr.vitalitte.vitalittebackend.stationery.product.rest.ProductDto;

import java.math.BigDecimal;

public class PaginationReviewsFiltered extends PaginationItemBySearchValue {

    String status;
    ProductDto productDto;
    BigDecimal rating;

    public String getStatus() {
        return status;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public BigDecimal getRating() {
        return rating;
    }
}

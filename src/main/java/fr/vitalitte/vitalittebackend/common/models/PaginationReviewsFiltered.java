package fr.vitalitte.vitalittebackend.common.models;

import fr.vitalitte.vitalittebackend.stationery.common.rest.ProductCommonValuesDto;

import java.math.BigDecimal;

public class PaginationReviewsFiltered extends PaginationItemBySearchValue {

    String status;
    ProductCommonValuesDto productCommonValuesDto;
    BigDecimal rating;

    public String getStatus() {
        return status;
    }

    public ProductCommonValuesDto getProductCommonValuesDto() {
        return productCommonValuesDto;
    }

    public BigDecimal getRating() {
        return rating;
    }
}

package fr.vitalitte.vitalittebackend.publication.rest;

import fr.vitalitte.vitalittebackend.common.models.Pagination;

public class PublicationPaginated {

    private final String valueSearch;

    private final Pagination pagination;

    public PublicationPaginated(String valueSearch, Pagination pagination) {
        this.valueSearch = valueSearch;
        this.pagination = pagination;
    }

    public String getValueSearch() {
        return valueSearch;
    }

    public Pagination getPagination() {
        return pagination;
    }
}

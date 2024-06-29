package fr.vitalitte.vitalittebackend.common.models;

import jakarta.validation.constraints.NotBlank;

public class Pagination {

    @NotBlank
    private final int page;

    @NotBlank
    private final int size;

    public Pagination(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}


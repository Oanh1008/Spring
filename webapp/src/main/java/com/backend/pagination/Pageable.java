package com.backend.pagination;

import java.util.List;

/**
 * returned type after call pagination rest service
 * @param <Element>
 */
public class Pageable<Element> {

    private List<Element> elements;

    private int totalPages;

    public Pageable() {
    }

    public Pageable(List<Element> elements, int totalPages) {
        this.elements = elements;
        this.totalPages = totalPages;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
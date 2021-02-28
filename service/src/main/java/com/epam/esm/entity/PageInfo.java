package com.epam.esm.entity;

public class PageInfo {
    private int pageNumber;
    private int pageSize;
    private long entitiesNumber;

    public PageInfo() {
    }

    public PageInfo(int pageNumber, int pageSize, long entitiesNumber) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.entitiesNumber = entitiesNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getEntitiesNumber() {
        return entitiesNumber;
    }

    public int getOffset() {
        return (pageNumber - 1) * pageSize;
    }
}

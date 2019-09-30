package com.guestlogix.takehome.models;

public class ResultInfo {
    private int count;
    private int pages;


    public ResultInfo(int count, int pages) {
        this.count = count;
        this.pages = pages;
    }

    public int getCount() {
        return count;
    }

    public int getPages() {
        return pages;
    }
}

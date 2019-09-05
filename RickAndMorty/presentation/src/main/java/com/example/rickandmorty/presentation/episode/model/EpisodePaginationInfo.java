package com.example.rickandmorty.presentation.episode.model;

public class EpisodePaginationInfo {
    private int count;
    private int pages;
    private String next;
    private String prev;

    public EpisodePaginationInfo() {
        count = 0;
        pages = 0;
        next = "";
        prev = "";
    }

    public EpisodePaginationInfo(int count, int pages, String next, String prev) {
        this.count = count;
        this.pages = pages;
        this.next = next;
        this.prev = prev;
    }

    public int getCount() {
        return count;
    }

    public int getPages() {
        return pages;
    }

    public String getNext() {
        return next;
    }

    public String getPrev() {
        return prev;
    }
}

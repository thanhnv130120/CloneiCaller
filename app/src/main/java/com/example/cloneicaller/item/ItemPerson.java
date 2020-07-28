package com.example.cloneicaller.item;

public class ItemPerson  {
    private String name;
    private int viewType;

    public ItemPerson() {
    }

    public ItemPerson(String name, int viewType) {
        this.name = name;
        this.viewType = viewType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}

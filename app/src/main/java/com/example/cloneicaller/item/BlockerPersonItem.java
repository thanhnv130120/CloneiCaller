package com.example.cloneicaller.item;

public class BlockerPersonItem {
    private String name;
    private String type;
    private String number;
    private String image;
    public BlockerPersonItem(String name, String type, String number,String image) {
        this.name = name;
        this.type = type;
        this.number = number;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

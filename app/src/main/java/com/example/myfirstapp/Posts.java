package com.example.myfirstapp;

public class Posts {
    private String post_id;
    private String user_id;
    private String image;
    private String title;
    private String price;
    private String description;
    private String campus;
    private String other;
    private String email;
    private String cell;
    private String name;

    public Posts(String post_id, String user_id, String image, String title, String price, String description, String campus, String other, String email, String cell, String name) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.image = image;
        this.title = title;
        this.price = price;
        this.description = description;
        this.campus = campus;
        this.other = other;
        this.email = email;
    }

    public Posts(){

    }

    //Getters
    public String getPost_id() {
        return post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCampus() {
        return campus;
    }

    public String getOther() {
        return other;
    }

    public String getEmail() {
        return email;
    }

    public String getCell() {
        return cell;
    }

    public String getName() {
        return name;
    }

    //Setters

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public void setName(String name) {
        this.name = name;
    }

    //toString


    @Override
    public String toString() {
        return "Posts{" +
                "post_id='" + post_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", campus='" + campus + '\'' +
                ", other='" + other + '\'' +
                ", email='" + email + '\'' +
                ", cell='" + cell + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

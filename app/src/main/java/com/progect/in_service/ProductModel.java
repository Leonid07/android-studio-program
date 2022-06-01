package com.progect.in_service;

public class ProductModel {
    private String name;
    private String description;
    private String price;
    private String condition;
    private String timeCode;
    private String ID;
    private String ID_Master;
    private String OtvetMas;

    public ProductModel() {
    }

    private ProductModel(String name, String description, String price, String condition, String timeCode, String ID_Master, String OtvetMas) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.timeCode = timeCode;
        this.ID_Master = ID_Master;
        this.OtvetMas = OtvetMas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTimeCode() {
        return timeCode;
    }

    public void setTimeCode(String timeCode) {
        this.timeCode = timeCode;
    }

    public String getID_Master() {
        return ID_Master;
    }

    public void setID_Master(String ID_Master) {
        this.ID_Master = ID_Master;
    }

    public String getOtvetMas() {
        return OtvetMas;
    }

    public void setOtvetMas(String otvetMas) {
        this.OtvetMas = otvetMas;
    }
}

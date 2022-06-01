package com.progect.in_service.regestrationsNewMan;

public class NewMan {
    private String name;
    private String secondName;
    private String lastName;
    public String profession;
    public String telephone;

    public NewMan() {
    }

    private NewMan(String name, String secondName, String lastName, String profession) {
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.profession = profession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

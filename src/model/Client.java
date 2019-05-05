package model;

import org.jetbrains.annotations.Contract;

public class Client {

    private int id;
    private String name;
    private String phoneNumber;

    @Contract(pure = true)
    public Client(String name, String phoneNumber) {
        this.id = -1;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Contract(pure = true)
    public Client(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return id + "#" + name + "#" + phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

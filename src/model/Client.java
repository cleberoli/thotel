package model;

import org.jetbrains.annotations.Contract;

public class Client {

    private int id;
    private String name;
    private String phone;

    @Contract(pure = true)
    public Client(String name, String phone) {
        this.id = -1;
        this.name = name;
        this.phone = phone;
    }

    @Contract(pure = true)
    public Client(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return id + "#" + name + "#" + phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

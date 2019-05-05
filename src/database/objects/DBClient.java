package database.objects;

import database.DatabaseObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DBClient implements DatabaseObject {

    private int id;
    private String name;
    private String phoneNumber;
    private boolean deleted;

    @Contract(pure = true)
    public DBClient(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.deleted = false;
    }

    @Contract(pure = true)
    public DBClient(int id, String name, String phoneNumber, boolean deleted) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.deleted = deleted;
    }

    @Contract(pure = true)
    public DBClient(@NotNull String attributes) {
        String[] parts = attributes.split("#");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.phoneNumber = parts[2];
        this.deleted = Boolean.parseBoolean(parts[3]);
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

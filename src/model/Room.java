package model;

import org.jetbrains.annotations.Contract;

public class Room {

    private int id;
    private int categoryId;

    @Contract(pure = true)
    public Room(int categoryId) {
        this.id = -1;
        this.categoryId = categoryId;
    }

    @Contract(pure = true)
    public Room(int id, int categoryId) {
        this.id = id;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return id + "#" + categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}

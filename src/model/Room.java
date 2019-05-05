package model;

import org.jetbrains.annotations.Contract;

public class Room {

    private int id;
    private int idCategory;

    @Contract(pure = true)
    public Room(int idCategory) {
        this.id = -1;
        this.idCategory = idCategory;
    }

    @Contract(pure = true)
    public Room(int id, int idCategory) {
        this.id = id;
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return id + "#" + idCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}

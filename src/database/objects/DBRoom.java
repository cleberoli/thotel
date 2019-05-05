package database.objects;

import database.DatabaseObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DBRoom implements DatabaseObject {

    private int id;
    private int idCategory;
    private boolean deleted;

    @Contract(pure = true)
    public DBRoom(int id, int idCategory) {
        this.id = id;
        this.idCategory = idCategory;
        this.deleted = false;
    }

    @Contract(pure = true)
    public DBRoom(int id, int idCategory, boolean deleted) {
        this.id = id;
        this.idCategory = idCategory;
        this.deleted = deleted;
    }

    public DBRoom(@NotNull String attributes) {
        String[] parts = attributes.split("#");
        this.id = Integer.parseInt(parts[0]);
        this.idCategory = Integer.parseInt(parts[1]);
        this.deleted = Boolean.parseBoolean(parts[2]);
    }

    @Override
    public String toString() {
        return id + "#" + idCategory + "#" + deleted;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

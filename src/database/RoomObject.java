package database;

public class RoomObject {

    private int id;
    private int idCategory;
    private boolean deleted;

    public RoomObject(int id, int idCategory, boolean deleted) {
        this.id = id;
        this.idCategory = idCategory;
        this.deleted = deleted;
    }

    public RoomObject(String attributes) {
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

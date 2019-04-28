package database;

public class ClientObject {

    private int id;
    private String name;
    private String phoneNumber;

    public ClientObject(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public ClientObject(String attributes) {
        String[] parts = attributes.split("#");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.phoneNumber = parts[2];
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

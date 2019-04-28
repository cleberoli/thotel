package database;

public class CategoryObject {

    private int id;
    private String name;
    private double dailyRate;
    private int capacity;
    private boolean extraBed;
    private double extraBedFee;

    public CategoryObject(int id, String name, double dailyRate, int capacity, boolean extraBed, double extraBedFee) {
        this.id = id;
        this.name = name;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.extraBed = extraBed;
        this.extraBedFee = extraBedFee;
    }

    public CategoryObject(String attributes) {
        String[] parts = attributes.split("#");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.dailyRate = Double.parseDouble(parts[2]);
        this.capacity = Integer.parseInt(parts[3]);
        this.extraBed = Boolean.parseBoolean(parts[4]);
        this.extraBedFee = Double.parseDouble(parts[5]);
    }

    @Override
    public String toString() {
        return id + "#" + name + "#" + dailyRate + "#" + capacity + "#" + extraBed + "#" + extraBedFee;
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

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isExtraBed() {
        return extraBed;
    }

    public void setExtraBed(boolean extraBed) {
        this.extraBed = extraBed;
    }

    public double getExtraBedFee() {
        return extraBedFee;
    }

    public void setExtraBedFee(double extraBedFee) {
        this.extraBedFee = extraBedFee;
    }
}

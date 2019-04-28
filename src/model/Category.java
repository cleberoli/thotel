package model;

public class Category {

    private int id;
    private String name;
    private double dailyRate;
    private int capacity;
    private boolean extraBed;
    private double extraBedFee;

    public Category(int id, String name, double dailyRate, int capacity) {
        this.id = id;
        this.name = name;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.extraBed = true;
        this.extraBedFee = 0.3;
    }

    public Category(int id, String name, double dailyRate, int capacity, boolean extraBed, double extraBedFee) {
        this.id = id;
        this.name = name;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.extraBed = extraBed;
        this.extraBedFee = extraBedFee;
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

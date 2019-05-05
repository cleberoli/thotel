package database.objects;

import database.DatabaseObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DBCategory implements DatabaseObject {

    private int id;
    private String name;
    private double dailyRate;
    private int capacity;
    private boolean extraBed;
    private double extraBedFee;
    private boolean deleted;

    @Contract(pure = true)
    public DBCategory(int id, String name, double dailyRate, int capacity, boolean extraBed, double extraBedFee) {
        this.id = id;
        this.name = name;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.extraBed = extraBed;
        this.extraBedFee = extraBedFee;
        this.deleted = false;
    }

    @Contract(pure = true)
    public DBCategory(int id, String name, double dailyRate, int capacity, boolean extraBed, double extraBedFee, boolean deleted) {
        this.id = id;
        this.name = name;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.extraBed = extraBed;
        this.extraBedFee = extraBedFee;
        this.deleted = deleted;
    }

    @Contract(pure = true)
    public DBCategory(@NotNull String attributes) {
        String[] parts = attributes.split("#");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.dailyRate = Double.parseDouble(parts[2]);
        this.capacity = Integer.parseInt(parts[3]);
        this.extraBed = Boolean.parseBoolean(parts[4]);
        this.extraBedFee = Double.parseDouble(parts[5]);
        this.deleted = Boolean.parseBoolean(parts[6]);
    }

    @Override
    public String toString() {
        return id + "#" + name + "#" + dailyRate + "#" + capacity + "#" + extraBed + "#" + extraBedFee + "#" + deleted;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

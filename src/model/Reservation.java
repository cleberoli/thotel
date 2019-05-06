package model;

import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

    private int id;
    private int clientId;
    private int roomId;
    private int guests;
    private Date expectedCheckinDate;
    private Date expectedCheckoutDate;

    @Contract(pure = true)
    public Reservation(int clientId, int roomId, int guests, Date expectedCheckinDate, Date expectedCheckoutDate) {
        this.id = -1;
        this.clientId = clientId;
        this.roomId = roomId;
        this.guests = guests;
        this.expectedCheckinDate = expectedCheckinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
    }

    @Contract(pure = true)
    public Reservation(int id, int clientId, int roomId, int guests, Date expectedCheckinDate, Date expectedCheckoutDate) {
        this.id = id;
        this.clientId = clientId;
        this.roomId = roomId;
        this.guests = guests;
        this.expectedCheckinDate = expectedCheckinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
    }

    @Override
    public String toString() {
        return clientId + "#" + roomId + "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckinDate) +
                "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckoutDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public Date getExpectedCheckinDate() {
        return expectedCheckinDate;
    }

    public void setExpectedCheckinDate(Date expectedCheckinDate) {
        this.expectedCheckinDate = expectedCheckinDate;
    }

    public Date getExpectedCheckoutDate() {
        return expectedCheckoutDate;
    }

    public void setExpectedCheckoutDate(Date expectedCheckoutDate) {
        this.expectedCheckoutDate = expectedCheckoutDate;
    }
}

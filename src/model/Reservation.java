package model;

import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

    private int idClient;
    private int idRoom;
    private int guests;
    private Date expectedCheckinDate;
    private Date expectedCheckoutDate;

    @Contract(pure = true)
    public Reservation(int idClient, int idRoom, int guests, Date expectedCheckinDate, Date expectedCheckoutDate) {
        this.idClient = idClient;
        this.idRoom = idRoom;
        this.guests = guests;
        this.expectedCheckinDate = expectedCheckinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
    }

    @Override
    public String toString() {
        return idClient + "#" + idRoom + "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckinDate) +
                "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckoutDate);
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
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

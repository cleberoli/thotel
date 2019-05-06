package model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Occupation {

    private int id;
    private int clientId;
    private int roomId;
    private int guests;
    private Date checkinDate;
    private Date expectedCheckoutDate;
    private Date checkoutDate;
    private double totalAmount;

    @Contract(pure = true)
    public Occupation(int clientId, int roomId, int guests, Date checkinDate, Date expectedCheckoutDate) {
        this.id = -1;
        this.clientId = clientId;
        this.roomId = roomId;
        this.guests = guests;
        this.checkinDate = checkinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
        this.checkoutDate = null;
        this.totalAmount = 0;
    }

    @Contract(pure = true)
    public Occupation(int id, int clientId, int roomId, int guests, Date checkinDate, Date expectedCheckoutDate, @Nullable Date checkoutDate, @Nullable double totalAmount) {
        this.id = id;
        this.clientId = clientId;
        this.roomId = roomId;
        this.guests = guests;
        this.checkinDate = checkinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
        this.checkoutDate = checkoutDate;
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        if (checkoutDate != null) {
            return clientId + "#" + roomId + "#" + guests + "#" + new SimpleDateFormat("dd/MM/yyyy").format(checkinDate) +
                    "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckoutDate) +
                    "#" + new SimpleDateFormat("dd/MM/yyyy").format(checkoutDate);
        } else {
            return clientId + "#" + roomId + "#" + guests + "#" + new SimpleDateFormat("dd/MM/yyyy").format(checkinDate) +
                    "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckoutDate) + "#null";
        }
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

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getExpectedCheckoutDate() {
        return expectedCheckoutDate;
    }

    public void setExpectedCheckoutDate(Date expectedCheckoutDate) {
        this.expectedCheckoutDate = expectedCheckoutDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

package model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Occupation {

    private int idClient;
    private int idRoom;
    private int guests;
    private Date checkinDate;
    private Date expectedCheckoutDate;
    private Date checkoutDate;

    @Contract(pure = true)
    public Occupation(int idClient, int idRoom, int guests, Date checkinDate, Date expectedCheckoutDate) {
        this.idClient = idClient;
        this.idRoom = idRoom;
        this.guests = guests;
        this.checkinDate = checkinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
        this.checkoutDate = null;
    }

    @Contract(pure = true)
    public Occupation(int idClient, int idRoom, int guests, Date checkinDate, Date expectedCheckoutDate, @Nullable Date checkoutDate) {
        this.idClient = idClient;
        this.idRoom = idRoom;
        this.guests = guests;
        this.checkinDate = checkinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
        this.checkoutDate = checkoutDate;
    }

    @Override
    public String toString() {
        if (checkoutDate != null) {
            return idClient + "#" + idRoom + "#" + guests + "#" + new SimpleDateFormat("dd/MM/yyyy").format(checkinDate) +
                    "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckoutDate) +
                    "#" + new SimpleDateFormat("dd/MM/yyyy").format(checkoutDate);
        } else {
            return idClient + "#" + idRoom + "#" + guests + "#" + new SimpleDateFormat("dd/MM/yyyy").format(checkinDate) +
                    "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckoutDate) + "#null";
        }
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
}

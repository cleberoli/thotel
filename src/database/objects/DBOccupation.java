package database.objects;

import database.DatabaseObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DBOccupation implements DatabaseObject {

    private int id;
    private int idClient;
    private int idRoom;
    private int guests;
    private Date checkinDate;
    private Date expectedCheckoutDate;
    private Date checkoutDate;
    private boolean deleted;

    @Contract(pure = true)
    public DBOccupation(int id, int idClient, int idRoom, int guests, Date checkinDate, Date expectedCheckoutDate) {
        this.id = id;
        this.idClient = idClient;
        this.idRoom = idRoom;
        this.guests = guests;
        this.checkinDate = checkinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
        this.checkoutDate = null;
        this.deleted = false;
    }

    @Contract(pure = true)
    public DBOccupation(int id, int idClient, int idRoom, int guests, Date checkinDate, Date expectedCheckoutDate, boolean deleted) {
        this.id = id;
        this.idClient = idClient;
        this.idRoom = idRoom;
        this.guests = guests;
        this.checkinDate = checkinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
        this.checkoutDate = null;
        this.deleted = deleted;
    }

    @Contract(pure = true)
    public DBOccupation(int id, int idClient, int idRoom, int guests, Date checkinDate, Date expectedCheckoutDate,@Nullable Date checkoutDate) {
        this.id = id;
        this.idClient = idClient;
        this.idRoom = idRoom;
        this.guests = guests;
        this.checkinDate = checkinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
        this.checkoutDate = checkoutDate;
        this.deleted = false;
    }

    @Contract(pure = true)
    public DBOccupation(int id, int idClient, int idRoom, int guests, Date checkinDate, Date expectedCheckoutDate,@Nullable Date checkoutDate, boolean deleted) {
        this.id = id;
        this.idClient = idClient;
        this.idRoom = idRoom;
        this.guests = guests;
        this.checkinDate = checkinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
        this.checkoutDate = checkoutDate;
        this.deleted = deleted;
    }

    @Contract(pure = true)
    public DBOccupation(@NotNull String attributes) {
        try {
            String[] parts = attributes.split("#");
            this.id = Integer.parseInt(parts[0]);
            this.idClient = Integer.parseInt(parts[1]);
            this.idRoom = Integer.parseInt(parts[2]);
            this.guests = Integer.parseInt(parts[3]);

            if (parts[4].startsWith("current")) {
                if (parts[4].equals("current")) {
                    this.checkinDate = new Date();
                } else if (parts[4].startsWith("current+")) {
                    int shift = Integer.parseInt(parts[4].split("\\+")[1]);
                    Date currentDate = new Date();
                    LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    localDateTime = localDateTime.plusDays(shift);
                    this.checkinDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                } else {
                    int shift = Integer.parseInt(parts[4].split("-")[1]);
                    Date currentDate = new Date();
                    LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    localDateTime = localDateTime.minusDays(shift);
                    this.checkinDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                }
            } else {
                this.checkinDate = new SimpleDateFormat("dd/MM/yyyy").parse(parts[4]);
            }

            if (parts[5].startsWith("current")) {
                if (parts[5].equals("current")) {
                    this.expectedCheckoutDate = new Date();
                } else if (parts[5].startsWith("current+")) {
                    int shift = Integer.parseInt(parts[5].split("\\+")[1]);
                    Date currentDate = new Date();
                    LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    localDateTime = localDateTime.plusDays(shift);
                    this.expectedCheckoutDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                } else {
                    int shift = Integer.parseInt(parts[5].split("-")[1]);
                    Date currentDate = new Date();
                    LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    localDateTime = localDateTime.minusDays(shift);
                    this.expectedCheckoutDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                }
            } else {
                this.expectedCheckoutDate = new SimpleDateFormat("dd/MM/yyyy").parse(parts[5]);
            }

            if (parts[6].equals("null")) {
                this.checkoutDate = null;
            } else {
                this.checkoutDate = new SimpleDateFormat("dd/MM/yyyy").parse(parts[6]);
            }

            this.deleted = Boolean.parseBoolean(parts[7]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        if (checkoutDate != null) {
            return id + "#" + idClient + "#" + idRoom + "#" + guests + "#" + new SimpleDateFormat("dd/MM/yyyy").format(checkinDate) +
                    "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckoutDate) +
                    "#" + new SimpleDateFormat("dd/MM/yyyy").format(checkoutDate) + "#" + deleted;
        } else {
            return id + "#" + idClient + "#" + idRoom + "#" + guests  + "#" + new SimpleDateFormat("dd/MM/yyyy").format(checkinDate) +
                    "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckoutDate) + "#null#" + deleted;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

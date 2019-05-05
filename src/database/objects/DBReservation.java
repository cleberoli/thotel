package database.objects;

import database.DatabaseObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DBReservation implements DatabaseObject {

    private int id;
    private int idClient;
    private int idRoom;
    private int guests;
    private Date expectedCheckinDate;
    private Date expectedCheckoutDate;
    private boolean deleted;

    @Contract(pure = true)
    public DBReservation(int id, int idClient, int idRoom, int guests, Date expectedCheckinDate, Date expectedCheckoutDate) {
        this.id = id;
        this.idClient = idClient;
        this.idRoom = idRoom;
        this.guests = guests;
        this.expectedCheckinDate = expectedCheckinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
        this.deleted = false;
    }

    @Contract(pure = true)
    public DBReservation(int id, int idClient, int idRoom, int guests, Date expectedCheckinDate, Date expectedCheckoutDate, boolean deleted) {
        this.id = id;
        this.idClient = idClient;
        this.idRoom = idRoom;
        this.guests = guests;
        this.expectedCheckinDate = expectedCheckinDate;
        this.expectedCheckoutDate = expectedCheckoutDate;
        this.deleted = deleted;
    }

    @Contract(pure = true)
    public DBReservation(@NotNull String attributes) {
        try {
            String[] parts = attributes.split("#");
            this.id = Integer.parseInt(parts[0]);
            this.idClient = Integer.parseInt(parts[1]);
            this.idRoom = Integer.parseInt(parts[2]);
            this.guests = Integer.parseInt(parts[3]);

            if (parts[4].startsWith("current")) {
                if (parts[4].equals("current")) {
                    this.expectedCheckinDate = new Date();
                } else if (parts[4].startsWith("current+")) {
                    int shift = Integer.parseInt(parts[4].split("\\+")[1]);
                    Date currentDate = new Date();
                    LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    localDateTime = localDateTime.plusDays(shift);
                    this.expectedCheckinDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                } else {
                    int shift = Integer.parseInt(parts[4].split("-")[1]);
                    Date currentDate = new Date();
                    LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    localDateTime = localDateTime.minusDays(shift);
                    this.expectedCheckinDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                }
            } else {
                this.expectedCheckinDate = new SimpleDateFormat("dd/MM/yyyy").parse(parts[4]);
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

            this.deleted = Boolean.parseBoolean(parts[6]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
            return id + "#" + idClient + "#" + idRoom + "#" + guests + "#"+ new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckinDate) +
                    "#" + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckoutDate) + "#" + deleted;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

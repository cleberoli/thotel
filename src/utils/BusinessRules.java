package utils;

import database.DataConnection;
import model.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class BusinessRules {

    public static boolean isValidReservation(Reservation reservation) {
        if (!isRoomFree(reservation)) {System.out.println("INVALID RESERVATION: The room is not free during the period requested.");}
        if (!isValidPeriod(reservation)) {System.out.println("INVALID RESERVATION: The guests must stay at the hotel for at least one day.");}
        if (isRoomBooked(reservation)) {System.out.println("INVALID RESERVATION: The room already has a reservation during the period requested.");}
        if (!isValidNumberGuests(reservation)) {System.out.println("INVALID RESERVATION: The selected room does not support so many guests.");}
        return isRoomFree(reservation) && isValidPeriod(reservation) && !isRoomBooked(reservation) && isValidNumberGuests(reservation);
    }

    private static boolean isRoomFree(Reservation reservation) {
        try {
            int roomId = reservation.getRoomId();
            String checkinDate = new SimpleDateFormat("yyyy-MM-dd").format(reservation.getExpectedCheckinDate());
            String checkoutDate = new SimpleDateFormat("yyyy-MM-dd").format(reservation.getExpectedCheckoutDate());
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT * from occupation WHERE room_id = "+roomId+" AND NOT EXISTS (\n" +
                    "\tSELECT * from occupation WHERE checkout_date IS NOT NULL AND checkout_date < '"+checkinDate+"' OR\n" +
                    "\t\tcheckout_date IS NULL AND expected_checkout_date < '"+checkinDate+"' OR\n" +
                    "\t\tcheckin_date > '"+checkoutDate+"');");

            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isRoomBooked(Reservation reservation) {
        try {
            int roomId = reservation.getRoomId();
            String checkinDate = new SimpleDateFormat("yyyy-MM-dd").format(reservation.getExpectedCheckinDate());
            String checkoutDate = new SimpleDateFormat("yyyy-MM-dd").format(reservation.getExpectedCheckoutDate());
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT * from reservation WHERE room_id = "+roomId+" AND NOT EXISTS (\n" +
                    "\tSELECT * from occupation WHERE expected_checkout_date < '"+checkinDate+"' OR\n" +
                    "\t\texpected_checkin_date > '"+checkoutDate+"');");

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isValidPeriod(Reservation reservation) {
        return reservation.getExpectedCheckoutDate().compareTo(reservation.getExpectedCheckinDate()) > 0;
    }

    private static boolean isValidNumberGuests(Reservation reservation) {
        try {
            int roomId = reservation.getRoomId();
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT capacity from room INNER JOIN category ON room.category_id = category.id WHERE room.id = "+roomId+";");

            if (rs.next()) {
                int capacity = rs.getInt("capacity");
                return reservation.getGuests() <= (capacity + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

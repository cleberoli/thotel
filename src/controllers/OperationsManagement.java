package controllers;

import database.DataConnection;
import model.Client;
import model.Occupation;
import model.Reservation;
import model.Room;
import utils.BusinessRules;
import utils.InputMethods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class OperationsManagement {

    public static void makeReservation() {
        System.out.println("201 - Make a reservation ===============================================================");
        Reservation reservation;
        String clientName;
        Date expectedCheckinDate, expectedCheckoutDate;
        int roomId, guests;

        do {
            Client client = InputMethods.getValidClientById();
            clientName = client.getName();
            Room room = InputMethods.getValidRoomById();
            roomId = room.getId();
            expectedCheckinDate = InputMethods.getValidDate("Please enter the check-in date in the format DD/MM/YYYY: ",
                    "Please enter the date in the format DD/MM/YYYY: ",
                    "The date entered is not valid.");
            expectedCheckoutDate = InputMethods.getValidDate("Please enter the check-out date in the format DD/MM/YYYY: ",
                    "Please enter the date in the format DD/MM/YYYY: ",
                    "The date entered is not valid.");
            guests = InputMethods.getValidGuests("Please enter the number of guests: ",
                    "Please enter a positive number of guests: ");

            reservation = new Reservation(client.getId(), room.getId(), guests, expectedCheckinDate, expectedCheckoutDate);
        } while (!BusinessRules.isValidReservation(reservation));

        int reservationId = saveReservation(reservation);
        System.out.println("\nReservation Details:");
        System.out.println("ID: " + reservationId);
        System.out.println("CLIENT: " + clientName);
        System.out.println("ROOM: " + roomId);
        System.out.println("EXPECTED CHECK-IN DATE: " + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckinDate));
        System.out.println("EXPECTED CHECK-OUT DATE: " + new SimpleDateFormat("dd/MM/yyyy").format(expectedCheckoutDate));
        System.out.println("NUMBER OF GUESTS: " + guests);
    }

    public static void checkIn() {
        System.out.println("202 - Check-in =========================================================================");
        Reservation reservation = InputMethods.getValidReservationById();
        boolean deleteReservation = reservation != null;

        if (reservation.getExpectedCheckinDate().compareTo(new Date()) == 0) {
            System.out.println("The check-in date for this reservation is not today. You must makeReservation a new reservation.");
            reservation = null;
            deleteReservation = false;
        }

        if (reservation == null)  {
            Date checkinDate, expectedCheckoutDate;

            do {
                Client client = InputMethods.getValidClientById();
                Room room = InputMethods.getValidRoomById();
                checkinDate = new Date();
                expectedCheckoutDate = InputMethods.getValidDate("Please enter the check-out date in the format DD/MM/YYYY: ",
                        "Please enter the date in the format DD/MM/YYYY: ",
                        "The date entered is not valid.");
                int guests = InputMethods.getValidGuests("Please enter the number of guests: ",
                        "Please enter a positive number of guests: ");

                reservation = new Reservation(client.getId(), room.getId(), guests, checkinDate, expectedCheckoutDate);
            } while (!BusinessRules.isValidReservation(reservation));
        }

        Occupation occupation = new Occupation(reservation.getClientId(),
                reservation.getRoomId(),
                reservation.getGuests(),
                reservation.getExpectedCheckinDate(),
                reservation.getExpectedCheckoutDate());

        int occupationId = saveOccupation(occupation);

        if (deleteReservation) {
            deleteReservation(reservation);
        }

        System.out.println("\nOccupation Details:");
        System.out.println("ID: " + occupationId);
        System.out.println("EXPECTED CHECK-OUT DATE: " + new SimpleDateFormat("dd/MM/yyyy").format(occupation.getExpectedCheckoutDate()));

    }

    public static void checkOut() {
        System.out.println("203 - Check-out ========================================================================");
        Room room = InputMethods.getValidRoomById();
        Occupation occupation = InputMethods.getValidOccupationByRoom(room.getId());

        if (occupation != null) {
            occupation.setCheckoutDate(new Date());

            long diffInMilliseconds = occupation.getCheckoutDate().getTime() - occupation.getCheckinDate().getTime();
            long occupationPeriod = TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);

            try {
                DataConnection connection = DataConnection.getInstance();
                ResultSet rs = connection.sql("SELECT client.name as client_name, category.name as category_name, " +
                        "category.daily_rate, category.capacity, category.extra_bed_fee FROM occupation \n" +
                        "\tINNER JOIN client ON occupation.client_id = client.id\n" +
                        "\tINNER JOIN room ON occupation.room_id = room.id\n" +
                        "\tINNER JOIN category ON room.category_id = category.id\n" +
                        "\tWHERE occupation.id = " + occupation.getId() + ";");

                rs.next();
                String clientName = rs.getString("client_name");
                String categoryName = rs.getString("category_name");
                double dailyRate = rs.getDouble("daily_rate");
                int capacity = rs.getInt("capacity");
                double extraBedFee = rs.getDouble("extra_bed_fee");

                double totalAmount = occupationPeriod * dailyRate;

                occupation.setTotalAmount(totalAmount);
                updateOccupation(occupation);

                if (occupation.getGuests() > capacity) {
                    totalAmount += totalAmount * extraBedFee;
                }

                System.out.println("CHECKOUT SUCCESSFUL ===================================================================");
                System.out.println("OCCUPATION DETAILS ====================================================================");
                System.out.printf("CLIENT: %d - %s\n", occupation.getClientId(), clientName);
                System.out.printf("ROOM: %d - %s\n", occupation.getRoomId(), categoryName);
                System.out.printf("NUMBER OF GUESTS: %d\n", occupation.getGuests());
                System.out.printf("CHECK-IN DATE: %s\n", new SimpleDateFormat("dd/MM/yyyy").format(occupation.getCheckinDate()));
                System.out.printf("CHECK-OUT DATE: %s\n", new SimpleDateFormat("dd/MM/yyyy").format(occupation.getCheckoutDate()));

                if (occupationPeriod == 1) {
                    System.out.printf("OCCUPATION PERIOD: %d day\n", occupationPeriod);
                } else {
                    System.out.printf("OCCUPATION PERIOD: %d days\n", occupationPeriod);
                }

                System.out.printf("DAILY RATE: %.2f\n", dailyRate);
                System.out.printf("TOTAL AMOUNT: %.2f\n", totalAmount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static int saveOccupation(Occupation occupation) {
        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("INSERT INTO occupation (client_id, room_id, guests, checkin_date, expected_checkout_date)\n" +
                    "\tVALUES ("+occupation.getClientId()+", "+occupation.getRoomId()+", "+occupation.getGuests()+
                    ", '"+new SimpleDateFormat("yyyy-MM-dd").format(occupation.getCheckinDate())+
                    "', '"+new SimpleDateFormat("yyyy-MM-dd").format(occupation.getExpectedCheckoutDate())+"') RETURNING id;");

            rs.next();
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static void updateOccupation(Occupation occupation) {
        DataConnection connection = DataConnection.getInstance();
        connection.executeUpdate("UPDATE occupation SET checkout_date='"+occupation.getCheckoutDate()+"'," +
                "total_amount='"+occupation.getTotalAmount()+"' WHERE id = "+occupation.getId()+";");
    }

    private static int saveReservation(Reservation reservation) {
        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("INSERT INTO reservation (client_id, room_id, guests, expected_checkin_date, expected_checkout_date)\n" +
                    "\tVALUES ("+reservation.getClientId()+", "+reservation.getRoomId()+", "+reservation.getGuests()+
                    ", '"+new SimpleDateFormat("yyyy-MM-dd").format(reservation.getExpectedCheckinDate())+
                    "', '"+new SimpleDateFormat("yyyy-MM-dd").format(reservation.getExpectedCheckoutDate())+"') RETURNING id;");

            rs.next();
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static void deleteReservation(Reservation reservation) {
        DataConnection connection = DataConnection.getInstance();
        connection.executeUpdate("DELETE FROM reservation where id =  "+reservation.getId()+";");

    }

}

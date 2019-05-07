package controllers;

import database.DataConnection;
import model.Client;
import model.Room;
import utils.InputMethods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Reports {

    public static void hotelReport() {
        System.out.println("301 - Hotel Report =====================================================================");

        int totalRooms = getTotalRooms();
        int availableRooms = totalRooms - getOccupiedRooms();
        double monthRevenue = getMonthRevenue();
        int monthGuests = getMonthGuests();
        int reservationsToday = getReservationsToday();

        System.out.println("\nHOTEL DETAILS ==========================================================================");
        System.out.printf("NUMBER OF ROOMS: %d\n", totalRooms);
        System.out.printf("AVAILABLE ROOMS: %d\n", availableRooms);
        System.out.printf("THIS MONTH'S REVENUES: %.2f\n", monthRevenue);
        System.out.printf("THIS MONTH'S GUESTS: %d\n", monthGuests);
        System.out.printf("NUMBER OF RESERVATIONS FOR TODAY: %d\n", reservationsToday);
    }

    public static void clientReport() {
        System.out.println("302 - Client Report ====================================================================");
        Client client = InputMethods.getValidClientById();

        System.out.println("\nCLIENT DETAILS =========================================================================");
        System.out.printf("CLIENT: %d - %s\n", client.getId(), client.getName());
        System.out.printf("PHONE NUMBER: %s\n", client.getPhone());

        System.out.println("RESERVATIONS ===========================================================================");

        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT * from reservation WHERE client_id="+client.getId()+";");
            boolean hasReservation = false;


            while (rs.next()) {
                System.out.printf("RESERVATION #%d\n", rs.getInt("id"));
                System.out.printf("ROOM: %d\n", rs.getInt("room_id"));
                System.out.printf("GUESTS: %d\n", rs.getInt("guests"));
                System.out.printf("EXPECTED CHECK-IN DATE: %s\n",
                        new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("expected_checkin_date")));
                System.out.printf("EXPECTED CHECK-OUT DATE: %s\n\n",
                        new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("expected_checkout_date")));
                hasReservation = true;
            }

            if (!hasReservation) {
                System.out.println("This client has no reservations.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("OCCUPATIONS ============================================================================");

        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT * from occupation WHERE client_id="+client.getId()+";");
            boolean hasOccupations = false;

            while (rs.next()) {
                System.out.printf("OCCUPATION #%d\n", rs.getInt("id"));
                System.out.printf("ROOM: %d\n", rs.getInt("room_id"));
                System.out.printf("GUESTS: %d\n", rs.getInt("guests"));
                System.out.printf("CHECK-IN DATE: %s\n",
                        new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("checkin_date")));

                if (rs.getDate("checkout_date") != null) {
                    System.out.printf("CHECK-OUT DATE: %s\n",
                            new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("checkout_date")));
                    System.out.printf("TOTAL AMOUNT: %.2f\n\n", rs.getDouble("total_amount"));
                } else {
                    System.out.printf("EXPECTED CHECK-OUT DATE: %s\n\n",
                            new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("expected_checkout_date")));
                }

                hasOccupations = true;
            }

            if (!hasOccupations) {
                System.out.println("This client has no occupations.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void roomReport() {
        System.out.println("303 - Room Report ======================================================================");
        Room room = InputMethods.getValidRoomById();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        System.out.println("\nROOM DETAILS ===========================================================================");

        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT name, capacity, daily_rate FROM room \n" +
                    "\tINNER JOIN category ON room.category_id = category.id \n" +
                    "\tWHERE room.id="+room.getId()+";");

            rs.next();
            System.out.printf("ROOM #%d\n", room.getId());
            System.out.printf("CATEGORY: %s\n", rs.getString("name"));
            System.out.printf("CAPACITY: %d\n", rs.getInt("capacity"));
            System.out.printf("DAILY RATE: %.2f\n", rs.getDouble("daily_rate"));

            rs = connection.sql("SELECT * FROM occupation WHERE room_id = "+room.getId()+" \n" +
                    "\tAND checkout_date IS NULL \n" +
                    "\tAND expected_checkout_date > '"+currentDate+"' \n" +
                    "\tAND checkin_date <= '"+currentDate+"';");

            if (rs.next()) {
                System.out.println("STATUS: Occupied");
            } else {
                System.out.println("STATUS: Available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("RESERVATIONS ===========================================================================");

        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT * from reservation WHERE room_id="+room.getId()+";");
            boolean hasReservation = false;

            while (rs.next()) {
                System.out.printf("RESERVATION #%d\n", rs.getInt("id"));
                System.out.printf("CLIENT: %d\n", rs.getInt("client_id"));
                System.out.printf("GUESTS: %d\n", rs.getInt("guests"));
                System.out.printf("EXPECTED CHECK-IN DATE: %s\n",
                        new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("expected_checkin_date")));
                System.out.printf("EXPECTED CHECK-OUT DATE: %s\n\n",
                        new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("expected_checkout_date")));
                hasReservation = true;
            }

            if (!hasReservation) {
                System.out.println("This room has no reservations.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getMonthGuests() {
        try {
            DataConnection connection = DataConnection.getInstance();
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month = localDate.getMonthValue();
            int year = localDate.getYear();

            ResultSet rs = connection.sql("SELECT sum(guests) FROM occupation \n" +
                    "\tWHERE (checkout_date IS NOT NULL \n" +
                    "\tAND DATE_PART('month', checkout_date) = "+month+" AND DATE_PART('year', checkout_date) = "+year+")" +
                    "\tOR DATE_PART('month', checkin_date) = "+month+" AND DATE_PART('year', checkin_date) = "+year+"" +
                    "\tOR DATE_PART('month', expected_checkout_date) = "+month+" AND DATE_PART('year', expected_checkout_date) = "+year+";");

            rs.next();
            return rs.getInt("sum");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static double getMonthRevenue() {
        try {
            DataConnection connection = DataConnection.getInstance();
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month = localDate.getMonthValue();
            int year = localDate.getYear();

            ResultSet rs = connection.sql("SELECT sum(total_amount) FROM occupation \n" +
                    "\tWHERE checkout_date IS NOT NULL \n" +
                    "\tAND DATE_PART('month', checkout_date) = "+month+" AND DATE_PART('year', checkout_date) = "+year+";");

            rs.next();
            return rs.getDouble("sum");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int getOccupiedRooms() {
        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT count(id) FROM occupation WHERE checkout_date IS NULL;");

            rs.next();
            return rs.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int getTotalRooms() {
        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT count(id) FROM room;");

            rs.next();
            return rs.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int getReservationsToday() {
        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT sum(id) FROM reservation WHERE expected_checkin_date = '"+
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"';");

            rs.next();
            return rs.getInt("sum");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

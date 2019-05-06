package utils;

import database.DataConnection;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ReadInput {

    public static Client getValidClientById() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the client id: ");
        int clientId = scanner.nextInt();
        Client client = getClient(clientId);

        while (client == null) {
            System.out.print("Please enter a valid client id: ");
            clientId = scanner.nextInt();
            client = getClient(clientId);
        }

        return client;
    }

    public static Room getValidRoomById() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the room id: ");
        int roomId = scanner.nextInt();
        Room room = getRoom(roomId);

        while (room == null) {
            System.out.print("Please enter a valid room id: ");
            roomId = scanner.nextInt();
            room = getRoom(roomId);
        }

        return room;
    }

    public static Reservation getValidReservationById() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the reservation id or -1 in case you don't have a reservation: ");
        int reservationId = scanner.nextInt();

        if (reservationId == -1) {
            return null;
        } else {
            Reservation reservation = getReservation(reservationId);

            while (reservation == null) {
                System.out.print("Please enter a valid reservation id or -1 in case you don't have a reservation: ");
                reservationId = scanner.nextInt();

                if (reservationId == -1) {
                    return null;
                }

                reservation = getReservation(reservationId);
            }

            return reservation;
        }
    }

    public static Occupation getValidOccupationByRoom(int roomId) {
        try {
            DataConnection connection = DataConnection.getInstance();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            ResultSet rs = connection.sql("SELECT * FROM occupation WHERE room_id = "+roomId+" \n" +
                    "\tAND checkout_date IS NULL \n" +
                    "\tAND expected_checkout_date >= '"+currentDate+"' \n" +
                    "\tAND checkin_date < '"+currentDate+"';");
            if (!rs.next()) {
                System.out.println("There is not occupations to be checked out at this time.");
            } else {
                Occupation occupation = new Occupation(rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getInt("room_id"),
                        rs.getInt("guests"),
                        rs.getDate("checkin_date"),
                        rs.getDate("expected_checkout_date"),
                        null, 0);
                return occupation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getValidDate(String instructionMessage, String retryMessage, String errorMessage) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(instructionMessage);
        String line = scanner.nextLine();
        Date date = getDate(line, errorMessage);

        while (date == null) {
            System.out.print(retryMessage);
            line = scanner.nextLine();
            date = getDate(line, errorMessage);
        }

        return date;
    }

    public static int getValidGuests(String instructionMessage, String retryMessage) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(instructionMessage);
        int guests = scanner.nextInt();

        while (guests < 1) {
            System.out.print(retryMessage);
            guests = scanner.nextInt();
        }

        return guests;
    }

    private static Client getClient(int clientId) {
        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT * from client WHERE id = " +clientId+ ";");
            if (!rs.next()) {
                System.out.println("There are no clients with id " + clientId);
            } else {
                Client client = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("phone"));
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Room getRoom(int roomId) {
        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT * from room WHERE id = " +roomId+ ";");
            if (!rs.next()) {
                System.out.println("There are no rooms with id " + roomId);
            } else {
                Room room = new Room(rs.getInt("id"), rs.getInt("category_id"));
                return room;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Reservation getReservation(int reservationId) {
        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("SELECT * from reservation WHERE id = " +reservationId+ ";");
            if (!rs.next()) {
                System.out.println("There are no reservations with id " + reservationId);
            } else {
                Reservation reservation = new Reservation(rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getInt("room_id"),
                        rs.getInt("guests"),
                        rs.getDate("expected_checkin_date"),
                        rs.getDate("expected_checkout_date"));
                return reservation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Date getDate(String line, String errorMessage) {
        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(line);
            return date;
        } catch (ParseException e) {
            System.out.println(errorMessage);
        }
        return null;
    }
}

package controllers;

import database.DataConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ClientManagement {

    public static void createClient() {
        System.out.println("101 - Create new client ===============================================================");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the client name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the client phone number: ");
        String phoneNumber = scanner.nextLine();

        try {
            DataConnection connection = DataConnection.getInstance();
            ResultSet rs = connection.sql("INSERT INTO client (name, phone) VALUES ('"+name+"', '"+phoneNumber+"') RETURNING id;");

            rs.next();
            System.out.println("\nOPERATION WAS COMPLETED SUCCESSFULLY ==================================================");
            System.out.printf("Client %s inserted with id %d\n", name, rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

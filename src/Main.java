import controllers.ClientManagement;
import controllers.Reports;
import controllers.OperationsManagement;
import database.DataConnection;

import java.util.Arrays;
import java.util.Scanner;


public class Main {

    private static final int CREATE_CLIENT = 101;
    private static final int MAKE_RESERVATION = 201;
    private static final int CHECKIN = 202;
    private static final int CHECKOUT = 203;
    private static final int HOTEL_REPORT = 301;
    private static final int CLIENT_REPORT = 302;
    private static final int ROOM_REPORT = 303;
    private static final int EXIT = 401;
    private static final int[] OPTIONS = {CREATE_CLIENT, MAKE_RESERVATION, CHECKIN, CHECKOUT, HOTEL_REPORT, CLIENT_REPORT, ROOM_REPORT, EXIT};

    public static void main(String[] args) {
        setupDatabase();
        clearScreen();
        welcome();
        menu();

        int option = readOption();

        while (option != EXIT) {
            clearScreen();

            switch (option) {
                case CREATE_CLIENT:
                    ClientManagement.createClient();
                    break;
                case MAKE_RESERVATION:
                    OperationsManagement.makeReservation();
                    break;
                case CHECKIN:
                    OperationsManagement.checkIn();
                    break;
                case CHECKOUT:
                    OperationsManagement.checkOut();
                    break;
                case HOTEL_REPORT:
                    Reports.hotelReport();
                    break;
                case CLIENT_REPORT:
                    Reports.clientReport();
                    break;
                case ROOM_REPORT:
                    Reports.roomReport();
                    break;
                case EXIT:
                    break;
                default:
                    break;
            }

            waitKey();
            clearScreen();
            menu();
            option = readOption();
        }

        System.out.println("Thank you for choosing a Umbrella Corporation product.");
    }

    private static void setupDatabase() {
        System.out.println("Setting up database. Please wait...");
        DataConnection.getInstance();
    }

    private static void clearScreen() {
        System.out.println("\n========================================================================================\n");
    }

    private static void welcome() {
        System.out.println("Welcome to T-Hotel by Umbrella Corporation");
        System.out.println("========================================================================================");
        System.out.print("\n");
    }

    private static void menu() {
        System.out.println("1) Client management ===================================================================");
        System.out.println("101 - Create new client");
        System.out.print("\n");

        System.out.println("2) Operations management ===============================================================");
        System.out.println("201 - Make a reservation");
        System.out.println("202 - Check-in");
        System.out.println("203 - Check-out");
        System.out.print("\n");

        System.out.println("3) Reports =============================================================================");
        System.out.println("301 - Generate hotel report");
        System.out.println("302 - Generate client report");
        System.out.println("303 - Generate room report");
        System.out.print("\n");

        System.out.println("4) Settings ============================================================================");
        System.out.println("401 - Exit");
        System.out.print("\n");
    }

    private static int readOption() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter one of the options above followed by <enter>: ");


        while(true) {
            int option = scanner.nextInt();

            if (Arrays.stream(OPTIONS).anyMatch(i -> i == option)) {
                return option;
            } else {
                System.out.println("The option you entered is invalid.");
                System.out.print("Enter one of the options above followed by <enter>: ");
            }
        }
    }

    private static void waitKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPress any key to go back to the menu... ");
        scanner.nextLine();
    }
}

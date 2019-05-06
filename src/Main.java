import controllers.AdministrativeManagement;
import controllers.OperationsManagement;
import database.DataConnection;

import java.util.Arrays;
import java.util.Scanner;


public class Main {

    private static final int MAKE_RESERVATION = 101;
    private static final int CHECKIN = 102;
    private static final int CHECKOUT = 103;
    private static final int HOTEL_REPORT = 201;
    private static final int CLIENT_REPORT = 202;
    private static final int ROOM_REPORT = 203;
    private static final int EXIT = 204;
    private static final int CREATE_CATEGORY = 301;
    private static final int UPDATE_CATEGORY = 302;
    private static final int DELETE_CATEGORY = 303;
    private static final int RETRIEVE_CATEGORY = 304;
    private static final int CREATE_CLIENT = 401;
    private static final int UPDATE_CLIENT = 402;
    private static final int DELETE_CLIENT = 403;
    private static final int RETRIEVE_CLIENT = 404;
    private static final int CREATE_ROOM = 501;
    private static final int UPDATE_ROOM = 502;
    private static final int DELETE_ROOM = 503;
    private static final int RETRIEVE_ROOM  = 504;
    private static final int[] OPTIONS = {MAKE_RESERVATION, CHECKIN, CHECKOUT,
            HOTEL_REPORT, CLIENT_REPORT, ROOM_REPORT, EXIT,
            CREATE_CATEGORY, UPDATE_CATEGORY, DELETE_CATEGORY, RETRIEVE_CATEGORY,
            CREATE_CLIENT, UPDATE_CLIENT, DELETE_CLIENT, RETRIEVE_CLIENT,
            CREATE_ROOM, UPDATE_ROOM, DELETE_ROOM, RETRIEVE_ROOM};

    public static void main(String[] args) {
        setupDatabse();
        clearScreen();
        welcome();
        menu();

        int option = readOption();

        while (option != EXIT) {
            clearScreen();

            OperationsManagement operationsManagement = new OperationsManagement();

            switch (option) {
                case MAKE_RESERVATION:
                    operationsManagement.make();
                    break;
                case CHECKIN:
                    operationsManagement.checkIn();
                    break;
                case CHECKOUT:
                    operationsManagement.checkOut();
                    break;
                case HOTEL_REPORT:
                    break;
                case CLIENT_REPORT:
                    AdministrativeManagement.clientReport();
                    break;
                case ROOM_REPORT:
                    AdministrativeManagement.roomReport();
                    break;
                case EXIT:
                    break;
                case CREATE_CATEGORY:
                    break;
                case UPDATE_CATEGORY:
                    break;
                case DELETE_CATEGORY:
                    break;
                case RETRIEVE_CATEGORY:
                    break;
                case CREATE_CLIENT:
                    break;
                case UPDATE_CLIENT:
                    break;
                case DELETE_CLIENT:
                    break;
                case CREATE_ROOM:
                    break;
                case UPDATE_ROOM:
                    break;
                case DELETE_ROOM:
                    break;
                case RETRIEVE_ROOM:
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

    private static void setupDatabse() {
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
        System.out.println("1) Operations management ===============================================================");
        System.out.println("101 - Make a reservation");
        System.out.println("102 - Check-in");
        System.out.println("103 - Check-out");
        System.out.print("\n");

        System.out.println("2) Administrative management ===========================================================");
        System.out.println("201 - Generate hotel report");
        System.out.println("202 - Generate client report");
        System.out.println("203 - Generate room report");
        System.out.println("204 - Exit");
        System.out.print("\n");

        System.out.println("3) Category management =================================================================");
        System.out.println("301 - Create new category");
        System.out.println("302 - Update existing category");
        System.out.println("303 - Delete existing category");
        System.out.println("304 - Consult category information");
        System.out.print("\n");

        System.out.println("4) Client management ===================================================================");
        System.out.println("401 - Create new client");
        System.out.println("402 - Update client information");
        System.out.println("403 - Delete existing client");
        System.out.println("404 - Consult client information");
        System.out.print("\n");

        System.out.println("5) Room management =====================================================================");
        System.out.println("501 - Create new room");
        System.out.println("502 - Update room information");
        System.out.println("503 - Delete existing room");
        System.out.println("504 - Consult room information");
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
        System.out.print("Press any key to go back to the menu... ");
        scanner.nextLine();
    }
}

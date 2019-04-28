package database;

import java.io.*;
import java.util.ArrayList;

public class DataConnection {

    private static DataConnection instance;

    private enum DB_OBJECT {ROOM, CATEGORY, CLIENT, OCCUPATION, RESERVATION}

    private static final String DB = "db";
    private static final String HOTEL = "db/hotel.thtl";
    private static final String ROOMS = "db/rooms.thtl";
    private static final String CATEGORIES = "db/categories.thtl";
    private static final String CLIENTS = "db/clients.thtl";
    private static final String RESERVATIONS = "db/reservations.thtl";
    private static final String OCCUPATIONS = "db/occupations.thtl";

    private static final File categoriesFile = new File(CATEGORIES);
    private static final File roomsFile = new File(ROOMS);
    private static final File clientsFile = new File(CLIENTS);
    private static final File occupationsFile = new File(OCCUPATIONS);
    private static final File reservationsFile = new File(RESERVATIONS);

    private static final ArrayList<CategoryObject> categories = new ArrayList<>();
    private static final ArrayList<RoomObject> rooms = new ArrayList<>();
    private static final ArrayList<ClientObject> clients = new ArrayList<>();
    private static final ArrayList<OccupationObject> occupations = new ArrayList<>();
    private static final ArrayList<ReservationObject> reservations = new ArrayList<>();

    private DataConnection() {
        try {
            new File("db").mkdirs();
            new File("db/hotel.thtl").createNewFile();
            categoriesFile.createNewFile();
            roomsFile.createNewFile();
            clientsFile.createNewFile();
            occupationsFile.createNewFile();
            reservationsFile.createNewFile();

            loadDatabaseObject(DB_OBJECT.CATEGORY);
            loadDatabaseObject(DB_OBJECT.ROOM);
            loadDatabaseObject(DB_OBJECT.CLIENT);
            loadDatabaseObject(DB_OBJECT.OCCUPATION);
            loadDatabaseObject(DB_OBJECT.RESERVATION);

            System.out.println(categories.toString());
            System.out.println(rooms.toString());
            System.out.println(clients.toString());
            System.out.println(occupations.toString());
            System.out.println(reservations.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataConnection getInstance() {
        if (instance == null) {
            instance = new DataConnection();
        }
        return instance;
    }

    private void loadDatabaseObject(DB_OBJECT object) {
        try {
            String line;
            BufferedReader br;

            switch (object) {
                case ROOM:
                    br = new BufferedReader(new FileReader(roomsFile));

                    while ((line = br.readLine()) != null) {
                        rooms.add(new RoomObject(line));
                    }

                    break;
                case CATEGORY:
                    br = new BufferedReader(new FileReader(categoriesFile));

                    while ((line = br.readLine()) != null) {
                        categories.add(new CategoryObject(line));
                    }

                    break;
                case CLIENT:
                    br = new BufferedReader(new FileReader(clientsFile));

                    while ((line = br.readLine()) != null) {
                        clients.add(new ClientObject(line));
                    }

                    break;
                case OCCUPATION:
                    br = new BufferedReader(new FileReader(occupationsFile));

                    while ((line = br.readLine()) != null) {
                        occupations.add(new OccupationObject(line));
                    }

                    break;
                case RESERVATION:
                    br = new BufferedReader(new FileReader(reservationsFile));

                    while ((line = br.readLine()) != null) {
                        reservations.add(new ReservationObject(line));
                    }

                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

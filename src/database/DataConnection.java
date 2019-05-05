package database;

import database.objects.*;
import model.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

public class DataConnection {

    private static DataConnection instance;

    public enum DB_OBJECT {CATEGORY, CLIENT, OCCUPATION, RESERVATION, ROOM}
    public enum DB_COMP {EQ, LT, MT, LE, ME}

    private static final String PATH_DB = "db";
    private static final String PATH_ROOMS = "db/rooms.thtl";
    private static final String PATH_CATEGORIES = "db/categories.thtl";
    private static final String PATH_CLIENTS = "db/clients.thtl";
    private static final String PATH_RESERVATIONS = "db/reservations.thtl";
    private static final String PATH_OCCUPATIONS = "db/occupations.thtl";

    private static final File dbFolder = new File(PATH_DB);
    private static final File categoriesFile = new File(PATH_CATEGORIES);
    private static final File roomsFile = new File(PATH_ROOMS);
    private static final File clientsFile = new File(PATH_CLIENTS);
    private static final File occupationsFile = new File(PATH_OCCUPATIONS);
    private static final File reservationsFile = new File(PATH_RESERVATIONS);

    private static final ArrayList<DBCategory> categories = new ArrayList<>();
    private static final ArrayList<DBRoom> rooms = new ArrayList<>();
    private static final ArrayList<DBClient> clients = new ArrayList<>();
    private static final ArrayList<DBOccupation> occupations = new ArrayList<>();
    private static final ArrayList<DBReservation> reservations = new ArrayList<>();

    private DataConnection() {
        setup();
        load();
    }

    public static DataConnection getInstance() {
        if (instance == null) {
            instance = new DataConnection();
        }
        return instance;
    }

    public boolean insert(Object databaseObject) {
        if (isValidDatabaseObject(databaseObject)) {
            if (databaseObject instanceof Category) {
                Category category = (Category) databaseObject;
                DBCategory dbCategory = new DBCategory(categories.size() + 1, category.getName(), category.getDailyRate(), category.getCapacity(), category.isExtraBed(), category.getExtraBedFee());
                categories.add(dbCategory);
                writeDatabaseObject(categoriesFile, dbCategory);
                return true;
            } else if (databaseObject instanceof Client) {
                Client client = (Client) databaseObject;
                DBClient dbClient = new DBClient(clients.size() + 101, client.getName(), client.getPhoneNumber());
                clients.add(dbClient);
                writeDatabaseObject(clientsFile, dbClient);
                return true;
            } else if (databaseObject instanceof Occupation) {
                Occupation occupation = (Occupation)databaseObject;
                DBOccupation dbOccupation = new DBOccupation(occupations.size() + 1, occupation.getIdClient(), occupation.getIdRoom(), occupation.getGuests(), occupation.getCheckinDate(), occupation.getExpectedCheckoutDate(), occupation.getCheckoutDate());
                occupations.add(dbOccupation);
                writeDatabaseObject(occupationsFile, dbOccupation);
                return true;
            } else if (databaseObject instanceof Reservation) {
                Reservation reservation = (Reservation)databaseObject;
                DBReservation dbReservation = new DBReservation(reservations.size() + 1, reservation.getIdClient(), reservation.getIdRoom(), reservation.getGuests(), reservation.getExpectedCheckinDate(), reservation.getExpectedCheckoutDate());
                reservations.add(dbReservation);
                writeDatabaseObject(reservationsFile, dbReservation);
                return true;
            } else if (databaseObject instanceof Room) {
                Room room = (Room)databaseObject;
                DBRoom dbRoom = new DBRoom(rooms.size() + 1, room.getIdCategory());
                rooms.add(dbRoom);
                writeDatabaseObject(roomsFile, dbRoom);
            }
        }
        return false;
    }

    public boolean update(Object databaseObject) {
        if (isValidDatabaseObject(databaseObject)) {
            if (databaseObject instanceof Category) {
                Category category = (Category)databaseObject;

                for (DBCategory dbCategory: categories) {
                    if ((category.getId() == dbCategory.getId()) && !dbCategory.isDeleted()) {
                        dbCategory.setName(category.getName());
                        dbCategory.setDailyRate(category.getDailyRate());
                        dbCategory.setCapacity(category.getCapacity());
                        dbCategory.setExtraBed(category.isExtraBed());
                        dbCategory.setExtraBedFee(category.getExtraBedFee());
                        writeDatabaseObjectFile(DB_OBJECT.CATEGORY);
                        break;
                    }
                }
            }
        }
        return false;
    }

    public boolean delete(Object databaseObject) {
        return false;
    }

    public Object select(DB_OBJECT databaseObject, String key, String value, DB_COMP comp) {
        switch (databaseObject) {
            case CATEGORY:
                return selectCategory(key, value, comp);
            case CLIENT:
                return selectClient(key, value, comp);
            case OCCUPATION:
                return selectOccupation(key, value, comp);
            case RESERVATION:
                return selectReservation(key, value, comp);
            case ROOM:
                return selectRoom(key, value, comp);
            default:
                return null;
        }
    }

    private Object selectCategory(String key, String value, DB_COMP comp) {
        ArrayList<Category> categoryArray = new ArrayList<>();
        switch (key) {
            case "":
                for (DBCategory category: categories) {
                    if (!category.isDeleted()) {
                        categoryArray.add(new Category(category.getId(), category.getName(), category.getDailyRate(), category.getCapacity(), category.isExtraBed(), category.getExtraBedFee()));
                    }
                }
                return categoryArray;
            case "id":
                for (DBCategory category: categories) {
                    if ((category.getId() == Integer.parseInt(value)) && !category.isDeleted()) {
                        return new Category(category.getId(), category.getName(), category.getDailyRate(), category.getCapacity(), category.isExtraBed(), category.getExtraBedFee());
                    }
                }
                return null;
            case "name":
                for (DBCategory category: categories) {
                    if ((category.getName().equals(value)) && !category.isDeleted()) {
                        return new Category(category.getId(), category.getName(), category.getDailyRate(), category.getCapacity(), category.isExtraBed(), category.getExtraBedFee());
                    }
                }
                return null;
            case "capacity":
                for (DBCategory category: categories) {
                    if ((category.getCapacity() == Double.parseDouble(value)) &&!category.isDeleted()) {
                        categoryArray.add(new Category(category.getId(), category.getName(), category.getDailyRate(), category.getCapacity(), category.isExtraBed(), category.getExtraBedFee()));
                    }
                }
                return categoryArray;
            default:
                return null;
        }
    }

    private Object selectClient(String key, String value, DB_COMP comp) {
        ArrayList<Client> clientArray = new ArrayList<>();
        switch (key) {
            case "":
                for (DBClient client: clients) {
                    if (!client.isDeleted()) {
                        clientArray.add(new Client(client.getId(), client.getName(), client.getPhoneNumber()));
                    }
                }
                return clientArray;
            case "id":
                for (DBClient client: clients) {
                    if ((client.getId() == Integer.parseInt(value)) && !client.isDeleted()) {
                        return new Client(client.getId(), client.getName(), client.getPhoneNumber());
                    }
                }
                return null;
            case "name":
                for (DBClient client: clients) {
                    if ((client.getName().equals(value)) && !client.isDeleted()) {
                        clientArray.add(new Client(client.getId(), client.getName(), client.getPhoneNumber()));
                    }
                }
                return clientArray;
            default:
                return null;
        }
    }

    private Object selectOccupation(String key, String value, DB_COMP comp) {
        return null;
    }

    private Object selectReservation(String key, String value, DB_COMP comp) {
        return null;
    }

    private Object selectRoom(String key, String value, DB_COMP comp) {
        ArrayList<Room> roomArray = new ArrayList<>();
        switch (key) {
            case "":
                for (DBRoom room: rooms) {
                    if (!room.isDeleted()) {
                        roomArray.add(new Room(room.getId(), room.getIdCategory()));
                    }
                }
                return roomArray;
            case "id":
                for (DBRoom room: rooms) {
                    if ((room.getId() == Integer.parseInt(value)) && !room.isDeleted()) {
                        return new Room(room.getId(), room.getIdCategory());
                    }
                }
                return null;
            case "idCategory":
                for (DBRoom room: rooms) {
                    if ((room.getIdCategory() == Integer.parseInt(value)) && !room.isDeleted()) {
                        roomArray.add(new Room(room.getId(), room.getIdCategory()));
                    }
                }
                return roomArray;
        }
        return null;
    }

    private void setup() {
        try {
            dbFolder.mkdirs();
            categoriesFile.createNewFile();
            roomsFile.createNewFile();
            clientsFile.createNewFile();
            occupationsFile.createNewFile();
            reservationsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        loadDatabaseObject(DB_OBJECT.CATEGORY);
        loadDatabaseObject(DB_OBJECT.ROOM);
        loadDatabaseObject(DB_OBJECT.CLIENT);
        loadDatabaseObject(DB_OBJECT.OCCUPATION);
        loadDatabaseObject(DB_OBJECT.RESERVATION);
    }

    private void writeDatabaseObject(File file, @NotNull Object databaseObject) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.newLine();
            writer.write(databaseObject.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearDatabaseObjectFile(File file) {
        try {
            new FileWriter(file, false).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDatabaseObjectFile(DB_OBJECT databaseObject) {
        switch (databaseObject) {
            case CATEGORY:
                clearDatabaseObjectFile(categoriesFile);
                for (DBCategory category: categories) {
                    writeDatabaseObject(categoriesFile, category);
                }
                break;
            case CLIENT:
                clearDatabaseObjectFile(clientsFile);
                for (DBClient client: clients) {
                    writeDatabaseObject(clientsFile, client);
                }
                break;
            case OCCUPATION:
                clearDatabaseObjectFile(occupationsFile);
                for (DBOccupation occupation: occupations) {
                    writeDatabaseObject(occupationsFile, occupation);
                }
                break;
            case RESERVATION:
                clearDatabaseObjectFile(reservationsFile);
                for (DBReservation reservation: reservations) {
                    writeDatabaseObject(reservationsFile, reservation);
                }
                break;
            case ROOM:
                clearDatabaseObjectFile(roomsFile);
                for (DBRoom room: rooms) {
                    writeDatabaseObject(roomsFile, room);
                }
                break;
            default:
                break;
        }
    }

    private boolean isValidDatabaseObject(@NotNull Object databaseObject) {
        if (databaseObject instanceof Category) {
            return true;
        } else if (databaseObject instanceof Client) {
            return true;
        } else if (databaseObject instanceof Occupation) {
            Occupation occupation = (Occupation)databaseObject;
            return (select(DB_OBJECT.CLIENT, "id", Integer.toString(occupation.getIdClient()), DB_COMP.EQ) != null) && (select(DB_OBJECT.ROOM, "id", Integer.toString(occupation.getIdRoom()), DB_COMP.EQ) != null);
        } else if (databaseObject instanceof Reservation) {
            Reservation reservation = (Reservation)databaseObject;
            return (select(DB_OBJECT.CLIENT, "id", Integer.toString(reservation.getIdClient()), DB_COMP.EQ) != null) && (select(DB_OBJECT.ROOM, "id", Integer.toString(reservation.getIdRoom()), DB_COMP.EQ) != null);
        } else if (databaseObject instanceof Room) {
            Room room = (Room)databaseObject;
            return (select(DB_OBJECT.CATEGORY, "id", Integer.toString(room.getIdCategory()), DB_COMP.EQ) != null);
        } else {
            return false;
        }
    }

    private void loadDatabaseObject(@NotNull DB_OBJECT object) {
        try {
            String line;
            BufferedReader br;

            switch (object) {
                case CATEGORY:
                    br = new BufferedReader(new FileReader(categoriesFile));

                    while ((line = br.readLine()) != null) {
                        categories.add(new DBCategory(line));
                    }

                    break;
                case CLIENT:
                    br = new BufferedReader(new FileReader(clientsFile));

                    while ((line = br.readLine()) != null) {
                        clients.add(new DBClient(line));
                    }

                    break;
                case OCCUPATION:
                    br = new BufferedReader(new FileReader(occupationsFile));

                    while ((line = br.readLine()) != null) {
                        occupations.add(new DBOccupation(line));
                    }

                    break;
                case RESERVATION:
                    br = new BufferedReader(new FileReader(reservationsFile));

                    while ((line = br.readLine()) != null) {
                        reservations.add(new DBReservation(line));
                    }

                    break;
                case ROOM:
                    br = new BufferedReader(new FileReader(roomsFile));

                    while ((line = br.readLine()) != null) {
                        rooms.add(new DBRoom(line));
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

import database.DataConnection;
import model.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
////        CategoryService cat = new CategoryService(1, "Standard", 268.00, 2, true, 0.3);
////        System.out.print(cat);
//   DataConnection connection = DataConnection.getInstance();
////        Reservation r = new Reservation(102, 5, 3, new SimpleDateFormat("dd/MM/yyyy").parse("28/4/2019"), new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2019"));
////        Occupation occ = new Occupation(101, 1, 101, new SimpleDateFormat("dd/MM/yyyy").parse("28/4/2019"), new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2019"));
////        connection.insert(r);
////        connection.insert(occ);
//        System.out.println(connection.select(DataConnection.DB_OBJECT.CATEGORY, "name", "Suite", DataConnection.DB_COMP.EQ));
//        Category c = (Category)connection.select(DataConnection.DB_OBJECT.CATEGORY, "name", "Suite", DataConnection.DB_COMP.EQ);
//        c.setDailyRate(766);
//        connection.update(c);



//        System.out.println(occ);


        String url = "jdbc:postgresql://localhost:5433/thotel";
        String user = "postgres";
        String password = "postgres";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM \"Category\" WHERE \"CategoryID\" = 1;")) {

            if (rs.next()) {
                System.out.println(rs.getString("Name"));
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Main.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
}

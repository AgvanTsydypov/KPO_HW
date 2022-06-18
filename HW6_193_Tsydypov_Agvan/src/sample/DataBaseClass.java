import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

//TODO: set working directory to DERBY folder before starting this application in IDEA
//      to have the database and derby.log located there...

public class DataBaseClass {
    static public ObservableList<Person> filteredData = FXCollections.observableArrayList();
    private static DataBaseClass instance;
    private DataBaseClass(){};
    public static DataBaseClass getInstance()
    {
        if(instance == null)
            instance = new DataBaseClass();
        return instance;
    }

    //   ## DEFINE VARIABLES SECTION ##
    // define the driver to use
    String driver = "org.apache.derby.jdbc.EmbeddedDriver";

    Connection conn = null;
    Statement s;
    PreparedStatement psInsert;
    //ResultSet myWishes;
    //String printLine = "  __________________________________________________";
    String createString = "CREATE TABLE PHONE_BOOK( "
            + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
            + "NAME VARCHAR(255) NOT NULL, "
            + "LASTNAME VARCHAR(255) NOT NULL, "
            + "SURNAME VARCHAR(255), "
            + "MOBILEH VARCHAR(255), "
            + "MOBILEW VARCHAR(255), "
            + "ADRESS VARCHAR(255), "
            + "BDAY DATE, "
            + "COMMENT VARCHAR(255)) ";
    String answer;

    public ArrayList<Integer> search(String str) {
        ArrayList<Integer> result = new ArrayList<>();

        String sql = "SELECT ID FROM PHONE_BOOK WHERE NAME LIKE '%" + str + "%' OR LASTNAME LIKE '%" + str + "%' OR SURNAME LIKE '%" + str + "%'";
        try {
            psInsert = conn.prepareStatement(sql);
            var res = psInsert.executeQuery();
            while (res.next()) {
                result.add(res.getInt("Id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    private static void setInfo(Person person, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getLastname());
        preparedStatement.setString(3, person.getSurname());
        preparedStatement.setString(4, person.getMobileH());
        preparedStatement.setString(5, person.getMobileW());
        preparedStatement.setString(6, person.getAdress());
        var date = person.getBday() != null ? Date.valueOf(person.getBday()) : null;
        preparedStatement.setDate(7, date);
        preparedStatement.setString(8, person.getComment());
        System.out.println("Saved in BD");
    }

    public boolean redactPersonBD(Person person) {
        String sql = "UPDATE PHONE_BOOK SET NAME = ?, LASTNAME = ?, SURNAME = ?, MOBILEH = ?, MOBILEW = ?, ADRESS = ?, BDAY = ?, COMMENT = ? WHERE ID = ?";
        try {
            psInsert = conn.prepareStatement(sql);
            setInfo(person, psInsert);
            psInsert.setInt(9, person.getId());
            return psInsert.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean add(Person person) {
        try {
            psInsert = conn.prepareStatement("INSERT INTO PHONE_BOOK(NAME, LASTNAME, SURNAME, MOBILEH, MOBILEW, ADRESS, BDAY, COMMENT) VALUES (?,?,?,?,?,?,?,?)");
            setInfo(person, psInsert);
            psInsert.executeUpdate();
            psInsert = conn.prepareStatement("SELECT ID FROM PHONE_BOOK WHERE NAME = (?) AND LASTNAME = (?) AND SURNAME=(?)");
            psInsert.setString(1, person.getName());
            psInsert.setString(2, person.getLastname());
            psInsert.setString(3, person.getSurname());
            var res = psInsert.executeQuery();
            res.next();
            person.setId(res.getInt("Id"));
            psInsert.close();
        } catch (Exception e) {
            System.out.println("Error BD saving");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Person> readDB() {
        ArrayList<Person> result = new ArrayList<>();
        String sql = "SELECT * FROM PHONE_BOOK";
        try {
            var res = s.executeQuery(sql);
            while (res.next()) {
                var bday = res.getDate("BDAY") == null ? null : res.getDate("BDAY").toLocalDate();
                Person contact = new Person(res.getString("NAME"),
                        res.getString("LASTNAME"),
                        res.getString("SURNAME"),
                        res.getString("MOBILEW"),
                        res.getString("MOBILEH"),
                        res.getString("ADRESS"),
                        bday,
                        res.getString("COMMENT"));
                result.add(contact);
                contact.setId(res.getInt("ID"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public boolean remove(int id)
    {
        try {
            String sql = "DELETE FROM PHONE_BOOK WHERE ID = (?)";
            psInsert = conn.prepareStatement(sql);
            psInsert.setInt(1, id);
            psInsert.execute();
        } catch (Exception e) {
            System.out.println("Error BD removing");
            return false;
        }
        return true;
    }

    public void InitDB(String conUrl, String dbName)
    {
        //  JDBC code sections
        //  Beginning of Primary DB access section
        //   ## BOOT DATABASE SECTION ##
        try {
            // Create (if needed) and connect to the database.
            // The driver is loaded automatically.
            conn = DriverManager.getConnection(conUrl);
            System.out.println("Connected to database " + dbName);

            //   ## INITIAL SQL SECTION ##
            //   Create a statement to issue simple commands.
            s = conn.createStatement();
            // Call utility method to check if table exists.
            //      Create the table if needed
            if (!WwdUtils.wwdChk4Table(conn)) {
                System.out.println(" . . . . creating table WISH_LIST");
                s.execute(createString);
            }
        } catch (Throwable e) {
            /*       Catch all exceptions and pass them to
             *       the Throwable.printStackTrace method  */
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
    }

    public void StopDB() {
        try {
            // Release the resources (clean up )
            if (psInsert != null)
                psInsert.close();
            s.close();
            conn.close();
            System.out.println("Closed connection");

            //   ## DATABASE SHUTDOWN SECTION ##
            /*** In embedded mode, an application should shut down Derby.
             Shutdown throws the XJ015 exception to confirm success. ***/
            if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
                boolean gotSQLExc = false;
                try {
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");
                } catch (SQLException se) {
                    if (se.getSQLState().equals("XJ015")) {
                        gotSQLExc = true;
                    }
                }
                if (!gotSQLExc) {
                    System.out.println("Database did not shut down normally");
                } else {
                    System.out.println("Database shut down normally");
                }
            }
            //  Beginning of the primary catch block: prints stack trace
        } catch (Throwable e) {
            /*       Catch all exceptions and pass them to
             *       the Throwable.printStackTrace method  */
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
        System.out.println("Getting Started With Derby JDBC program ending.");
    }

}
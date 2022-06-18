import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataBaseClassTest {

    static String path = "saveFile.txt";
    static String dbName = "PhoneBookDBTest";
    static String connectionURL = "jdbc:derby:" + dbName + ";create=true";
    public static DataBaseClass db;

    @BeforeAll
    static void setUp() {
        db = DataBaseClass.getInstance();
        db.InitDB(connectionURL, dbName);
    }

    @AfterAll
    static void tearDown() {
        db.StopDB();
    }

    @Test
    void db_AddCheck() {
        Person person = new Person("АгванAdd", "Цыдыпов", "Валерьевич", "89852703500", "89852703500", "moscow russia", LocalDate.of(2001,5,16), "Yeppi!");
        db.add(person);
        assertEquals(db.search("Агван").get(0), person.getId());
        db.remove(person.getId());
    }
    @Test
    void db_RedactCheck() {
        Person person = new Person("АгванRedact", "Цыдыпов", "Валерьевич", "89852703500", "89852703500", "moscow russia", LocalDate.of(2001,5,16), "Yeppi!");
        db.add(person);
        person.setName("CheckName");
        db.redactPersonBD(person);
        assertEquals(db.search("Агван").size(), 0);
        assertEquals(db.search("CheckName").get(0), person.getId());
        db.remove(person.getId());
    }

    @Test
    void db_RemoveCheck()
    {
        Person person = new Person("АгванRemove", "Цыдыпов", "Валерьевич", "89852703500", "89852703500", "moscow russia", LocalDate.of(2001,5,16), "Yeppi!");
        db.add(person);
        db.remove(person.getId());
        assertEquals(db.search("Агван").size(), 0);
    }

    @Test
    void db_ReadDbCheck() throws SQLException {
        //Statement statement = db.getConn().createStatement();
//
        //String SQL = "TRUNCATE TABLE PHONE_BOOK";
//
        //statement.executeUpdate(SQL);

        Person person = new Person("АгванReadDb", "Цыдыпов", "Валерьевич", "89852703500", "89852703500", "moscow russia", LocalDate.of(2001,5,16), "Yeppi!");
        db.add(person);
        ArrayList<Person> persons= db.readDB();
        assertEquals(persons.get(0).getId(), person.getId());
        System.out.println(persons.size());
        db.remove(person.getId());
    }
}
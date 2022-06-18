import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaveLogicTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void rightCheckSave() throws IOException, ClassNotFoundException {
        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(new Person("Агван", "Цыдыпов", "Валерьевич", "89852703500", "89852703500", "moscow russia", LocalDate.of(2001,5,16), "Yeppi!"));
        String path = "TestSave";
        SaveLogic.saveFile(path, persons);
        ArrayList<Person> readpersons = ReadLogic.readFile(path);
        readpersons.get(0).getSurname().equals(persons.get(0).getSurname());
        assertEquals(readpersons.get(0).getSurname(), persons.get(0).getSurname());
        assertEquals(readpersons.get(0).getName(), persons.get(0).getName());
        assertEquals(readpersons.get(0).getLastname(), persons.get(0).getLastname());
    }
}
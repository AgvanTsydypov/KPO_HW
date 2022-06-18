import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SearchLogicTest {
    @Test
    void rightCheckTable() {
        Controller.persons.add(new Person("Агван", "Цыдыпов", "Валерьевич", "89852703500", "89852703500", "moscow russia", LocalDate.of(2001,5,16), "Yeppi!"));
        assertEquals(SearchLogic.fillTable("Агв"), Controller.persons);
    }
    @Test
    void wrongCheckTable() {
        Controller.persons.add(new Person("Агван", "Цыдыпов", "Валерьевич", "89852703500", "89852703500", "moscow russia", LocalDate.of(2001,5,16), "Yeppi!"));
        assertNotEquals(SearchLogic.fillTable("ХхХ"), Controller.persons);
    }
}
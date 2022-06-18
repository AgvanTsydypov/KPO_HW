import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class ReadLogic {
    /**
     * считывание
     */
    public static ArrayList<Person> read(String path) throws IOException, ClassNotFoundException {
        ArrayList<Person> arrayList = new ArrayList<Person>();
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream;
        objectInputStream = new ObjectInputStream(fileInputStream);
        arrayList = (ArrayList<Person>)objectInputStream.readObject();
        ObservableList<Person> personsNew = FXCollections.observableArrayList(arrayList);
        Controller.persons.addAll(personsNew);
        return arrayList;
    }
}

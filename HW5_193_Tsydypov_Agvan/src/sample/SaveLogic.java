import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class SaveLogic {
    /**
     * сохранение
     */
    public static void save(String path, ArrayList<Person> arrayList) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(path);
        ObjectOutputStream objectOutputStream = null;
        objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(arrayList);
        objectOutputStream.close();
    }
}

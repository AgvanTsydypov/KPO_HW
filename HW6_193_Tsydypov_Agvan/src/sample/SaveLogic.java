import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class SaveLogic {
    /**
     * сохранение
     */
    public static void saveFile(String path, ArrayList<Person> arrayList) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(path);
        ObjectOutputStream objectOutputStream = null;
        objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(arrayList);
        objectOutputStream.close();
    }
    public static void saveDB(ArrayList<Person> arrayList) {
        DataBaseClass bd = DataBaseClass.getInstance();
        for (int i = 0; i < arrayList.size(); i++) {
            bd.add(arrayList.get(i));
        }
    }
}

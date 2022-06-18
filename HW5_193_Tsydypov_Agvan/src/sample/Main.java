import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {
    String path = "saveFile.txt";

    /**
     * срабатывает при выходе из программы
     */
    @Override
    public void stop(){
        ArrayList<Person> arrayList = new ArrayList<Person>(Controller.persons);
        try {
            SaveLogic.save(path,arrayList);
        } catch (IOException e) {
            System.out.println("Cant save files");
        }
        System.out.println("Saved");
    }

    /**
     * срабатывает при запуске программы
     */
    @Override
    public void init(){
        try {
            ReadLogic.read(path);
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Cant read files");
        }
        System.out.println("Loaded");
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxmlFiles/sample.fxml"));
        primaryStage.setTitle("Телефонная книга");
        primaryStage.setMinWidth(640);
        primaryStage.setMinHeight(400);
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
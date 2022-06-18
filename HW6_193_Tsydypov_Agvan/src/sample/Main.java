import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {
    String path = "saveFile.txt";
    String dbName = "PhoneBookDB";
    String connectionURL = "jdbc:derby:" + dbName + ";create=true";
    public DataBaseClass db;
    /**
     * срабатывает при выходе из программы
     */
    @Override
    public void stop(){
        ArrayList<Person> arrayList = new ArrayList<Person>(Controller.persons);
        try {
            SaveLogic.saveFile(path,arrayList);
        } catch (IOException e) {
            System.out.println("Cant save files");
        }
        System.out.println("Saved in file");
        db.StopDB();
    }

    /**
     * срабатывает при запуске программы
     */
    @Override
    public void init(){
        db = DataBaseClass.getInstance();
        db.InitDB(connectionURL, dbName);
        try {
            //ReadLogic.readFile(path);
            ReadLogic.readDB();
        } catch (Exception e) {
            System.out.println("Cant read files/DB");
        }
        System.out.println("Loaded files/DB");
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
    public static void main(String[] args){
        launch(args);
    }
}
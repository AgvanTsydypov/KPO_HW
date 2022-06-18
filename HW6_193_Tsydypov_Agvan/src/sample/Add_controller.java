import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
//добавление новых контактов
public class Add_controller implements Initializable {

    @FXML
    private DatePicker bday_dataP;

    @FXML
    private Button exit_btn;

    @FXML
    private TextField lastname;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField mobileH;

    @FXML
    private TextField adress;

    @FXML
    private TextField bday;

    @FXML
    private Button add_user_btn;

    @FXML
    private TextArea comment;

    @FXML
    private TextField mobileW;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //выход
        exit_btn.setOnAction(event -> {
            exit_btn.getScene().getWindow().hide();
        });
        //добавление новых контактов и их проверка на свопадение
        add_user_btn.setOnAction(event -> {
            String lastnameS = lastname.getText().trim();
            String surnameS = surname.getText().trim();
            String nameS = name.getText().trim();
            String mobileHS = mobileH.getText().trim();
            String mobileWS = mobileW.getText().trim();
            LocalDate bdayS = bday_dataP.getValue();
            String adressS = adress.getText().trim();
            String commentS = comment.getText().trim();
            Person person = new Person(nameS, lastnameS, surnameS, mobileHS, mobileWS, adressS, bdayS, commentS);
            if(!checkLastName(lastnameS) && !checkName(nameS) && (checkMobiles(mobileHS,mobileWS))) {
                boolean bool = true;
                for (int i = 0; i < Controller.persons.size(); i++) {
                    if(Controller.persons.get(i).getName().equals(person.getName()) && Controller.persons.get(i).getLastname().equals(person.getLastname()) && Controller.persons.get(i).getSurname().equals(person.getSurname()))
                    {
                        bool = false;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Подсказка");
                        alert.setHeaderText(null);
                        alert.setContentText("Контакт с таким ФИО уже существует");
                        alert.showAndWait();
                    }
                }
                if(bool) {
                    loginPerson(person);

                    DataBaseClass db = DataBaseClass.getInstance();
                    db.add(person);

                    exit_btn.getScene().getWindow().hide();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Подсказка");
                alert.setHeaderText(null);
                alert.setContentText("Обнаружен некорректный ввод");
                alert.showAndWait();
            }
        });
    }
    //ппроверка на правильность телефонов
    public boolean checkMobiles(String h, String w)
    {
        boolean home = true;
        boolean work = true;
        int len = 0;
        for (int i = 0; i < h.length() ; i++)
            if(h.charAt(i) >= '0' || h.charAt(i) <= '9')
                len += 1;
        if((h.length() == 11 && len == 11) || (len == 0 && w.length() != 0)){
            mobileH.setStyle("-fx-border-color: null;");
            home =  true;
        }
        else {
            mobileH.setStyle("-fx-border-color: red;");
            home =  false;
        }
        len = 0;
        for (int i = 0; i < w.length() ; i++)
            if(w.charAt(i) >= '0' || w.charAt(i) <= '9')
                len += 1;
        if((w.length() == 11 && len == 11) || (len == 0 && h.length() != 0)){
            mobileW.setStyle("-fx-border-color: null;");
            work =  true;
        }
        else {
            mobileW.setStyle("-fx-border-color: red;");
            work =  false;
        }
        return (work && home);
    }
    //ппроверка на правильность имен
    public boolean checkName(String str)
    {
        if (str.equals("")) {
            name.setStyle("-fx-border-color: red;");
            return true;
        }
        else {
            name.setStyle(null);
            return false;
        }
    }
    //ппроверка на правильность фамилий
    public boolean checkLastName(String str)
    {
        if (str.equals("")) {
            lastname.setStyle("-fx-border-color: red;");
            return true;
        }
        else {
            lastname.setStyle(null);
            return false;
        }
    }
    //добавление персонов
    static private void loginPerson(Person person) {
        Controller.addPerson(person);
    }
}
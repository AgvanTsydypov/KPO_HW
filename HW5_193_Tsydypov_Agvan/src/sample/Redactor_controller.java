import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Redactor_controller implements Initializable {
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

    static public Person redactPerson;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(redactPerson.getName());
        lastname.setText(redactPerson.getLastname());
        surname.setText(redactPerson.getSurname());
        mobileH.setText(redactPerson.getMobileH());
        mobileW.setText(redactPerson.getMobileW());
        comment.setText(redactPerson.getComment());
        bday_dataP.setValue(redactPerson.getBday());
        adress.setText(redactPerson.getAdress());
        String nameSSave = name.getText();
        String surnameSSave = surname.getText();
        String lastnameSSave = lastname.getText();
        exit_btn.setOnAction(event -> {
            exit_btn.getScene().getWindow().hide();
        });
        //редактирование контакта + проверка его ФИО на совпадение
        add_user_btn.setOnAction(event -> {
            String lastnameS = lastname.getText().trim();
            String surnameS = surname.getText().trim();
            String nameS = name.getText().trim();
            String mobileHS = mobileH.getText().trim();
            String mobileWS = mobileW.getText().trim();
            LocalDate bdayS = bday_dataP.getValue();
            String adressS = adress.getText().trim();
            String commentS = comment.getText().trim();
            if(!checkLastName(lastnameS) && !checkName(nameS) && (checkMobiles(mobileHS,mobileWS))) {
                boolean bool = true;
                for (int i = 0; i < Controller.persons.size(); i++) {
                    if(Controller.persons.get(i).getName().equals(nameS) && Controller.persons.get(i).getLastname().equals(lastnameS) && Controller.persons.get(i).getSurname().equals(surnameS))
                    {
                        if(nameSSave.equals(nameS) && lastnameSSave.equals(lastnameS) && surnameS.equals(surnameSSave))
                            bool = true;
                        else {
                            bool = false;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Подсказка");
                            alert.setHeaderText(null);
                            alert.setContentText("Контакт с таким ФИО уже существует");
                            alert.showAndWait();
                        }
                    }
                }
                if(bool) {
                    //Person pers = new Person(nameS,surnameS,lastnameS,mobileHS,mobileWS,adressS,bdayS,commentS);
                    //redactPerson = pers;
                    redactPerson.setName(nameS);
                    redactPerson.setSurname(surnameS);
                    redactPerson.setLastname(lastnameS);
                    redactPerson.setMobileH(mobileHS);
                    redactPerson.setMobileW(mobileWS);
                    redactPerson.setBday(bdayS);
                    redactPerson.setAdress(adressS);
                    redactPerson.setComment(commentS);
                    redactPerson.setMobiles(mobileHS, mobileWS);
                    add_user_btn.getScene().getWindow().hide();
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
}

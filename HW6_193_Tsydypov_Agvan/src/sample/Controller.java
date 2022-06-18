import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private MenuItem import_menuItem;

    @FXML
    private MenuItem export_menuItem;

    @FXML
    private MenuItem spravka_menuitem;

    @FXML
    private Button add_user_btn;

    @FXML
    private TableView<Person> table;

    @FXML
    private TableColumn<Person, String> lastname;

    @FXML
    private TableColumn<Person, String> name;

    @FXML
    private TableColumn<Person, String> surname;

    @FXML
    private TableColumn<Person, String> mobile;

    @FXML
    private TableColumn<Person, String> adress;

    @FXML
    private TableColumn<Person, LocalDate> bday;

    @FXML
    private TableColumn<Person, String> comment;

    @FXML
    private Button redactor_user_btn;

    @FXML
    private Button dlt_btn;

    @FXML
    private MenuItem add_menuItem;

    @FXML
    private MenuItem redact_menuItem;

    @FXML
    private MenuItem delete_menuItem;

    @FXML
    private MenuItem exit_menuItem;

    @FXML
    private TextField search_textField;

    //удаление на кнопку delete
    @FXML
    void keyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.DELETE))
            deleteUserMethod();
    }

    @FXML
    private Button search_btn;

    static public ObservableList<Person> persons = FXCollections.observableArrayList(
            //new Person("Агван", "Цыдыпов", "Валерьевич", "89852703500", "89852703500", "moscow russia", LocalDate.of(2001,5,16), "Yeppi!"),
            //new Person("Иван", "Симонов", "Сергеевич", "89167130079", "89167130079", "midLineOrFeed", LocalDate.of(2001,6,13), "love algosi")
    );

    static public void addPerson(Person person)
    {
        persons.add(person);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        lastname.setCellValueFactory(new PropertyValueFactory<Person, String>("lastname"));
        surname.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
        mobile.setCellValueFactory(new PropertyValueFactory<Person, String>("mobiles"));
        adress.setCellValueFactory(new PropertyValueFactory<Person, String>("adress"));
        bday.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("bday"));
        comment.setCellValueFactory(new PropertyValueFactory<Person, String>("comment"));

        table.setItems(persons);

        table.refresh();

        redactor_user_btn.setOnAction(event -> {
            redactUserMethod();
        });

        add_user_btn.setOnAction(event -> {
            addUserMethod();
        });

        dlt_btn.setOnAction(event -> {
            deleteUserMethod();
        });

        add_menuItem.setOnAction(event -> {
            addUserMethod();
        });

        delete_menuItem.setOnAction(event -> {
            deleteUserMethod();
        });

        redact_menuItem.setOnAction(event -> {
            redactUserMethod();
        });

        exit_menuItem.setOnAction(event -> {
            dlt_btn.getScene().getWindow().hide();
        });

        //справка
        spravka_menuitem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Справка");
            alert.setHeaderText(null);
            alert.setContentText("193_Цыдыпов_Агван\nПлюсы работы:\n1)Добавил удаление на клавишу delete\n2)сделал поиск сразу по ФИО(по свопадениям)\n3)Изменение контактов в поиске");
            alert.showAndWait();
        });

        //экспорт
        export_menuItem.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxmlFiles/export.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                System.out.println("Экспорт не прошел");
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Экспорт файлов");
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            table.refresh();
            String path = "";
            try {
                ArrayList<Person> arrayList = new ArrayList<Person>(persons);
                FileOutputStream outputStream;
                path = ExportController.path;
                SaveLogic.saveFile(path,arrayList);
            } catch (Exception e) {
                try {
                    if (path.equals("")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Предупреждение");
                        alert.setHeaderText(null);
                        alert.setContentText("экспорт не прошел");
                        alert.showAndWait();
                    }
                }catch (Exception b)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Предупреждение");
                    alert.setHeaderText(null);
                    alert.setContentText("экспорт не прошел");
                    alert.showAndWait();}
            }
        });

        //импорт
        import_menuItem.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxmlFiles/import.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                System.out.println("Импорт не прошел");
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Импорт файлов");
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            table.refresh();
            String path = "";

            try {
                FileInputStream fileInputStream;
                ArrayList<Person> arrayList = null;
                path = ImportController.path;
                fileInputStream = new FileInputStream(path);
                ObjectInputStream objectInputStream;
                objectInputStream = new ObjectInputStream(fileInputStream);
                arrayList = (ArrayList<Person>)objectInputStream.readObject();
                ObservableList<Person> personsNew = FXCollections.observableArrayList(arrayList);
                //persons.addAll(personsNew);
                for(Person person: personsNew) {
                    boolean bool = true;
                    for (int i = 0; i < persons.size(); i++) {
                        if(persons.get(i).getName().equals(person.getName()) && persons.get(i).getLastname().equals(person.getLastname()) && persons.get(i).getSurname().equals(person.getSurname()))
                        {
                            bool = false;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Подсказка");
                            alert.setHeaderText(null);
                            alert.setContentText("Была попытка импорта уже существуещего пользователя, он был пропущен\n Имя: " + persons.get(i).getName()
                            + "\n Фамилия: " + persons.get(i).getLastname() + "\n Отчество: " + persons.get(i).getSurname());
                            alert.showAndWait();
                        }
                    }
                    if(bool) {
                        persons.add(person);

                        DataBaseClass bd = DataBaseClass.getInstance();
                        bd.add(person);
                    }
                }
                table.refresh();
            } catch (Exception e) {
                try {
                    if (path.equals("")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Предупреждение");
                        alert.setHeaderText(null);
                        alert.setContentText("Ошибка импорта");
                        alert.showAndWait();
                    }
                }catch (Exception b)
                {Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Предупреждение");
                    alert.setHeaderText(null);
                    alert.setContentText("импорт не прошел");
                    alert.showAndWait();}
            }
        });

        //поиск на Enter
        search_textField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER))
            {
                if(search_textField.getText().isEmpty()) {
                    table.setItems(persons);
                }
                else
                {
                    //table.setItems(SearchLogic.fillTable(search_textField.getText()));
                    SearchLogic.searchContact(search_textField.getText());
                    table.setItems(DataBaseClass.filteredData);
                    table.refresh();
                }
            }

        });

        //поиск на кнопку поиска
        search_btn.setOnAction(event -> {
            if(search_textField.getText().isEmpty()) {
                table.setItems(persons);
            }
            else
            {
                //table.setItems(SearchLogic.fillTable(search_textField.getText()));
                SearchLogic.searchContact(search_textField.getText());
                table.setItems(DataBaseClass.filteredData);
                table.refresh();
            }
        });

        //следит за изменением в главном списке контактов
        persons.addListener(new ListChangeListener<Person>() {

            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Person> pChange) {
                while (pChange.next()) {
                    if(!search_textField.getText().trim().isBlank()) {
                        table.setItems(SearchLogic.fillTable(search_textField.getText()));
                        table.refresh();
                    }
                }
            }
        });
    }

    /**
     * редактирование
     */
    public void redactUserMethod()
    {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Redactor_controller.redactPerson = table.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxmlFiles/redactor.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Редактирование контакта");
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            table.refresh();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setHeaderText(null);
            alert.setContentText("не выделен контакт для редактирования");
            alert.showAndWait();
        }
    }
    /**
     * добавление
     */
    public void addUserMethod()
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxmlFiles/add_app.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Добавление контакта");
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
    /**
     * удаление
     */
    public void deleteUserMethod()
    {
        if (table.getSelectionModel().getSelectedItem() != null) {
            DataBaseClass bd = DataBaseClass.getInstance();
            int id = table.getSelectionModel().getSelectedItem().getId();
            bd.remove(id);
            persons.remove(table.getSelectionModel().getSelectedItem());
            table.refresh();
        }
    }
}
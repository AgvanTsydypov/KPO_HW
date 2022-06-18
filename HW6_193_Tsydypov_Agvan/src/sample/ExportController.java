import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExportController implements Initializable {

    @FXML
    private TextField path_textField;

    @FXML
    private Button export_btn;

    @FXML
    private Button exit_btn;

    /**
     * открывает файл чузер
     */
    @FXML
    void mouseClick(MouseEvent event) throws IOException {
        Stage stage= (Stage) export_btn.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile;
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.*"));
        selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            path_textField.setText(selectedFile.getCanonicalPath());
        }
    }

    static public String path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //добавление пути
        export_btn.setOnAction(event -> {
            path = path_textField.getText();
            export_btn.getScene().getWindow().hide();
        });
        //выход
        exit_btn.setOnAction(event -> {
            exit_btn.getScene().getWindow().hide();
        });
    }
}
//exportChooser();
//static public DirectoryChooser directoryChooser;

// public void exportChooser(){
//     try{
//         String anotherpath = directoryChooser.showDialog(new Stage()).getAbsolutePath();
//         path_textField.setText(anotherpath);
//         System.out.println(path_textField.getText());
//     }catch (Exception e)
//     {}
// }
//directoryChooser = new DirectoryChooser();
//directoryChooser.setTitle("Сохранить файл");
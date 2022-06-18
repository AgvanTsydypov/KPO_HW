import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImportController implements Initializable {

    @FXML
    private TextField path_textField;

    @FXML
    private Button export_btn;

    @FXML
    private Button exit_btn;

    //private FileChooser fileChooser;
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
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            path_textField.setText(selectedFile.getCanonicalPath());
        }
    }

    static public String path;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * чтение пути
         */
        export_btn.setOnAction(event -> {
            path = path_textField.getText();
            exit_btn.getScene().getWindow().hide();
        });
        /**
         * выход
         */
        exit_btn.setOnAction(event -> {
            exit_btn.getScene().getWindow().hide();
        });
    }
}
// public void importChooser()
// {
//     try{
//         String anotherPath = fileChooser.showSaveDialog(new Stage()).getAbsolutePath();
//         path_textField.setText(anotherPath);
//     } catch (Exception ignored){
//     }
// }
//  fileChooser = new FileChooser();
//  fileChooser.setTitle("Открыть файл");
//  fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
//       importChooser();
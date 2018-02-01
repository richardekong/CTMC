package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NavigationDrawerController implements Initializable {
    // instance variable declaration
    @FXML
    private VBox drawercontent;

    @FXML
    private ImageView sidePaneImage;

    @FXML
    private Button savebtn;

    @FXML
    private Button loadbtn;

    @FXML
    private Button aboutbtn;

    @FXML
    private Button Quitbtn;
    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}

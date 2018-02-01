package sample;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class Main extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CTMCGUI.fxml"));
        primaryStage.setTitle("CTMC Availability of IaaS with Multiple pools of PMs and VMs");
        primaryStage.setScene(new Scene(root, 970, 730));
        primaryStage.setResizable(false);
        mainStage = primaryStage;
        styleScene(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void styleScene(Stage stage) {
        URL resource = this.getClass().getResource("style.css");
        resource.toExternalForm();
        stage.getScene().getRoot().setId("Root");
        stage.getScene().getStylesheets().add(resource.toString());
    }

    public static Stage getMainStage() {
        return mainStage;
    }


}
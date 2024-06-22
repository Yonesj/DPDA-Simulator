package app;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.PDAs;
import view.HomePanel;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            PDAs.configure();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("IO Error");
            alert.setContentText("could n't load PDAs.");
        }

        HomePanel homePanel = new HomePanel(stage);
        Scene homeScene = new Scene(homePanel.getPanel(), 1280, 720);
        stage.setScene(homeScene);
        stage.setTitle("home panel");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
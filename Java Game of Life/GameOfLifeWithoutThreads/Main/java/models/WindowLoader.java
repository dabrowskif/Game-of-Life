package models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowLoader {

    public void createWindow(Stage stage, String fxml, String css, String title, String icon, Object controller, boolean isResizable) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setTitle(title);
        //stage.getIcons().add(new Image(icon));
        stage.setResizable(isResizable);
    }
}

import controllers.MainWindowController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        openMainWindow();
    }

    private void openMainWindow() throws IOException {
        MainWindowController mainWindowController = new MainWindowController();
        mainWindowController.showStage();
    }



}

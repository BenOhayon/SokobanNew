package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Run extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainWindow.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root, 600, 600);

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
    }
}

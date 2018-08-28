package view;

import controller.SokobanHQ;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.SokobanDataSource;

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

            SUI window = loader.getController();
            SokobanDataSource dataSource = new SokobanDataSource();
            SokobanHQ commander = new SokobanHQ(dataSource, window);
            window.addObserver(commander);
            dataSource.addObserver(commander);
            commander.start();

            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                window.quit();
            });
            primaryStage.show();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
    }
}

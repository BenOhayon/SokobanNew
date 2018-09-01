package view;

import controller.SokobanHQ;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.SokobanDataSource;

import java.io.File;

public class Run extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainWindow.fxml"));
            AnchorPane root = loader.load();
            File f = new File("/resources/sokoban_icon.jpg");
            Image image = new Image(f.toURI().toString());
            primaryStage.getIcons().add(image);
            Scene scene = new Scene(root, 800, 650);
            scene.getStylesheets().add("/view/application.css");

            SUI window = loader.getController();
            SokobanDataSource dataSource = new SokobanDataSource();
            SokobanHQ commander = new SokobanHQ(dataSource, window);
            window.addObserver(commander);
            dataSource.addObserver(commander);
            commander.start();

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
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

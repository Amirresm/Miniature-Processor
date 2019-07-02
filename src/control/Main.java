package control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        double screenX = Screen.getPrimary().getBounds().getMaxX();
        double screenY = Screen.getPrimary().getBounds().getMaxY();
        Parent root = FXMLLoader.load(getClass().getResource("view/ui.fxml"));
        primaryStage.setTitle("Miniature processor");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("view/images/icon.png")));
        Scene scene = new Scene(root, screenX * 0.9, screenY * 0.9);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

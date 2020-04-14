package application;


import java.io.FileInputStream;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * @author Jun
 *
 */
public class Main extends Application {
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<String> args;

    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 200;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // save args example
        args = this.getParameters().getRaw();
            
        // Create a vertical box with Hello labels for each args
            VBox vbox = new VBox();
            for (String arg : args) {
                vbox.getChildren().add(new Label("hello "+arg));
            }

        // Main layout is Border Pane example (top,left,center,right,bottom)
            BorderPane root = new BorderPane();

        // Add the vertical box to the center of the root pane
        ComboBox box = new ComboBox();
        box.getItems().add("CS200");
        box.getItems().add("CS300");
        box.getItems().add("CS400");
        ImageView face = new ImageView(new Image(new FileInputStream("src/face.jpg")));
        face.setPreserveRatio(true);
        face.setFitHeight(100);
        ToggleButton tb = new ToggleButton("Toggle");
        
        root.setTop(new Label("CS400 My First JavaFX Program"));
        root.setLeft(box);
        root.setCenter(face);
        root.setBottom(new Button("Done"));
        root.setRight(tb);
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Add the stuff and set the primary stage
            primaryStage.setTitle("P5 HelloFX");
            primaryStage.setScene(mainScene);
            primaryStage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
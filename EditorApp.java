package View;

import Controller.EditorController;
import Model.EditorModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class EditorApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        EditorModel model = new EditorModel();
        EditorView view = new EditorView();
        EditorController controller = new EditorController(view, model, primaryStage);

        Scene scene = new Scene(view, 900, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Editor App");
        primaryStage.show();
    }
}

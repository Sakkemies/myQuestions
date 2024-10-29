package org.lizkozz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.lizkozz.resources.Resource;
import org.lizkozz.resources.ResourceHandler;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = ResourceHandler.loadFXMLResource(this, Resource.MAIN_SCENE);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(ResourceHandler.getResourceAsString(this,Resource.STYLES));

        stage.setTitle("myQuestions v0.1");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
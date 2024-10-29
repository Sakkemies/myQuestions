package org.lizkozz;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.lizkozz.memory.Memory;
import org.lizkozz.resources.Resource;
import org.lizkozz.resources.ResourceHandler;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception
    {
        Memory.initialize();
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
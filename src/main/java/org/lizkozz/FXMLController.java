package org.lizkozz;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.lizkozz.resources.UIUtilities;

public class FXMLController implements Initializable {

    @FXML
    private MenuBar menuBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");


        initializeMenu();
    }
    private void initializeMenu()
    {
        Menu fileMenu = UIUtilities.getMenu("Tiedosto");
        MenuItem exit = UIUtilities.getMenuItem("Sulje");
        exit.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
        MenuItem loadQuestions = UIUtilities.getMenuItem("Lataa kysymykset");
        MenuItem clearQuestions = UIUtilities.getMenuItem("Nollaa kysymykset");

        fileMenu.getItems().addAll(clearQuestions, loadQuestions, exit);

        menuBar.getMenus().add(fileMenu);
    }
}
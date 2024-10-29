package org.lizkozz;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.lizkozz.file.FileHandler;
import org.lizkozz.memory.Memory;
import org.lizkozz.models.Answer;
import org.lizkozz.models.Question;
import org.lizkozz.resources.Extensions;
import org.lizkozz.resources.UIUtilities;

public class FXMLController implements Initializable {

    @FXML
    public TabPane questionBox;
    @FXML
    private MenuBar menuBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        initializeMenu();
        initializeQuestionBox();
    }
    private void initializeQuestionBox()
    {
        questionBox.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
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
        loadQuestions.setOnAction(event ->
        {
            Memory.setQuestions(new FileHandler().openFile(menuBar.getScene().getWindow(), Extensions.TXT_FILES));
            refresh();
        });

        MenuItem clearQuestions = UIUtilities.getMenuItem("Nollaa kysymykset");
        clearQuestions.setOnAction(event -> {
            refresh();
        });

        fileMenu.getItems().addAll(clearQuestions, loadQuestions, exit);

        menuBar.getMenus().add(fileMenu);
    }

    private void refresh()
    {
        Memory.shuffle();
        List<Question> questionList = Memory.getQuestions();
        questionBox.getTabs().clear();


        for(int i = 0; i < questionList.size(); i++)
        {
            if(questionList.get(i).isValid()) {
                Tab tab = new Tab();
                tab.setText("Kysymys " + (i + 1));
                setQuestion(tab, questionList.get(i));
            }
        }
    }

    private void setQuestion(Tab tab, Question question)
    {
        VBox vBox = new VBox();
        Label questionLabel = new Label(question.getQuestionAsString());
        questionLabel.setBackground(new Background(new BackgroundFill(Color.valueOf("#7ea6a5"), null, null)));
        questionLabel.setPadding(new Insets(20));
        questionLabel.setPrefWidth(Double.MAX_VALUE);
        questionLabel.setMinWidth(0);
        questionLabel.setMaxWidth(Double.MAX_VALUE);

        VBox answersVBox = new VBox();
        answersVBox.setPadding(new Insets(20));

        vBox.getChildren().addAll(questionLabel, answersVBox);

        if (question.isMultiChoice()) {
            for (Answer answer : question.getAnswers()) {
                CheckBox answerCheckBox = new CheckBox(answer.getAnswerAsText());
                answersVBox.getChildren().add(answerCheckBox);
            }
        }
        else {
            ToggleGroup toggleGroup = new ToggleGroup();
            for (Answer answer : question.getAnswers()) {
                RadioButton answerRadioButton = new RadioButton(answer.getAnswerAsText());
                answerRadioButton.setToggleGroup(toggleGroup);

                answersVBox.getChildren().add(answerRadioButton);
            }
        }

        tab.setContent(vBox);
        questionBox.getTabs().add(tab);
    }
}
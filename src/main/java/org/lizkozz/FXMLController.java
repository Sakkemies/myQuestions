package org.lizkozz;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    public Button previousQuestion;
    @FXML
    public Button nextQuestion;
    @FXML
    public Button submit;
    @FXML
    public HBox buttonBox;
    @FXML
    public Button check;
    @FXML
    private MenuBar menuBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        initializeMenu();
        initializeQuestionBox();
        initializeImageBox();
    }
    private void initializeImageBox()
    {

    }
    private void initializeQuestionBox()
    {
        questionBox.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        nextQuestion.setOnAction(event -> switchToNextTab());
        previousQuestion.setOnAction(event -> switchToPreviousTab());

        submit.setOnAction(event -> checkAllQuestions());
        check.setOnAction(event -> checkCurrentQuestion());
        updateButtonState();

        setButtonSize(120,100, nextQuestion);
        setButtonSize(120,100, submit);
        setButtonSize(120,100, check);
        setButtonSize(120,100, previousQuestion);

        buttonBox.setPadding(new Insets(10));
        buttonBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#7ea6a5"), null, null)));
    }
    private void setButtonSize(double width, double height, Button button)
    {
        button.setMaxSize(width, height);
        button.setPrefSize(width, height);
        button.setMinSize(width, height);
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

        Menu optionsMenu = UIUtilities.getMenu("Asetukset");

        CheckMenuItem shuffle = UIUtilities.getCheckMenuItem("Sekoita kysymykset");
        shuffle.setOnAction(event -> {
            Memory.setShuffle(!Memory.isShuffle());
            shuffle.setSelected(Memory.isShuffle());
        });
        shuffle.setSelected(Memory.isShuffle());

        optionsMenu.getItems().addAll(shuffle);

        menuBar.getMenus().addAll(fileMenu, optionsMenu);
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
                tab.setText("Q" + (i + 1));
                setQuestion(tab, questionList.get(i));
            }
        }

        updateButtonState();
    }

    private void setQuestion(Tab tab, Question question)
    {
        VBox vBox = new VBox();
        Label questionLabel = new Label(question.getQuestionAsString());
        questionLabel.setWrapText(true);
        questionLabel.setBackground(new Background(new BackgroundFill(Color.valueOf("#7ea6a5"), null, null)));
        questionLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        questionLabel.setPadding(new Insets(20));
        questionLabel.setPrefWidth(Double.MAX_VALUE);
        questionLabel.setMinWidth(0);
        questionLabel.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(questionLabel, Priority.ALWAYS);
        questionLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);
        questionLabel.setMinHeight(Region.USE_PREF_SIZE);
        questionLabel.setMaxHeight(Region.USE_COMPUTED_SIZE);

        HBox imageBox = new HBox();
        imageBox.setPadding(new Insets(10));
        imageBox.setSpacing(4);

        imageBox.setAlignment(Pos.CENTER);
        for (File file : question.getImageFiles()) {

            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#7ea6a5"), null, null)));

            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();
            double minSize = 250;

            imageView.setFitWidth(Math.min(minSize, imageWidth));
            imageView.setFitHeight(Math.min(minSize, imageHeight));

            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.BLACK);
            dropShadow.setOffsetX(0);
            dropShadow.setOffsetY(0);
            dropShadow.setRadius(10);

            imageView.setEffect(dropShadow);

            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(event -> openFullScreenImage(file));

            imageBox.getChildren().add(imageView);
        }

        VBox answersVBox = new VBox();
        answersVBox.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane();
        vBox.getChildren().addAll(questionLabel, imageBox, answersVBox);

        scrollPane.setContent(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        if (question.isMultiChoice()) {
            for (Answer answer : question.getAnswers()) {
                CheckBox answerCheckBox = new CheckBox(answer.getAnswerAsText());
                answersVBox.getChildren().add(answerCheckBox);

                //answerCheckBox.setPrefWidth(Double.MAX_VALUE);
                //answerCheckBox.setMinWidth(0);
                //answerCheckBox.setMaxWidth(Double.MAX_VALUE);
                answerCheckBox.setWrapText(true);
                answerCheckBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
                answerCheckBox.setMinHeight(Region.USE_PREF_SIZE);
                answerCheckBox.setMaxHeight(Region.USE_COMPUTED_SIZE);
                VBox.setVgrow(answerCheckBox, Priority.ALWAYS);
                answerCheckBox.setStyle("-fx-font-size: 15;");
                answerCheckBox.setPadding(new Insets(5));
            }
        }
        else {
            ToggleGroup toggleGroup = new ToggleGroup();
            for (Answer answer : question.getAnswers()) {
                RadioButton answerRadioButton = new RadioButton(answer.getAnswerAsText());
                answerRadioButton.setToggleGroup(toggleGroup);

                answersVBox.getChildren().add(answerRadioButton);

                //answerRadioButton.setPrefWidth(Double.MAX_VALUE);
                //answerRadioButton.setMinWidth(0);
                //answerRadioButton.setMaxWidth(Double.MAX_VALUE);
                answerRadioButton.setWrapText(true);
                answerRadioButton.setPrefHeight(Region.USE_COMPUTED_SIZE);
                answerRadioButton.setMinHeight(Region.USE_PREF_SIZE);
                answerRadioButton.setMaxHeight(Region.USE_COMPUTED_SIZE);
                VBox.setVgrow(answerRadioButton, Priority.ALWAYS);
                answerRadioButton.setStyle("-fx-font-size: 15;");
                answerRadioButton.setPadding(new Insets(5));
            }
        }

        tab.setContent(scrollPane);
        questionBox.getTabs().add(tab);

        tab.setOnSelectionChanged(event -> updateButtonState());
    }

    private void switchToPreviousTab() {
        int currentIndex = questionBox.getSelectionModel().getSelectedIndex();
        if (currentIndex > 0) {
            questionBox.getSelectionModel().select(currentIndex - 1);
        }
        updateButtonState();
    }

    private void switchToNextTab() {
        int currentIndex = questionBox.getSelectionModel().getSelectedIndex();
        if (currentIndex < questionBox.getTabs().size() - 1) {
            questionBox.getSelectionModel().select(currentIndex + 1);
        }
        updateButtonState();
    }
    private void updateButtonState() {
        int currentIndex = questionBox.getSelectionModel().getSelectedIndex();
        previousQuestion.setDisable(currentIndex == 0 || Memory.getQuestions().isEmpty());
        nextQuestion.setDisable(currentIndex == questionBox.getTabs().size() - 1);
    }

    private void checkCurrentQuestion() {
        try {
            int currentIndex = questionBox.getSelectionModel().getSelectedIndex();
            Question question = Memory.getQuestions().get(currentIndex);

            VBox vbox = (VBox) ((ScrollPane) questionBox.getTabs().get(currentIndex).getContent()).getContent();

            VBox answerBox = (VBox) vbox.getChildren().get(2);

            boolean isRight = true;
            for (int i = 0; i < answerBox.getChildren().size(); i++)
            {
                Node node = answerBox.getChildren().get(i);

                if(isRight)
                    isRight = colorizeQuestion(node, i, question);
                else
                    colorizeQuestion(node, i, question);
            }

            showSingleAnswerDialog(isRight);
        }
        catch (Exception e){}
    }
    private static final Border answerBorder = new Border(new BorderStroke(
            Color.BLACK,
            BorderStrokeStyle.DOTTED,
            CornerRadii.EMPTY,
            new BorderWidths(1)
    ));
    private boolean colorizeQuestion(Node node, int index, Question question)
    {
        boolean isAllRight = true;

        if (node instanceof CheckBox checkBox)
        {
            checkBox.setBorder(Border.EMPTY);
            checkBox.setBackground(Background.EMPTY);
            boolean isTrue = question.isCorrect(checkBox.isSelected(), index);
            if(!isTrue)
            {
                isAllRight = false;
                if(question.isCorrect(index) && !checkBox.isSelected())
                {
                    checkBox.setBackground(new Background(new BackgroundFill(Color.valueOf(COLOR_RED), null, null)));
                    checkBox.setBorder(answerBorder);
                }
            }
            else
            {
                if(question.isCorrect(index) && checkBox.isSelected())
                {
                    checkBox.setBackground(new Background(new BackgroundFill(Color.valueOf(COLOR_GREEN), null, null)));
                    checkBox.setBorder(answerBorder);
                }
            }

        }
        else if (node instanceof RadioButton checkBox)
        {
            boolean isTrue = question.isCorrect(checkBox.isSelected(), index);
            if(!isTrue)
            {
                isAllRight = false;
                if(question.isCorrect(index) && !checkBox.isSelected())
                {
                    checkBox.setBackground(new Background(new BackgroundFill(Color.valueOf(COLOR_RED), null, null)));
                    checkBox.setBorder(answerBorder);
                }
            }
            else
            {
                if(question.isCorrect(index) && checkBox.isSelected())
                {
                    checkBox.setBackground(new Background(new BackgroundFill(Color.valueOf(COLOR_GREEN), null, null)));
                    checkBox.setBorder(answerBorder);
                }
            }
        }

        return isAllRight;
    }
    private void checkAllQuestions() {
        List<Question> questionList = Memory.getQuestions();
        int maxScore = questionList.size();
        int currentScore = 0;

        for (Question question : questionList)
        {
            VBox vbox = (VBox) ((ScrollPane) questionBox.getTabs().get(questionList.indexOf(question)).getContent()).getContent();

            VBox answerBox = (VBox) vbox.getChildren().get(2);

            boolean isAllRight = true;
            for (int i = 0; i < answerBox.getChildren().size(); i++)
            {
                Node node = answerBox.getChildren().get(i);
                if(isAllRight)
                    isAllRight = colorizeQuestion(node, i, question);
                else
                    colorizeQuestion(node, i, question);
            }
            if(isAllRight)
                currentScore++;
        }

        showScoreDialog(currentScore, maxScore);
    }
    private String COLOR_RED = "#ff6363";
    private String COLOR_GREEN = "#e0ffea";
    private void showScoreDialog(int currentScore, int maxScore) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tulos");
        alert.setHeaderText("Suorituksesi on arvioitu");
        double percentage = ((double) currentScore / maxScore) * 100;
        String percentageText = String.format("%.2f", percentage);

        alert.setContentText("Pisteet: " + currentScore + "/" + maxScore + "\nOnnistumisprosentti: " + percentageText + "%");

        alert.showAndWait();
    }
    private void showSingleAnswerDialog(boolean isRight) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vastauksesi on...");
        alert.setHeaderText(isRight ? "Oikea vastaus!\n" : "Virheellinen vastaus!\n");

        alert.setContentText(isRight ? "Vastauksesi on oikein!" : "Vastauksesi on virheellinen!");

        alert.showAndWait();
    }
    private void openFullScreenImage(File file) {
        Stage imageStage = new Stage();
        imageStage.initModality(Modality.NONE);

        Image fullImage = new Image(file.toURI().toString());
        ImageView fullImageView = new ImageView(fullImage);

        fullImageView.setFitWidth(fullImage.getWidth());
        fullImageView.setFitHeight(fullImage.getWidth());
        fullImageView.setPreserveRatio(true);

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setContent(fullImageView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        StackPane root = new StackPane();
        root.getChildren().add(scrollPane);

        Scene scene = new Scene(root);
        imageStage.setScene(scene);
        imageStage.setTitle(file.getName());
        imageStage.show();
    }
}
module myQuestions.main
{
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    exports org.lizkozz;
    opens org.lizkozz to javafx.fxml;
}
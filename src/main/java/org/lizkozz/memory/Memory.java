package org.lizkozz.memory;

import org.lizkozz.file.FileHandler;
import org.lizkozz.models.Question;
import org.lizkozz.models.QuestionBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memory
{
    public static String FILE_SETTINGS = "settings.set";
    private static String version = "0.4.0";
    private static String appName = "myQuestions";
    public static int MAX_AMOUNT_OF_QUESTIONS = 1000;
    private static int amountOfQuestions = MAX_AMOUNT_OF_QUESTIONS;
    private static boolean shuffleOn = true;
    public static void setShuffle(boolean shuffle) {shuffleOn = shuffle;}
    public static boolean isShuffle(){return shuffleOn;}
    private static List<Question> questions;
    public static void initialize()
    {
        questions = new ArrayList<>();
        new FileHandler().loadSettings();
    }
    public static List<Question> getQuestions() {return questions == null ? new ArrayList<>() : questions;}
    public static void clearQuestions() {questions = new ArrayList<>();}
    public static int getAmountOfQuestions(){return amountOfQuestions;}
    public static void setAmountOfQuestions(int amount){amountOfQuestions = amount;}
    public static void setQuestions(File file) {
        try {questions = new QuestionBuilder().build(file);}
        catch (Exception ignored) {}
    }
    public static void printQuestions()
    {
        for(Question question: questions)
            System.out.println(question);
    }
    public static void shuffle()
    {
        if(isShuffle()) {
            Collections.shuffle(questions);
            for (Question question : questions)
                Collections.shuffle(question.getAnswers());
        }
    }
    public static String getAppName()
    {
        return appName + " " + version;
    }
}

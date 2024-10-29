package org.lizkozz.memory;

import org.lizkozz.file.FileHandler;
import org.lizkozz.models.Question;
import org.lizkozz.models.QuestionBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Memory
{
    private static List<Question> questions;
    public static void initialize() {}
    public static List<Question> getQuestions() {return questions == null ? new ArrayList<>() : questions;}
    public static void clearQuestions() {questions = new ArrayList<>();}
    public static void setQuestions(File file) {
        try {questions = new QuestionBuilder().build(file); printQuestions();}
        catch (Exception ignored) {}
    }
    public static void printQuestions()
    {
        for(Question question: questions)
            System.out.println(question);
    }
}

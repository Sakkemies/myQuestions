package org.lizkozz.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Question
{
    private String question;
    private List<Answer> answerList;
    private List<String> images;
    private String path;
    public Question(String text, String path) {
        question = text;
        answerList = new ArrayList<>();
        this.path = path;
    }
    public String getPath()
    {
        return path;
    }
    public List<File> getImageFiles()
    {
        List<File> files = new ArrayList<>();
        for(String str: getImages())
            files.add(new File(path+"/"+str));

        return files;
    }
    public List<Answer> getAnswers()
    {
        if(answerList == null)
            return new ArrayList<>();

        return answerList;
    }
    public void addAnswer(Answer answer)
    {
        if(answerList == null) answerList = new ArrayList<>();
        answerList.add(answer);
    }
    public String getQuestionAsString()
    {
        return question;
    }
    public void addText(String text, boolean isNewLine) {
        question+= (isNewLine ? "\n" : "") + text;
    }
    @Override
    public String toString() {
        String answers = "";

        for(Answer answer : answerList)
            answers += answer.toString() + "\n";

        return question + "\n" + answers + "\n\n";
    }

    public boolean isMultiChoice()
    {
        int count = 0;
        for(Answer answer: getAnswers())
            count += answer.isRight() ? 1 : 0;

        return count > 1;
    }
    public boolean isValid()
    {
        int count = 0;
        for(Answer answer: getAnswers())
            count += answer.isRight() ? 1 : 0;

        return count > 0;
    }
    public boolean isCorrect(boolean userAnswer, int answerIndex) {
        try
        {
            return getAnswers().get(answerIndex).isRight() == userAnswer;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public void addImage(String path)
    {
        if(images == null)
            images = new ArrayList<>();

        images.add(path);
    }
    public List<String> getImages()
    {
        return images == null ? new ArrayList<>() : images;
    }
}

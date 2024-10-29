package org.lizkozz.models;

import java.util.ArrayList;
import java.util.List;

public class Question
{
    private String question;
    private List<Answer> answerList;
    public Question(String text) {
        question = text;
        answerList = new ArrayList<>();
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
}

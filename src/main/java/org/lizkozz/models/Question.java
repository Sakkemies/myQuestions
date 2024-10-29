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
    public void addAnswer(Answer answer)
    {
        if(answerList == null) answerList = new ArrayList<>();
        answerList.add(answer);
    }
    @Override
    public String toString() {
        String answers = "";

        for(Answer answer : answerList)
            answers += answer.toString() + "\n";

        return question + "\n" + answers + "\n\n";
    }
}

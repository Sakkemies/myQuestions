package org.lizkozz.models;

public class Answer
{
    private String answer;
    private boolean right;
    public Answer(String text) {
        answer = text;
    }
    public Answer(String text, boolean isRight)
    {
        answer = text;
        right = isRight;
    }
    @Override
    public String toString()
    {
        return answer + (right ? " (Oikein)" : "");
    }
}

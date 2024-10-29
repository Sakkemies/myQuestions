package org.lizkozz.models;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QuestionBuilder
{
    public List<Question> build(File file) throws IOException {
        if(file == null)
            return new ArrayList<>();

        List<Question> questionList = new ArrayList<>();
        Question currentQuestion = null;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("[/Q]")) {
                    if (currentQuestion != null) {
                        questionList.add(currentQuestion);
                    }
                    currentQuestion = new Question(line.replaceAll("\\[/Q]",""));

                } else if (line.startsWith("[/AW]") || line.startsWith("[/AR]")) {
                    Answer answer = new Answer(line.replaceAll("\\[/AR]","").replaceAll("\\[/AW]",""), line.startsWith("[/AR]"));
                    if (currentQuestion != null) {
                        currentQuestion.addAnswer(answer);
                    }
                }
            }
            if (currentQuestion != null) {
                questionList.add(currentQuestion);
            }
        }

        return questionList;
    }
}

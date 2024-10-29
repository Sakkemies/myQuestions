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
        Answer currentAnswer = null;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            boolean endFound = true;
            String endTag = "";
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if(!endFound && currentQuestion != null && endTag.equals("[/Q]")) {
                    currentQuestion.addText(line.replaceAll("\\[/Q]", ""), true);
                    if (line.endsWith("[/Q]")) {
                        endFound = true;
                    }
                }
                if(!endFound && endTag.equals("[/AW]") && currentAnswer != null) {
                    currentAnswer.addText(line.replaceAll("\\[/AW]", ""), true);
                    if (line.endsWith("[/AW]")) {
                        endFound = true;
                    }
                }
                if(!endFound && endTag.equals("[/AR]") && currentAnswer != null) {
                    currentAnswer.addText(line.replaceAll("\\[/AR]", ""), true);
                    if (line.endsWith("[/AR]")) {
                        endFound = true;
                    }
                }

                if(endFound) {
                    if (line.startsWith("[/Q]")) {
                        if (currentQuestion != null) {
                            questionList.add(currentQuestion);
                        }
                        currentQuestion = new Question(line.replaceAll("\\[/Q]", "").replaceAll("\\[Q]", ""));
                        endFound = false;
                        endTag = "[/Q]";

                        if(line.endsWith("[/Q]")) {
                            endFound = true;
                            endTag = "";
                        }

                    } else if (line.startsWith("[/AW]") || line.startsWith("[/AR]")) {
                        currentAnswer = new Answer(line.replaceAll("\\[/AR]", "").replaceAll("\\[/AW]", ""), line.startsWith("[/AR]"));
                        if (currentQuestion != null) {
                            currentQuestion.addAnswer(currentAnswer);
                        }
                        endTag = line.startsWith("[/AW]") ? "[/AW]" : "[/AR]";
                        endFound = false;

                        if(line.endsWith(endTag)) {
                            endFound = true;
                            endTag = "";
                        }
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

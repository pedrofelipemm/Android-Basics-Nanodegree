package study.pmoreira.project3;

import java.util.List;

public class Question {

    private String text;
    private List<Answer> answers;

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isMultipleAnswer() {
        int rightAnswer = 0;
        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                rightAnswer++;
            }
        }
        return rightAnswer > 1;
    }

    public boolean isTextQuestion() {
        return answers.size() == 1;
    }
}

package study.pmoreira.project3;

import java.util.ArrayList;
import java.util.List;

public class Questions {

    private List<Question> questions = new ArrayList<>();

    public Questions() {
    }

    public Questions(List<Question> questions) {
        this.questions = questions;
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public List<Answer> getAnswers(int questionIndex) {
        return getQuestion(questionIndex).getAnswers();
    }

    public int size() {
        return questions.size();
    }
}

package study.pmoreira.project3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class QuestionsUtil {

    public static Questions fetchQuestions() {

        //I'm not going to put it in strings.xml because it should be coming from a web server.. Hope to do it in future lessons
        List<Question> questions = new ArrayList<>();

        String question = "What is the colors of the japanese flag?";
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("White", true));
        answers.add(new Answer("Black", false));
        answers.add(new Answer("Orange", false));
        answers.add(new Answer("Red", true));
        questions.add(new Question(question, answers));

        question = "Who was the first president of the United States?";
        answers = new ArrayList<>();
        answers.add(new Answer("James Madison", false));
        answers.add(new Answer("George Washington", true));
        answers.add(new Answer("Thomas Jefferson", false));
        answers.add(new Answer("John Adams", false));
        questions.add(new Question(question, answers));

        question = "What is the colors of the brazilian flag?";
        answers = new ArrayList<>();
        answers.add(new Answer("Blue", true));
        answers.add(new Answer("Gold", true));
        answers.add(new Answer("Green", true));
        answers.add(new Answer("White", true));
        questions.add(new Question(question, answers));

        question = "When did world war 2 end?";
        answers = new ArrayList<>();
        answers.add(new Answer("1945", true));
        answers.add(new Answer("1944", false));
        answers.add(new Answer("1940", false));
        answers.add(new Answer("1955", false));
        questions.add(new Question(question, answers));

        question = "When was the first android released?";
        answers = new ArrayList<>();
        answers.add(new Answer("2010", false));
        answers.add(new Answer("2000", false));
        answers.add(new Answer("2007", false));
        answers.add(new Answer("2008", true));
        questions.add(new Question(question, answers));

        Collections.shuffle(questions);
        return new Questions(questions);
    }
}

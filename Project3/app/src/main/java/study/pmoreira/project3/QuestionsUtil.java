package study.pmoreira.project3;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class QuestionsUtil {

    private Context context;

    public QuestionsUtil(Context context) {
        this.context = context;
    }

    public Questions fetchQuestions() {

        List<Question> questions = new ArrayList<>();

        String question = context.getString(R.string.question1);
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(context.getString(R.string.question1_answer1), true));
        answers.add(new Answer(context.getString(R.string.question1_answer2), false));
        answers.add(new Answer(context.getString(R.string.question1_answer3), false));
        answers.add(new Answer(context.getString(R.string.question1_answer4), true));
        questions.add(new Question(question, answers));

        question = context.getString(R.string.question2);
        answers = new ArrayList<>();
        answers.add(new Answer(context.getString(R.string.question2_answer1), false));
        answers.add(new Answer(context.getString(R.string.question2_answer2), true));
        answers.add(new Answer(context.getString(R.string.question2_answer3), false));
        answers.add(new Answer(context.getString(R.string.question2_answer4), false));
        questions.add(new Question(question, answers));

        question = context.getString(R.string.question3);
        answers = new ArrayList<>();
        answers.add(new Answer(context.getString(R.string.question3_answer1), true));
        answers.add(new Answer(context.getString(R.string.question3_answer2), true));
        answers.add(new Answer(context.getString(R.string.question3_answer3), true));
        answers.add(new Answer(context.getString(R.string.question3_answer4), true));
        questions.add(new Question(question, answers));

        question = context.getString(R.string.question4);
        answers = new ArrayList<>();
        answers.add(new Answer(context.getString(R.string.question4_answer1), true));
        answers.add(new Answer(context.getString(R.string.question4_answer2), false));
        answers.add(new Answer(context.getString(R.string.question4_answer3), false));
        answers.add(new Answer(context.getString(R.string.question4_answer4), false));
        questions.add(new Question(question, answers));

        question = context.getString(R.string.question5);
        answers = new ArrayList<>();
        answers.add(new Answer(context.getString(R.string.question5_answe1), true));
        questions.add(new Question(question, answers));

        question = context.getString(R.string.question6);
        answers = new ArrayList<>();
        answers.add(new Answer(context.getString(R.string.question6_answer1), true));
        questions.add(new Question(question, answers));

        return new Questions(questions);
    }
}

package study.pmoreira.project3;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_SCORE = "CURRENT_SCORE";
    private static final String FINAL_SCORE = "FINAL_SCORE";
    private static final String CURRENT_QUESTION = "CURRENT_QUESTION";

    private int currentQuestion;
    private int currentScore;
    private int finalScore;

    private Questions questions = new Questions();

    private TextView textViewQuestion;

    private EditText answerEditText;

    private LinearLayout contentView;

    private Button btnNewGame;
    private Button btnSubmit;

    private View checkboxGroup;
    private RadioGroup radioGroup;

    private List<CompoundButton> answerRadios = new ArrayList<>();
    private List<CompoundButton> answerCheckboxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blurBackground(4);

        initViews();

        startGame(null);

        if (savedInstanceState != null) {
            currentScore = savedInstanceState.getInt(CURRENT_SCORE);
            finalScore = savedInstanceState.getInt(FINAL_SCORE);
            currentQuestion = savedInstanceState.getInt(CURRENT_QUESTION);
            if (currentQuestion == -1) {
                currentQuestion = 0;
            }

            textViewQuestion.setText(questions.getQuestion(currentQuestion).getText());
            displayAnswers(questions.getQuestion(currentQuestion));

            if (getResources().getConfiguration().orientation == 2) {
                ((FrameLayout.LayoutParams) contentView.getLayoutParams()).setMargins(
                        getResources().getDimensionPixelOffset(R.dimen.landscape_content_margin),
                        getResources().getDimensionPixelOffset(R.dimen.default_margin),
                        getResources().getDimensionPixelOffset(R.dimen.landscape_content_margin),
                        getResources().getDimensionPixelOffset(R.dimen.default_margin));
            } else {
                ((FrameLayout.LayoutParams) contentView.getLayoutParams()).setMargins(
                        getResources().getDimensionPixelOffset(R.dimen.default_margin),
                        getResources().getDimensionPixelOffset(R.dimen.default_margin),
                        getResources().getDimensionPixelOffset(R.dimen.default_margin),
                        getResources().getDimensionPixelOffset(R.dimen.default_margin));
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(CURRENT_SCORE, currentScore);
        outState.putInt(FINAL_SCORE, finalScore);
        outState.putInt(CURRENT_QUESTION, currentQuestion);
    }

    private void initViews() {
        answerRadios.add((RadioButton) findViewById(R.id.radio_answer1));
        answerRadios.add((RadioButton) findViewById(R.id.radio_answer2));
        answerRadios.add((RadioButton) findViewById(R.id.radio_answer3));
        answerRadios.add((RadioButton) findViewById(R.id.radio_answer4));

        answerCheckboxes.add((CheckBox) findViewById(R.id.checkbox_answer1));
        answerCheckboxes.add((CheckBox) findViewById(R.id.checkbox_answer2));
        answerCheckboxes.add((CheckBox) findViewById(R.id.checkbox_answer3));
        answerCheckboxes.add((CheckBox) findViewById(R.id.checkbox_answer4));

        checkboxGroup = findViewById(R.id.checkbox_group_answers);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_answers);

        btnNewGame = (Button) findViewById(R.id.btn_new_game);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        textViewQuestion = (TextView) findViewById(R.id.text_view_question);

        answerEditText = (EditText) findViewById(R.id.edit_text_answer);

        contentView = (LinearLayout) findViewById(R.id.content_view);
    }

    private void blurBackground(int sampleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        ImageView background = (ImageView) findViewById(R.id.image_view_background);
        background.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background, options));
    }

    public void startGame(View view) {
        questions = new QuestionsUtil(this).fetchQuestions();

        setUpGame();
        nextQuestion();

        btnNewGame.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.VISIBLE);
    }

    private void setUpGame() {
        currentQuestion = -1;
        currentScore = 0;

        checkboxGroup.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);

        textViewQuestion.setText(null);
        answerEditText.setVisibility(View.GONE);
    }

    private void clearAnswers() {
        for (CompoundButton answerCheckbox : answerCheckboxes) {
            answerCheckbox.setChecked(false);
        }
        answerEditText.setText(null);
        radioGroup.clearCheck();
    }

    private void nextQuestion() {
        currentQuestion++;
        clearAnswers();

        if (currentQuestion < questions.size()) {
            Question question = questions.getQuestion(currentQuestion);
            textViewQuestion.setText(question.getText());
            displayAnswers(question);
        } else {
            finalScore = currentScore;
            endGame();
        }
    }

    private void endGame() {
        btnNewGame.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);

        setUpGame();
    }

    private void displayAnswers(Question question) {
        if (question.isMultipleAnswer()) {
            answerEditText.setVisibility(View.GONE);
            checkboxGroup.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.GONE);
            displayAnswerButtons(answerCheckboxes);
        } else if (question.isTextQuestion()) {
            checkboxGroup.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
            answerEditText.setVisibility(View.VISIBLE);
        } else {
            answerEditText.setVisibility(View.GONE);
            checkboxGroup.setVisibility(View.GONE);
            radioGroup.setVisibility(View.VISIBLE);
            displayAnswerButtons(answerRadios);
        }
    }

    private void displayAnswerButtons(List<CompoundButton> compoundButtons) {
        List<Answer> answers = questions.getAnswers(currentQuestion);
        for (int i = 0; i < answers.size(); i++) {
            CompoundButton compoundButton = compoundButtons.get(i);
            String answer = answers.get(i).getText();

            compoundButton.setText(answer);
        }
    }

    public void submitAnswer(View view) {
        boolean isCorrectAnswer;
        Question question = questions.getQuestion(currentQuestion);

        if (question.isMultipleAnswer()) {
            isCorrectAnswer = isCorrectAnswer(answerCheckboxes);
        } else if (question.isTextQuestion()) {
            isCorrectAnswer = question.getAnswers().get(0).getText().trim().equals(answerEditText.getText().toString().trim());
        } else {
            isCorrectAnswer = isCorrectAnswer(answerRadios);
        }

        if (isCorrectAnswer) {
            currentScore += 10;
            nextQuestion();

            if (currentQuestion != -1) {
                Toast.makeText(this, R.string.right_answer_message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.end_game_message, finalScore), Toast.LENGTH_SHORT).show();
            }
        } else {
            currentScore -= 5;
            Toast.makeText(this, R.string.wrong_answer_message, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isCorrectAnswer(List<CompoundButton> btnAnswers) {
        List<Answer> answers = questions.getAnswers(currentQuestion);
        for (int i = 0; i < 4; i++) {
            if (btnAnswers.get(i).isChecked() != answers.get(i).isCorrect()) {
                return false;
            }
        }
        return true;
    }
}

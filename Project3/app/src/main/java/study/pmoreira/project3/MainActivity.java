package study.pmoreira.project3;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String playerName;
    private int currentQuestion;
    private int currentScore;

    private Questions questions = new Questions();

    private RecyclerView recyclerViewScore;

    private TextView textViewQuestion;

    private Button btnNewGame;
    private Button btnSubmit;

    private View checkboxGroup;
    private RadioGroup radioGroup;

    private List<CompoundButton> answerRadios = new ArrayList<>();
    private List<CompoundButton> answerCheckboxes = new ArrayList<>();

    private ProgressBar quizProgressBar;

    private List<ScoreNode> scoreNodes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blurBackground(4);

        initViews();
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

        quizProgressBar = (ProgressBar) findViewById(R.id.progress_bar_quiz);

        recyclerViewScore = (RecyclerView) findViewById(R.id.recycler_view_score);
        recyclerViewScore.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewScore.setAdapter(new RecyclerViewScoreAdapter(scoreNodes));
    }

    private void blurBackground(int sampleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        ImageView background = (ImageView) findViewById(R.id.image_view_background);
        background.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background, options));
    }

    public void startGame(View view) {
        questions = QuestionsUtil.fetchQuestions();
        displayNameDialog();
    }

    private void displayNameDialog() {
        AlertDialog nameDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.name_dialog_title)
                .setView(R.layout.dialog_name_content)
                .setPositiveButton(R.string.ok, null)
                .create();

        nameDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editTextName = (EditText) ((AlertDialog) dialog).findViewById(R.id.edit_text_name);
                        if (TextUtils.isEmpty(editTextName.getText())) {
                            Toast.makeText(MainActivity.this, R.string.no_name_message, Toast.LENGTH_SHORT).show();
                        } else {
                            playerName = editTextName.getText().toString();

                            dialog.dismiss();
                            setUpGame();
                            nextQuestion();

                            btnNewGame.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);

                            quizProgressBar.setVisibility(View.VISIBLE);
                            quizProgressBar.setMax(questions.size());
                            quizProgressBar.setProgress(0);
                        }
                    }
                });
            }
        });

        nameDialog.show();
    }

    private void setUpGame() {
        currentQuestion = -1;
        currentScore = 0;

        checkboxGroup.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);

        textViewQuestion.setText(null);

        recyclerViewScore.setVisibility(View.GONE);
    }

    private void clearButtons() {
        for (CompoundButton answerCheckbox : answerCheckboxes) {
            answerCheckbox.setChecked(false);
        }

        radioGroup.clearCheck();
    }

    private void nextQuestion() {
        currentQuestion++;
        quizProgressBar.setProgress(currentQuestion);
        clearButtons();

        if (currentQuestion < questions.size()) {
            Question question = questions.getQuestion(currentQuestion);
            textViewQuestion.setText(question.getText());
            displayAnswers(question);
        } else {
            endGame();
        }
    }

    private void endGame() {
        btnNewGame.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);

        quizProgressBar.setVisibility(View.GONE);

        scoreNodes.add(new ScoreNode(playerName, currentScore));
        recyclerViewScore.getAdapter().notifyDataSetChanged();

        setUpGame();

        recyclerViewScore.setVisibility(View.VISIBLE);
    }

    private void displayAnswers(Question question) {
        if (question.isMultipleAnswer()) {
            checkboxGroup.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.GONE);
            displayAnswerButtons(answerCheckboxes);
        } else {
            radioGroup.setVisibility(View.VISIBLE);
            checkboxGroup.setVisibility(View.GONE);
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

        if (questions.getQuestion(currentQuestion).isMultipleAnswer()) {
            isCorrectAnswer = isCorrectAnswer(answerCheckboxes);
        } else {
            isCorrectAnswer = isCorrectAnswer(answerRadios);
        }

        if (isCorrectAnswer) {
            currentScore += 10;
            nextQuestion();

            if (currentQuestion != -1) {
                Toast.makeText(this, R.string.right_answer_message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.end_game_message, Toast.LENGTH_SHORT).show();
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

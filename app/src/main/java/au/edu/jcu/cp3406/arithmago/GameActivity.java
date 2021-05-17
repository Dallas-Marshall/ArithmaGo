package au.edu.jcu.cp3406.arithmago;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import au.edu.jcu.cp3406.arithmago.gamelogic.ArithmaGoGame;

public class GameActivity extends AppCompatActivity {
    private Button answerButton01;
    private Button answerButton02;
    private Button answerButton03;
    private TextView equationDisplay;
    private ProgressBar lifeBar;
    private TextView scoreDisplay;
    private Locale locale;

    // App Variables
    private SharedPreferences dataSource;

    private ArithmaGoGame game;
    private int progress = 100;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        locale = Locale.getDefault();

        // Load SharedPreferences
        dataSource = getSharedPreferences("ArithmaGo_Variables", Context.MODE_PRIVATE);

        answerButton01 = findViewById(R.id.answer1);
        answerButton02 = findViewById(R.id.answer2);
        answerButton03 = findViewById(R.id.answer3);
        equationDisplay = findViewById(R.id.equationDisplay);
        setupGame();
        displayNextQuestion();
        scoreDisplay = findViewById(R.id.scoreDisplay);

        lifeBar = findViewById(R.id.lifeBar);
        progress = lifeBar.getProgress();
        new Thread(() -> {
            while (progress > 0) {
                progress -= 1;
                handler.post(() -> lifeBar.setProgress(progress));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setupGame() {
        boolean isMultiplicationEnabled = dataSource.getBoolean("isMultiplicationEnabled", true);
        boolean isDivisionEnabled = dataSource.getBoolean("isDivisionEnabled", true);
        boolean isAdditionEnabled = dataSource.getBoolean("isAdditionEnabled", true);
        boolean isSubtractionEnabled = dataSource.getBoolean("isSubtractionEnabled", true);
        String level = dataSource.getString("level", "Medium");
        boolean isDifficultyLocked;
        if (level.equals("Dynamic")) {
            isDifficultyLocked = false;
            level = "Medium";
        } else {
            isDifficultyLocked = true;
        }

        game = new ArithmaGoGame(level, isDifficultyLocked, isMultiplicationEnabled,
                isDivisionEnabled, isAdditionEnabled, isSubtractionEnabled);
    }


    private void displayNextQuestion() {
        String eqn = game.nextQuestion();
        String[] possibleAnswers = game.getPossibleAnswers();

        equationDisplay.setText(eqn);
        // Randomly set the three possible answers
        int randomNumber = (int) (Math.random() * 6) + 1;
        switch (randomNumber) {
            case 1:
                answerButton01.setText(possibleAnswers[0]);
                answerButton02.setText(possibleAnswers[1]);
                answerButton03.setText(possibleAnswers[2]);
                break;
            case 2:
                answerButton01.setText(possibleAnswers[0]);
                answerButton02.setText(possibleAnswers[2]);
                answerButton03.setText(possibleAnswers[1]);
                break;
            case 3:
                answerButton01.setText(possibleAnswers[1]);
                answerButton02.setText(possibleAnswers[2]);
                answerButton03.setText(possibleAnswers[0]);
                break;
            case 4:
                answerButton01.setText(possibleAnswers[2]);
                answerButton02.setText(possibleAnswers[1]);
                answerButton03.setText(possibleAnswers[0]);
                break;
            case 5:
                answerButton01.setText(possibleAnswers[2]);
                answerButton02.setText(possibleAnswers[0]);
                answerButton03.setText(possibleAnswers[1]);
                break;
            case 6:
                answerButton01.setText(possibleAnswers[1]);
                answerButton02.setText(possibleAnswers[0]);
                answerButton03.setText(possibleAnswers[2]);
                break;
        }
    }

    public void answerSelected(View view) {
        Button buttonPressed = (Button) view;
        int selectedAnswer = Integer.parseInt(buttonPressed.getText().toString());
        boolean isCorrect = game.checkAnswer(selectedAnswer);
        Log.i("Score", String.format(locale, "%b", isCorrect));

        if (isCorrect) {
            scoreDisplay.setText(String.format(locale, "%d", game.getScore()));
            Log.i("Score", String.format(locale, "%d", game.getScore()));
        }
        displayNextQuestion();
    }
}

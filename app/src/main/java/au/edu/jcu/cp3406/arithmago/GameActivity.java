package au.edu.jcu.cp3406.arithmago;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import au.edu.jcu.cp3406.arithmago.gamelogic.ArithmaGoGame;

public class GameActivity extends AppCompatActivity {
    private Button answerButton01;
    private Button answerButton02;
    private Button answerButton03;
    private TextView equationDisplay;

    // App Variables
    private SharedPreferences dataSource;

    private ArithmaGoGame game;
    private String eqn;
    private String[] possibleAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Load SharedPreferences
        dataSource = getSharedPreferences("ArithmaGo_Variables", Context.MODE_PRIVATE);

        answerButton01 = findViewById(R.id.answer1);
        answerButton02 = findViewById(R.id.answer2);
        answerButton03 = findViewById(R.id.answer3);
        equationDisplay = findViewById(R.id.equationDisplay);

        setupGame();

        updateQuestionDisplay();
    }

    private void setupGame() {
        boolean isMultiplicationEnabled = dataSource.getBoolean("isMultiplicationEnabled", true);
        boolean isDivisionEnabled = dataSource.getBoolean("isDivisionEnabled", true);
        boolean isAdditionEnabled = dataSource.getBoolean("isAdditionEnabled", true);
        boolean isSubtractionEnabled = dataSource.getBoolean("isSubtractionEnabled", true);
        String level = dataSource.getString("level", "Medium");
        boolean isDifficultyLocked = !level.equals("Dynamic");

        game = new ArithmaGoGame(level, isDifficultyLocked, isMultiplicationEnabled,
                isDivisionEnabled, isAdditionEnabled, isSubtractionEnabled);
        eqn = game.nextQuestion();
        possibleAnswers = game.getPossibleAnswers();
    }


    private void updateQuestionDisplay() {
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
}

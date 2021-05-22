package au.edu.jcu.cp3406.arithmago;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Objects;

import au.edu.jcu.cp3406.arithmago.database.ArithmaGoDatabaseAdapter;
import au.edu.jcu.cp3406.arithmago.gamelogic.ArithmaGoGame;
import au.edu.jcu.cp3406.arithmago.sound.AudioManager;
import au.edu.jcu.cp3406.arithmago.sound.Sound;

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
    private ArithmaGoDatabaseAdapter database;

    private ArithmaGoGame game;
    private int progress = 100;
    private final Handler handler = new Handler();
    private AudioManager audioManager;

    public static final int SHAKE_THRESHOLD = 15;
    private boolean isSkipUsed;
    private SensorManager sensorManager;
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        locale = Locale.getDefault();

        // Load SharedPreferences
        dataSource = getSharedPreferences("ArithmaGo_Variables", Context.MODE_PRIVATE);
        database = new ArithmaGoDatabaseAdapter(this);
        audioManager = new AudioManager(this);

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
            addLeaderboardRecord();
            // Move to results activity with necessary variables
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("numberOfIncorrectAnswers", game.getNumberOfIncorrectAnswers());
            intent.putExtra("numberOfCorrectAnswers", game.getNumberOfCorrectAnswers());
            intent.putExtra("score", game.getScore());
            startActivity(intent);
            finish();
        }).start();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(sensorManager).registerListener(shakeEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        acceleration = 10f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;
        isSkipUsed = false;
    }

    public void addLeaderboardRecord() {
        String username = dataSource.getString("username", "Guest");
        String level = dataSource.getString("level", "Dynamic");
        int score = game.getScore();
        database.insertData(username, score, level);
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioManager.resume();
        sensorManager.registerListener(shakeEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private final SensorEventListener shakeEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            lastAcceleration = currentAcceleration;
            currentAcceleration = (float) Math.sqrt((x * x + y * y + z * z));

            float delta = currentAcceleration - lastAcceleration;
            acceleration = (float) (acceleration * 0.9 + delta);
            if (acceleration > SHAKE_THRESHOLD) {
                if (!isSkipUsed) {
                    displayNextQuestion();
                    isSkipUsed = true;
                    Toast.makeText(getApplicationContext(), "Skipped", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No skips remaining.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        audioManager.pause();
        sensorManager.unregisterListener(shakeEventListener);
    }

    private void setupGame() {
        String level = dataSource.getString("level", "Medium");
        boolean isDifficultyLocked;
        if (level.equals("Dynamic")) {
            isDifficultyLocked = false;
            level = "Medium";
        } else {
            isDifficultyLocked = true;
        }
        game = new ArithmaGoGame(level, isDifficultyLocked);
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
        double selectedAnswer = Double.parseDouble(buttonPressed.getText().toString());
        boolean isCorrect = game.checkAnswer(selectedAnswer);

        if (isCorrect) {
            if (audioManager.isReady()) {
                audioManager.play(Sound.CORRECT);
            }
            scoreDisplay.setText(String.format(locale, "%d", game.getScore()));
            progress += 2;
            if (progress > 100) {
                progress = 100;
            }
        } else {
            if (audioManager.isReady()) {
                audioManager.play(Sound.INCORRECT);
            }
            progress -= 10;
        }
        displayNextQuestion();
    }

    /**
     * Stops the activity when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}

package au.edu.jcu.cp3406.arithmago;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ResultsActivity extends AppCompatActivity {
    private Locale locale;
    private SharedPreferences dataSource;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        locale = Locale.getDefault();

        // Load App Variables and values from intent
        dataSource = getSharedPreferences("ArithmaGo_Variables", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        int numberOfCorrectAnswers = intent.getIntExtra("numberOfCorrectAnswers", 0);
        int numberOfIncorrectAnswers = intent.getIntExtra("numberOfIncorrectAnswers", 0);
        score = intent.getIntExtra("score", 0);

        TextView numberCorrectDisplay = findViewById(R.id.numberCorrectDisplay);
        TextView numberIncorrectDisplay = findViewById(R.id.numberIncorrectDisplay);
        TextView finalScoreDisplay = findViewById(R.id.finalScoreDisplay);

        numberCorrectDisplay.setText(String.format(locale, "%d", numberOfCorrectAnswers));
        numberIncorrectDisplay.setText(String.format(locale, "%d", numberOfIncorrectAnswers));
        finalScoreDisplay.setText(String.format(locale, "%d", score));
    }

    /**
     * Handles options menu item being selected.
     *
     * @param item MenuItem - options menu item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO: Route to Homepage
                finish();
                return true;
            case (R.id.shareIcon):
                shareOptionSelected();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareOptionSelected() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        String username = dataSource.getString("username", "Guest");
        String messageToDisplay = String.format(locale, "%s just scored %d on ArithmaGo! Can you top that?", username, score);
        sendIntent.putExtra(Intent.EXTRA_TEXT, messageToDisplay);
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    /**
     * Adds the share icon to the options menu.
     *
     * @param menu Menu - the options menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Stops the activity when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void toLeaderboard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }
}
package au.edu.jcu.cp3406.arithmago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Locale locale = Locale.getDefault();

        Intent intent = getIntent();
        int numberOfCorrectAnswers = intent.getIntExtra("numberOfCorrectAnswers", 0);
        int numberOfIncorrectAnswers = intent.getIntExtra("numberOfIncorrectAnswers", 0);
        int score = intent.getIntExtra("score", 0);

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
                // finish();
                return true;
            case (R.id.shareIcon):
                // TODO: Implement social media sharing
        }

        return super.onOptionsItemSelected(item);
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
        // TODO: Route to Homepage
        // finish();
        super.onBackPressed();
    }

    public void toLeaderboard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }
}
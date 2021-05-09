package au.edu.jcu.cp3406.arithmago;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class LeaderboardActivity extends AppCompatActivity {
    private Random random;
    private Locale locale;
    private static final int[] avatars = {
            R.drawable.avatar_01,
            R.drawable.avatar_02,
            R.drawable.avatar_03,
            R.drawable.avatar_04,
            R.drawable.avatar_05,
            R.drawable.avatar_06,
            R.drawable.avatar_07,
            R.drawable.avatar_08
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        locale = Locale.getDefault();
        random = new Random();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        populateLeaderboard();
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
                finish();
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
        finish();
        super.onBackPressed();
    }

    /**
     * Adds top 10 high scores to leaderboardRows.
     */
    private void populateLeaderboard() {
        // TODO: Sort db
        // TODO: Fetch winner username from db
        // TODO: Fetch winner highScore from db
        addLeaderboardWinner("mobpuncher1", 10237);
        for (int position = 2; position < 11; position++) { // TODO: Iterate through every record in cursor
            // TODO: Fetch username from db
            // TODO: Fetch highScore from db
            addLeaderboardRow(position, "mobpuncher1", (10000 - (position * 361)));
        }
    }

    /**
     * Add formatted leaderboard_slot_winner view element to leaderboardRows.
     *
     * @param username  String - Username of winner.
     * @param highScore int - High score of winner.
     */
    private void addLeaderboardWinner(String username, int highScore) {
        // Inflate leaderboard_slot_winner into leaderboardRows
        ViewGroup leaderboardRows = findViewById(R.id.leaderboardRows);
        getLayoutInflater().inflate(R.layout.leaderboard_slot_winner, leaderboardRows);

        // Get View element just added to leaderboardRows
        View lastRow = leaderboardRows.getChildAt(leaderboardRows.getChildCount() - 1);

        // Set Username
        TextView usernameDisplay = lastRow.findViewById(R.id.usernameDisplay);
        usernameDisplay.setText(username);

        // Set HighScore
        TextView highScoreDisplay = lastRow.findViewById(R.id.highScoreDisplay);
        highScoreDisplay.setText(formatHighScore(highScore));

        // Set random Image
        int randIndex = random.nextInt(avatars.length);
        ImageView avatar = lastRow.findViewById(R.id.avatar);
        avatar.setImageDrawable(getResources().getDrawableForDensity(avatars[randIndex], 0));
    }

    /**
     * Add formatted leaderboard_slot view element to leaderboardRows.
     *
     * @param position  int - Position in top 10.
     * @param username  String - Username of player.
     * @param highScore int - High score of player.
     */
    private void addLeaderboardRow(int position, String username, int highScore) {
        // Inflate leaderboard_slot into leaderboardRows
        ViewGroup leaderboardRows = findViewById(R.id.leaderboardRows);
        getLayoutInflater().inflate(R.layout.leaderboard_slot, leaderboardRows);

        // Get View element just added to leaderboardRows
        View lastRow = leaderboardRows.getChildAt(leaderboardRows.getChildCount() - 1);

        // Set positionNumber
        TextView positionNumber = lastRow.findViewById(R.id.positionNumber);
        String pos = String.format(locale, "%s", position);
        positionNumber.setText(pos);

        // Set Username
        TextView usernameDisplay = lastRow.findViewById(R.id.usernameDisplay);
        usernameDisplay.setText(username);

        // Set HighScore
        TextView highScoreDisplay = lastRow.findViewById(R.id.highScoreDisplay);
        highScoreDisplay.setText(formatHighScore(highScore));

        // Set random Image
        int randIndex = random.nextInt(avatars.length);
        ImageView avatar = lastRow.findViewById(R.id.avatar);
        avatar.setImageDrawable(getResources().getDrawableForDensity(avatars[randIndex], 0));

        // Set Medal Colours
        if (position == 2) {        // Silver Medal
            int silver = Color.rgb(197, 197, 197);
            lastRow.setBackgroundColor(silver);
            int davys_grey = Color.rgb(71, 71, 71);
            positionNumber.setTextColor(davys_grey);
            usernameDisplay.setTextColor(davys_grey);
            highScoreDisplay.setTextColor(davys_grey);
        } else if (position == 3) { // Bronze Medal
            int copper_crayola = Color.rgb(221, 144, 114);
            lastRow.setBackgroundColor(copper_crayola);
        }
    }

    /**
     * Convert int score into String with a comma after every 3 digits.
     *
     * @param score int - High score to be formatted
     * @return String - Formatted with a comma after every 3 digits
     */
    private String formatHighScore(int score) {
        StringBuilder formattedHighScore = new StringBuilder();
        String highScore = String.format(locale, "%d", score);
        int numLength = highScore.length();
        for (int i = 0; i < numLength; i++) {
            if ((numLength - i) % 3 == 0 && i != 0) {
                formattedHighScore.append(",");
            }
            formattedHighScore.append(highScore.charAt(i));
        }
        return formattedHighScore.toString();
    }
}


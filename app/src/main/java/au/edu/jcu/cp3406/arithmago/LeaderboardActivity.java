package au.edu.jcu.cp3406.arithmago;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
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

        populateLeaderboard();
    }

    private void populateLeaderboard() {
        for (int position = 2; position < 11; position++) {
            // TODO: Fetch username from db
            // TODO: Fetch highScore from db
            addLeaderboardRow(position, "mobpuncher1", (10000 - (position * 361)));
        }
    }

    public void addLeaderboardRow(int position, String username, int highScore) {
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
        String score = String.format(locale, "%d", highScore);
        highScoreDisplay.setText(score);

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
}


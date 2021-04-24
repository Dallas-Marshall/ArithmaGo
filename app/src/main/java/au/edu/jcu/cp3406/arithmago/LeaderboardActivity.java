package au.edu.jcu.cp3406.arithmago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;

public class LeaderboardActivity extends AppCompatActivity {
    private ViewGroup leaderboardRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addLeaderboardRow();
    }

    public void addLeaderboardRow() {
        // Inflate leaderboard_slot into leaderboardRows
        leaderboardRows = findViewById(R.id.leaderboardRows);
        getLayoutInflater().inflate(R.layout.leaderboard_slot, leaderboardRows);
    }
}


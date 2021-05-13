package au.edu.jcu.cp3406.arithmago;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    // Intent Variables
    public static final int SETTINGS_REQUEST = 1;
    public static final int GAME_REQUEST = 2;

    // App Variables
    private SharedPreferences dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dataSource = getSharedPreferences("ArithmaGo_Variables", Context.MODE_PRIVATE);

        if (dataSource.getString("level", "Not Initialised").equals("Not Initialised")) { // No saved dataSource
            // Set defaults
            SharedPreferences.Editor editor = dataSource.edit();
            editor.putString("level", "Dynamic");
            editor.putBoolean("isMultiplicationEnabled", true);
            editor.putBoolean("isDivisionEnabled", true);
            editor.putBoolean("isAdditionEnabled", true);
            editor.putBoolean("isSubtractionEnabled", true);
            editor.putString("username", "Guest");
            editor.apply(); // Save changes
        }
        if (savedInstanceState != null) { // Previously saved state
            // Load saved variables
            SharedPreferences.Editor editor = dataSource.edit();
            editor.putString("level", savedInstanceState.getString("level"));
            editor.putBoolean("isMultiplicationEnabled", savedInstanceState.getBoolean("isMultiplicationEnabled"));
            editor.putBoolean("isDivisionEnabled", savedInstanceState.getBoolean("isDivisionEnabled"));
            editor.putBoolean("isAdditionEnabled", savedInstanceState.getBoolean("isAdditionEnabled"));
            editor.putBoolean("isSubtractionEnabled", savedInstanceState.getBoolean("isSubtractionEnabled"));
            editor.putString("username", savedInstanceState.getString("username"));
            editor.apply(); // Save changes
        }
    }

    public void leaderboardSelected(View buttonClicked) {
        // Start LeaderboardActivity
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void settingsSelected(View view) {
        // Start SettingsActivity
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void playSelected(View view) {
        // Start gameActivity
        Intent intent = new Intent(this, GameActivity.class);
        startActivityForResult(intent, GAME_REQUEST);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("level", dataSource.getString("level", "Medium"));
        outState.putBoolean("isMultiplicationEnabled", dataSource.getBoolean("isMultiplicationEnabled", true));
        outState.putBoolean("isDivisionEnabled", dataSource.getBoolean("isDivisionEnabled", true));
        outState.putBoolean("isAdditionEnabled", dataSource.getBoolean("isAdditionEnabled", true));
        outState.putBoolean("isSubtractionEnabled", dataSource.getBoolean("isSubtractionEnabled", true));
        outState.putString("username", dataSource.getString("username", "Guest"));
    }
}
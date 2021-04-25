package au.edu.jcu.cp3406.arithmago;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    // Intent Variables
    public static final int SETTINGS_REQUEST = 1;

    // App Variables
    private String speed;
    private boolean isMultiplicationEnabled;
    private boolean isDivisionEnabled;
    private boolean isAdditionEnabled;
    private boolean isSubtractionEnabled;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            // Set defaults
            speed = "Normal";
            isMultiplicationEnabled = true;
            isDivisionEnabled = true;
            isAdditionEnabled = true;
            isSubtractionEnabled = true;
            username = "Guest";
        } else {
            // Load saved variables
            speed = savedInstanceState.getString("speed");
            isMultiplicationEnabled = savedInstanceState.getBoolean("isMultiplicationEnabled");
            isDivisionEnabled = savedInstanceState.getBoolean("isDivisionEnabled");
            isAdditionEnabled = savedInstanceState.getBoolean("isAdditionEnabled");
            isSubtractionEnabled = savedInstanceState.getBoolean("isSubtractionEnabled");
            username = savedInstanceState.getString("username");
        }
    }

    public void leaderboardSelected(View buttonClicked) {
        // Start LeaderboardActivity
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void settingsSelected(View view) {
        // Start SettingsActivity
//        Log.i("Dallas", "settingsSelected: " + speed + " " + isMultiplicationEnabled + " " + isDivisionEnabled + " " + isAdditionEnabled + " " + isSubtractionEnabled + " " + username);
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("speed", speed);
        intent.putExtra("isMultiplicationEnabled", isMultiplicationEnabled);
        intent.putExtra("isDivisionEnabled", isDivisionEnabled);
        intent.putExtra("isAdditionEnabled", isAdditionEnabled);
        intent.putExtra("isSubtractionEnabled", isSubtractionEnabled);
        intent.putExtra("username", username);
        startActivityForResult(intent, SETTINGS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                speed = data.getStringExtra("speed");
                isMultiplicationEnabled = data.getBooleanExtra("isMultiplicationEnabled", true);
                isDivisionEnabled = data.getBooleanExtra("isDivisionEnabled", true);
                isAdditionEnabled = data.getBooleanExtra("isAdditionEnabled", true);
                isSubtractionEnabled = data.getBooleanExtra("isSubtractionEnabled", true);
                username = data.getStringExtra("username");
//                Log.i("Dallas", "onActivityResult: " + speed + " " + isMultiplicationEnabled + " " + isDivisionEnabled + " " + isAdditionEnabled + " " + isSubtractionEnabled + " " + username);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("speed", speed);
        outState.putBoolean("isMultiplicationEnabled", isMultiplicationEnabled);
        outState.putBoolean("isDivisionEnabled", isDivisionEnabled);
        outState.putBoolean("isAdditionEnabled", isAdditionEnabled);
        outState.putBoolean("isSubtractionEnabled", isSubtractionEnabled);
        outState.putString("username", username);
    }
}
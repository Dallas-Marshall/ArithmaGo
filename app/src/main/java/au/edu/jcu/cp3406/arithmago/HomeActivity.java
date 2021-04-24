package au.edu.jcu.cp3406.arithmago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    public void leaderboardSelected(View buttonClicked) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }
}
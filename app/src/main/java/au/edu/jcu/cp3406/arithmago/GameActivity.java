package au.edu.jcu.cp3406.arithmago;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import au.edu.jcu.cp3406.arithmago.skyview.Cloud;
import au.edu.jcu.cp3406.arithmago.skyview.Plane;
import au.edu.jcu.cp3406.arithmago.skyview.SkyView;
import au.edu.jcu.cp3406.arithmago.skyview.Utilities;

public class GameActivity extends AppCompatActivity {
    private Handler mainHandler;
    private Runnable redraw;
    private boolean isRedrawing;
    private SkyView skyView;
    private List<Cloud> clouds;
    private List<Plane> planes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        clouds = new ArrayList<>();
        planes = new ArrayList<>();

        skyView = findViewById(R.id.skyView);
        displayShields();

        // Draw skyView elements once view dimensions are set
        skyView.post(new Runnable() {
            @Override
            public void run() {
                int width = skyView.getWidth();
                int height = skyView.getHeight();
                drawElements(skyView);
            }
        });
        // setup redrawing
        mainHandler = new Handler();
        redraw = new Runnable() {
            @Override
            public void run() {
                if (isRedrawing) {
                    moveClouds();
                    skyView.invalidate();
                    mainHandler.postDelayed(redraw, 1);
                }
            }
        };

    }

    private void displayShields() {
        // Inflate leaderboard_slot_winner into leaderboardRows
        ViewGroup leaderboardRows = findViewById(R.id.shieldsDisplay);
        getLayoutInflater().inflate(R.layout.shield_display, leaderboardRows);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRedrawing = true;
        mainHandler.post(redraw);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRedrawing = false;
    }

    public void drawElements(View view) {
        int boundingWidth = skyView.getWidth();
        int boundingHeight = skyView.getHeight();
        float scale = 0.1f;
        for (int i = 150; i <= 1500; i += 150) {
            clouds.add(new Cloud(createBitmap(scale, R.drawable.cloud_filled), boundingWidth + i, boundingHeight));
        }
        skyView.setClouds(clouds);
        skyView.setPlane(new Plane(createBitmap(scale, R.drawable.airplane), boundingWidth, boundingHeight));
    }

    private void moveClouds() {
        for (Cloud cloud : clouds) {
            cloud.move();
        }
    }

    private Bitmap createBitmap(float scale, int imageID) {
        Point size = Utilities.computeSizeInDP(getWindowManager(), 0.5f);
        Bitmap bitmap = Utilities.decodeBitmap(getResources(), imageID, size);
        int width = Math.round(bitmap.getWidth() * scale);
        int height = Math.round(bitmap.getHeight() * scale);
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}

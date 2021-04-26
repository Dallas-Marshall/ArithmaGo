package au.edu.jcu.cp3406.arithmago;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import au.edu.jcu.cp3406.arithmago.Cloud;
import au.edu.jcu.cp3406.arithmago.R;
import au.edu.jcu.cp3406.arithmago.SkyView;
import au.edu.jcu.cp3406.arithmago.Utilities;

public class GameActivity extends AppCompatActivity {
    private Handler mainHandler;
    private Runnable redraw;
    private boolean isRedrawing;
    private Button add, remove;
    private SkyView skyView;
    private List<Cloud> clouds;
//    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        audioManager = new AudioManager(this);
        clouds = new ArrayList<>();

        // setup redrawing
        mainHandler = new Handler();
        skyView = findViewById(R.id.skyView);
        skyView.setClouds(clouds);
        redraw = new Runnable() {
            @Override
            public void run() {
                if (isRedrawing) {
                    moveDucks();
                    skyView.invalidate();
                    mainHandler.postDelayed(redraw, 24);
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        isRedrawing = true;
        mainHandler.post(redraw);
//        audioManager.resume();
        setClouds();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRedrawing = false;
//        audioManager.pause();
    }

    public void setClouds() {
        Random random = new Random();

                int width = skyView.getWidth();
                int height = skyView.getHeight();
                float scale = 0.25f + random.nextFloat() * (1 - 0.25f);
                clouds.add(new Cloud(createBitmap(scale), width, height));
    }

    private void moveDucks() {
        for (Cloud cloud : clouds) {
            boolean bounced = cloud.move();
            if (bounced) {
                Random random = new Random();
                float speed = 0.5f + random.nextFloat() * (2 - 0.5f); // range: [0.5,2)
                float volume = 0.5f + random.nextFloat() * (1 - 0.5f); // [0.5, 1)
//                audioManager.playSound(speed, volume);
            }
        }
    }

    private Bitmap createBitmap(float scale) {
        Point size = Utilities.computeSizeInDP(getWindowManager(), 0.5f);
        Bitmap bitmap = Utilities.decodeBitmap(getResources(), R.drawable.cloud, size);
        int width = Math.round(bitmap.getWidth() * scale);
        int height = Math.round(bitmap.getHeight() * scale);
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}

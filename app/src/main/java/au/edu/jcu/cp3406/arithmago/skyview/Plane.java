package au.edu.jcu.cp3406.arithmago.skyview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;

import java.util.Random;

public class Plane {
    private int x;
    private final int color;
    private final Bitmap bitmap;

    public Plane(Bitmap bitmap, int boundingWidth, int boundingHeight) {
        this.bitmap = bitmap;

        Random random = new Random();
        x = random.nextInt(boundingWidth);
        int y = random.nextInt(boundingHeight);
        color = Color.BLACK;
    }


    void draw(Canvas canvas) {
        canvas.save();
        float top = 0f;
        float left = 50f;

        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restore();
    }

    public boolean move() {
        x += 5;
        return true;
    }
}
package au.edu.jcu.cp3406.arithmago;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;

import java.util.Random;

public class Plane {
    private int x, y, boundingWidth, boundingHeight, speed;
    private int color;
    private Bitmap bitmap;

    public Plane(Bitmap bitmap, int boundingWidth, int boundingHeight) {
        this.bitmap = bitmap;
        this.boundingWidth = boundingWidth;
        this.boundingHeight = boundingHeight;

        Random random = new Random();
        x = random.nextInt(boundingWidth);
        y = random.nextInt(boundingHeight);
        speed = 1;
        color = Color.rgb(235, 235, 235);
    }


    void draw(Canvas canvas) {
        canvas.save();

        float top = 0f;
        float left = 50f;

        Log.i("Placements", "width: " + top + ", height: " + left);


        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, left, top, paint);

        canvas.restore();
    }

    public boolean move() {
        boolean bounced = false;
        x -= speed;

        if (x < 1) {
            bounced = true;
        }
        return bounced;
    }
}

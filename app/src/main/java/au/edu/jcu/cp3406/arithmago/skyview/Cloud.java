package au.edu.jcu.cp3406.arithmago.skyview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import java.util.Random;

public class Cloud {
    private int x;
    private final int y;
    private final int boundingWidth;
    private final int speed;
    private int color;
    private final Bitmap bitmap;

    public Cloud(Bitmap bitmap, int boundingWidth, int boundingHeight) {
        this.bitmap = bitmap;
        this.boundingWidth = boundingWidth;

        Random random = new Random();
        x = boundingWidth + 100;
        y = random.nextInt(boundingHeight) / 2 + 50;
        speed = 1;
        setColor();
    }

    private void setColor() {
        Random random = new Random();
        int chance = random.nextInt(4);
        if (chance < 1) {
            color = Color.rgb(153, 153, 153);
        } else if (chance < 3) {
            color = Color.rgb(230, 230, 230);
        } else {
            color = Color.rgb(245, 247, 242);
        }
    }


    void draw(Canvas canvas) {
        canvas.save();

        float top = y - bitmap.getWidth() / 2.0f;
        float left = x - bitmap.getHeight() / 2.0f;

        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, left, top, paint);

        canvas.restore();
    }

    public void move() {
        boolean hasPassed = false;
        x -= speed;

        if (x < -100) {
            x = boundingWidth + 165;
        }
    }
}

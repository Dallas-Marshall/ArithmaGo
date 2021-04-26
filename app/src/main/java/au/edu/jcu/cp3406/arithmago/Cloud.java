package au.edu.jcu.cp3406.arithmago;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import java.util.Random;

public class Cloud {
    private int x, y, boundingWidth, boundingHeight, speed;
    private int color;
    private Bitmap bitmap;

    public Cloud(Bitmap bitmap, int boundingWidth, int boundingHeight) {
        this.bitmap = bitmap;
        this.boundingWidth = boundingWidth;
        this.boundingHeight = boundingHeight;

        Random random = new Random();
        x = random.nextInt(boundingWidth);
        y = random.nextInt(boundingHeight);
        speed = 10;
        color = Color.rgb(236, 242, 254);
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

    public boolean move() {
        boolean bounced = false;
        x -= speed;

        if (x < 1) {
//            xDir *= -1;
            bounced = true;
        }
        return bounced;
    }
}

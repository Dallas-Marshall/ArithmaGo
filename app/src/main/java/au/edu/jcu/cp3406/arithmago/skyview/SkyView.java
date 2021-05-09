package au.edu.jcu.cp3406.arithmago.skyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class SkyView extends View {
    private int width;
    private int height;
    private List<Cloud> clouds;
    private Plane plane;

    public SkyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        width = newWidth;
        height = newHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawSkyTones(canvas);

        drawClouds(canvas);

        drawPlane(canvas);
    }

    private void drawSkyTones(Canvas canvas) {
        int h = height / 6;
        Paint skyPaint = new Paint();
        int[] colors = {
                Color.rgb(171, 196, 255),
                Color.rgb(182, 204, 254),
                Color.rgb(193, 211, 254),
                Color.rgb(204, 219, 253),
                Color.rgb(215, 227, 252),
                Color.rgb(58, 90, 64)};

        for (int i = 1; i < 7; i++) {
            skyPaint.setColor(colors[i - 1]);
            canvas.drawRect(0, (i - 1) * h, width, i * h, skyPaint);
        }
    }


    private void drawClouds(Canvas canvas) {
        if (clouds != null) {
            for (Cloud cloud : clouds) {
                cloud.draw(canvas);
            }
        }
    }

    private void drawPlane(Canvas canvas) {
        if (plane != null) {
            plane.draw(canvas);
        }
    }

    public void setClouds(List<Cloud> clouds) {
        this.clouds = clouds;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }
}

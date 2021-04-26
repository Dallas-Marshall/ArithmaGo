package au.edu.jcu.cp3406.arithmago;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utilities {
    public static Point computeSizeInDP(WindowManager windowManager, float scale) {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        int width = Math.round(metrics.widthPixels / metrics.density * scale);
        int height = Math.round(metrics.heightPixels / metrics.density * scale);
        return new Point(width, height);
    }

    public static Bitmap decodeBitmap(Resources res, int resId, Point requiredSize) {
        // Check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = computeSize(options, requiredSize);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int computeSize(BitmapFactory.Options options, Point requiredSize) {
        int height = options.outHeight;
        int width = options.outWidth;
        int size = 1;

        // Calculate the largest size value that is a power of 2 and keeps both
        // height and width larger than the requested size
        if (height > requiredSize.y || width > requiredSize.x) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            while ((halfHeight / size) > requiredSize.y
                    && (halfWidth / size) > requiredSize.x) {
                size *= 2;
            }
        }

        return size;
    }
}

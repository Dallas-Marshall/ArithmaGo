package au.edu.jcu.cp3406.arithmago;

import android.content.Context;
import android.widget.Toast;

/**
 * Helper class to allow ArithmaGoDatabaseAdapter to display Toast's.
 */
public class Message {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
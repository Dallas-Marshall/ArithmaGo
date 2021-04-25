package au.edu.jcu.cp3406.arithmago;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Extension of ArrayAdapter to work with SpeedItem objects.
 */
public class SpeedAdapter extends ArrayAdapter<SpeedItem> {

    public SpeedAdapter(Context context, ArrayList<SpeedItem> speedList) {
        super(context, 0, speedList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.speed_spinner_row, parent, false);
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.speedSpinnerRowIcon);
        TextView textViewName = convertView.findViewById(R.id.speedSpinnerRowName);

        SpeedItem currentItem = getItem(position);
        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getSpeedIcon());
            textViewName.setText(currentItem.getSpeedName());
        }

        return convertView;
    }
}
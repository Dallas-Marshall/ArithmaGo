package au.edu.jcu.cp3406.arithmago.levelSpinner;

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

import au.edu.jcu.cp3406.arithmago.R;

/**
 * Extension of ArrayAdapter to work with LevelItem objects.
 */
public class LevelAdapter extends ArrayAdapter<LevelItem> {

    public LevelAdapter(Context context, ArrayList<LevelItem> levelList) {
        super(context, 0, levelList);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.level_spinner_row, parent, false);
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.levelSpinnerRowIcon);
        TextView textViewName = convertView.findViewById(R.id.levelSpinnerRowName);

        LevelItem currentItem = getItem(position);
        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getLevelIcon());
            textViewName.setText(currentItem.getLevelName());
        }

        return convertView;
    }
}
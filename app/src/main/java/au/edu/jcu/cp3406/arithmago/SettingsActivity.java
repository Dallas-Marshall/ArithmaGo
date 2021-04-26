package au.edu.jcu.cp3406.arithmago;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private ArrayList<SpeedItem> speedList;
    private SpeedAdapter speedAdapter;
    SpeedItem selectedSpeedItem;

    // View Elements
    private Spinner speedSpinner;
    private CheckBox multiplicationCheckBox;
    private CheckBox divisionCheckBox;
    private CheckBox additionCheckBox;
    private CheckBox subtractionCheckBox;
    private EditText usernameEntry;

    // App Variables
    private SharedPreferences dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Load SharedPreferences
        dataSource = getSharedPreferences("ArithmaGo_Variables", Context.MODE_PRIVATE);

        // Disable up button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);

        generateSpeedList();// Define speedSpinner custom layouts
        setSelectedSpeedItem();// Set the selectedSpeedItem to match dataSource
        findActivityViews();
        speedSpinner = setupSpeedSpinner();
        updateCurrentViewState(); // Set view elements to match dataSource
        usernameEntry.addTextChangedListener(textWatcher);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Add save_menu to ActionBar
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void generateSpeedList() {
        // Define speedSpinner custom layouts
        speedList = new ArrayList<>();
        speedList.add(new SpeedItem("Slow", R.drawable.tortise));
        speedList.add(new SpeedItem("Normal", R.drawable.stopwatch));
        speedList.add(new SpeedItem("Fast", R.drawable.rabbit));
        speedList.add(new SpeedItem("Lightning", R.drawable.lightning_bolt));
        speedList.add(new SpeedItem("God Speed", R.drawable.jesus));
    }

    private void setSelectedSpeedItem() {
        // Set the selectedSpeedItem to match dataSource
        switch (dataSource.getString("speed", "Normal")) {
            case "Slow":
                selectedSpeedItem = speedList.get(0);
                break;
            case "Normal":
                selectedSpeedItem = speedList.get(1);
                break;
            case "Fast":
                selectedSpeedItem = speedList.get(2);
                break;
            case "Lightning":
                selectedSpeedItem = speedList.get(3);
                break;
            case "God Speed":
                selectedSpeedItem = speedList.get(4);
                break;
        }
    }

    private void findActivityViews() {
        speedSpinner = findViewById(R.id.speedSpinner);
        multiplicationCheckBox = findViewById(R.id.multiplicationCheckbox);
        divisionCheckBox = findViewById(R.id.divisionCheckbox);
        additionCheckBox = findViewById(R.id.additionCheckbox);
        subtractionCheckBox = findViewById(R.id.subtractionCheckbox);
        usernameEntry = findViewById(R.id.usernameEntry);
    }

    private Spinner setupSpeedSpinner() {
        speedSpinner = findViewById(R.id.speedSpinner);

        // Set SpeedAdapter to speedSpinner
        speedAdapter = new SpeedAdapter(this, speedList);
        speedSpinner.setAdapter(speedAdapter);

        // Set item Selected Listener
        speedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpeedItem = (SpeedItem) parent.getItemAtPosition(position);
                // Update speed in dataSource
                SharedPreferences.Editor editor = dataSource.edit();
                editor.putString("speed", selectedSpeedItem.getSpeedName());
                editor.apply(); // Save changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return speedSpinner;
    }

    private void updateCurrentViewState() {
        // Set view elements to match dataSource
        multiplicationCheckBox.setChecked(dataSource.getBoolean("isMultiplicationEnabled", true));
        divisionCheckBox.setChecked(dataSource.getBoolean("isDivisionEnabled", true));
        additionCheckBox.setChecked(dataSource.getBoolean("isAdditionEnabled", true));
        subtractionCheckBox.setChecked(dataSource.getBoolean("isSubtractionEnabled", true));
        usernameEntry.setText(dataSource.getString("username", "Guest"));
        speedSpinner.setSelection(speedAdapter.getPosition(selectedSpeedItem));
    }

    private final TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable text) {
            // Update username in dataSource
            SharedPreferences.Editor editor = dataSource.edit();
            editor.putString("username", text.toString());
            editor.apply(); // Save changes
        }

        public void beforeTextChanged(CharSequence text, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence text, int start, int before, int count) {
        }
    };

    @Override
    public void onBackPressed() {
        if (checkValidCheckboxSelection()) { // Minimum one operator selected
            saveCheckboxState();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (checkValidCheckboxSelection()) { // Minimum one operator selected
            if (item.getItemId() == R.id.save) {
                saveCheckboxState();
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCheckboxState() {
        SharedPreferences.Editor editor = dataSource.edit();
        editor.putBoolean("isMultiplicationEnabled", multiplicationCheckBox.isChecked());
        editor.putBoolean("isDivisionEnabled", divisionCheckBox.isChecked());
        editor.putBoolean("isAdditionEnabled", additionCheckBox.isChecked());
        editor.putBoolean("isSubtractionEnabled", subtractionCheckBox.isChecked());
        editor.apply(); // Save changes
    }

    private Boolean checkValidCheckboxSelection() {
        if ((!multiplicationCheckBox.isChecked()) && (!divisionCheckBox.isChecked())
                && (!additionCheckBox.isChecked()) && (!subtractionCheckBox.isChecked())) {
            Toast.makeText(this, "Must select minimum of 1 operator!", Toast.LENGTH_LONG).show();
            return false; // No operators Selected
        }
        return true;
    }
}
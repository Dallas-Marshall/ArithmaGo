package au.edu.jcu.cp3406.arithmago;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private String speed;
    private boolean isMultiplicationEnabled;
    private boolean isDivisionEnabled;
    private boolean isAdditionEnabled;
    private boolean isSubtractionEnabled;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Disable up button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);

        generateSpeedList();
        Intent intent = getIntent();
        readFromIntent(intent);
        defineSelectedSpeedItem();
        defineActivityViews();
        speedSpinner = setupSpeedSpinner();
        loadStoredValues();
        usernameEntry.addTextChangedListener(textWatcher);
    }

    private void defineSelectedSpeedItem() {
        switch (speed) {
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

    private void defineActivityViews() {
        speedSpinner = findViewById(R.id.speedSpinner);
        multiplicationCheckBox = findViewById(R.id.multiplicationCheckbox);
        divisionCheckBox = findViewById(R.id.divisionCheckbox);
        additionCheckBox = findViewById(R.id.additionCheckbox);
        subtractionCheckBox = findViewById(R.id.subtractionCheckbox);
        usernameEntry = findViewById(R.id.usernameEntry);
    }

    private void readFromIntent(Intent intent) {
        speed = intent.getStringExtra("speed");
        isMultiplicationEnabled = intent.getBooleanExtra("isMultiplicationEnabled", true);
        isDivisionEnabled = intent.getBooleanExtra("isDivisionEnabled", true);
        isAdditionEnabled = intent.getBooleanExtra("isAdditionEnabled", true);
        isSubtractionEnabled = intent.getBooleanExtra("isSubtractionEnabled", true);
        username = intent.getStringExtra("username");
    }

    private void generateSpeedList() {
        speedList = new ArrayList<>();
        speedList.add(new SpeedItem("Slow", R.drawable.tortise));
        speedList.add(new SpeedItem("Normal", R.drawable.stopwatch));
        speedList.add(new SpeedItem("Fast", R.drawable.rabbit));
        speedList.add(new SpeedItem("Lightning", R.drawable.lightning_bolt));
        speedList.add(new SpeedItem("God Speed", R.drawable.jesus));
    }

    private void loadStoredValues() {
        if (isMultiplicationEnabled) {
            multiplicationCheckBox.setChecked(true);
        }
        if (isDivisionEnabled) {
            divisionCheckBox.setChecked(true);
        }
        if (isAdditionEnabled) {
            additionCheckBox.setChecked(true);
        }
        if (isSubtractionEnabled) {
            subtractionCheckBox.setChecked(true);
        }
        usernameEntry.setText(username);
        speedSpinner.setSelection(speedAdapter.getPosition(selectedSpeedItem));
    }

    @Override
    public void onBackPressed() { // Stop user leaving settings without saving
        Toast.makeText(this, "You must click save!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            // Pass back variables in Intent
            Intent intent = new Intent();
            intent.putExtra("speed", speed);
            intent.putExtra("isMultiplicationEnabled", multiplicationCheckBox.isChecked());
            intent.putExtra("isDivisionEnabled", divisionCheckBox.isChecked());
            intent.putExtra("isAdditionEnabled", additionCheckBox.isChecked());
            intent.putExtra("isSubtractionEnabled", subtractionCheckBox.isChecked());
            intent.putExtra("username", usernameEntry.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private Spinner setupSpeedSpinner() {
        speedSpinner = findViewById(R.id.speedSpinner);
        speedAdapter = new SpeedAdapter(this, speedList);
        speedSpinner.setAdapter(speedAdapter);
        speedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpeedItem = (SpeedItem) parent.getItemAtPosition(position);
                speed = selectedSpeedItem.getSpeedName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return speedSpinner;
    }

    private final TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable text) {
//            Log.i("Dallas", "afterTextChanged: " + "Editable: " + text);
            username = text.toString();
        }

        public void beforeTextChanged(CharSequence text, int start, int count, int after) {
//            Log.i("Dallas", "beforeTextChanged: CharSequence: " + text + ", start: " + start + ", count: " + count + ", after: " + after);
        }

        public void onTextChanged(CharSequence text, int start, int before, int count) {
//            Log.i("Dallas", "beforeTextChanged: CharSequence: " + text + ", start: " + start + ", count: " + count);
        }
    };
}
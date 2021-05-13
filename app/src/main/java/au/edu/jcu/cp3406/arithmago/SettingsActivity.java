package au.edu.jcu.cp3406.arithmago;

import android.content.Context;
import android.content.SharedPreferences;
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
    private ArrayList<LevelItem> levelList;
    private LevelAdapter levelAdapter;
    private LevelItem selectedlevelItem;

    // View Elements
    private Spinner levelSpinner;
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

        generatelevelList();// Define levelSpinner custom layouts
        setSelectedlevelItem();// Set the selectedlevelItem to match dataSource
        findActivityViews();
        levelSpinner = setuplevelSpinner();
        updateCurrentViewState(); // Set view elements to match dataSource
        usernameEntry.addTextChangedListener(textWatcher);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Add save_menu to ActionBar
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void generatelevelList() {
        // Define levelSpinner custom layouts
        levelList = new ArrayList<>();
        levelList.add(new LevelItem("Dynamic", R.drawable.d));
        levelList.add(new LevelItem("Easy", R.drawable.e));
        levelList.add(new LevelItem("Medium", R.drawable.m));
        levelList.add(new LevelItem("Hard", R.drawable.h));
        levelList.add(new LevelItem("Expert", R.drawable.x));
    }

    private void setSelectedlevelItem() {
        // Set the selectedlevelItem to match dataSource
        switch (dataSource.getString("level", "Normal")) {
            case "Dynamic":
                selectedlevelItem = levelList.get(0);
                break;
            case "Easy":
                selectedlevelItem = levelList.get(1);
                break;
            case "Medium":
                selectedlevelItem = levelList.get(2);
                break;
            case "Hard":
                selectedlevelItem = levelList.get(3);
                break;
            case "Expert":
                selectedlevelItem = levelList.get(4);
                break;
        }
    }

    private void findActivityViews() {
        levelSpinner = findViewById(R.id.levelSpinner);
        multiplicationCheckBox = findViewById(R.id.multiplicationCheckbox);
        divisionCheckBox = findViewById(R.id.divisionCheckbox);
        additionCheckBox = findViewById(R.id.additionCheckbox);
        subtractionCheckBox = findViewById(R.id.subtractionCheckbox);
        usernameEntry = findViewById(R.id.usernameEntry);
    }

    private Spinner setuplevelSpinner() {
        levelSpinner = findViewById(R.id.levelSpinner);

        // Set levelAdapter to levelSpinner
        levelAdapter = new LevelAdapter(this, levelList);
        levelSpinner.setAdapter(levelAdapter);

        // Set item Selected Listener
        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedlevelItem = (LevelItem) parent.getItemAtPosition(position);
                // Update level in dataSource
                SharedPreferences.Editor editor = dataSource.edit();
                editor.putString("level", selectedlevelItem.getLevelName());
                editor.apply(); // Save changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return levelSpinner;
    }

    private void updateCurrentViewState() {
        // Set view elements to match dataSource
        multiplicationCheckBox.setChecked(dataSource.getBoolean("isMultiplicationEnabled", true));
        divisionCheckBox.setChecked(dataSource.getBoolean("isDivisionEnabled", true));
        additionCheckBox.setChecked(dataSource.getBoolean("isAdditionEnabled", true));
        subtractionCheckBox.setChecked(dataSource.getBoolean("isSubtractionEnabled", true));
        usernameEntry.setText(dataSource.getString("username", "Guest"));
        levelSpinner.setSelection(levelAdapter.getPosition(selectedlevelItem));
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
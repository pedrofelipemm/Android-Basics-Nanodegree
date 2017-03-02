package study.pmoreira.pets.ui.editor;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.pets.R;
import study.pmoreira.pets.data.PetContract.PetEntry;
import study.pmoreira.pets.ui.catalog.CatalogActivity;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.edit_pet_name)
    EditText mNameEditText;

    @BindView(R.id.edit_pet_breed)
    EditText mBreedEditText;

    @BindView(R.id.edit_pet_weight)
    EditText mWeightEditText;

    @BindView(R.id.spinner_gender)
    Spinner mGenderSpinner;

    private int mGender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetEntry.GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetEntry.GENDER_FEMALE;
                    } else {
                        mGender = PetEntry.GENDER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                try {
                    savePet();
                    startActivity(new Intent(this, CatalogActivity.class));
                } catch (IllegalArgumentException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePet() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PetEntry.COLUMN_PET_NAME, mNameEditText.getText().toString().trim());
        contentValues.put(PetEntry.COLUMN_PET_BREED, mBreedEditText.getText().toString().trim());
        contentValues.put(PetEntry.COLUMN_PET_GENDER, mGender);

        String weightString = mWeightEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(weightString)) {
            contentValues.put(PetEntry.COLUMN_PET_WEIGHT, Integer.parseInt(weightString));
        }

        Uri uri = getContentResolver().insert(PetEntry.CONTENT_URI, contentValues);

        if (uri == null) {
            Toast.makeText(this, getString(R.string.editor_insert_pet_failed), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.editor_insert_pet_successful), Toast.LENGTH_SHORT).show();
        }
    }
}
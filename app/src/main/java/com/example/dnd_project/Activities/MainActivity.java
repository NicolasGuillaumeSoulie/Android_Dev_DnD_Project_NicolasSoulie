package com.example.dnd_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dnd_project.AsyncTasks.AsyncJSONClassListData;
import com.example.dnd_project.AsyncTasks.AsyncJSONSpellListData;
import com.example.dnd_project.Constants;
import com.example.dnd_project.FileManager;
import com.example.dnd_project.Models.MySpellList;
import com.example.dnd_project.R;
import com.example.dnd_project.SpellAdapter;
import com.example.dnd_project.Models.SpellSimple;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The main activity.
 * Allow to see the spell list
 */
public class MainActivity extends AppCompatActivity {
    Button profilePicButton;
    RecyclerView rvSpells;
    TextView spellCountText;
    EditText searchNameBar;
    List<SpellSimple> spellList;
    SpellAdapter spellAdapter;
    ImageView myProfilePicture;
    Spinner classSpinner;
    LinearLayout layoutNameSearch;

    String charClass, nameSearch;

    MySpellList profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the views to attributes
        classSpinner = (Spinner) findViewById(R.id.classSpinner);
        searchNameBar = (EditText) findViewById(R.id.searchNameBar);
        myProfilePicture = (ImageView) findViewById(R.id.myProfilePicture);
        rvSpells = findViewById(R.id.rvSpells);
        spellCountText = findViewById(R.id.spellCountText);
        layoutNameSearch = (LinearLayout) findViewById(R.id.layoutNameSearch);

        // Set up the adapter
        spellList = new ArrayList<SpellSimple>();
        spellAdapter = new SpellAdapter(spellList);
        rvSpells.setAdapter(spellAdapter);
        rvSpells.setLayoutManager(new LinearLayoutManager(this));

        getPermissionFromUser();
        loadMySpellList();

        // Set up the research filters
        setUpSpinner();
        setUpSearchBar();

        // Get a first spell list without filters
        getSpellListFromApi();

        // Set up the image button to get a picture from storage
        profilePicButton = findViewById(R.id.profilePicButton);
        profilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pick a picture from storage
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);

            }

        });
    }

    /**
     * Send a request to the API for a spell list (AsyncTask)
     */
    private void getSpellListFromApi() {
        // Get the list of spell from the API
        AsyncJSONSpellListData asyncJSONSpellListData = (AsyncJSONSpellListData) new AsyncJSONSpellListData(spellAdapter) {
            // Display the number of spell in the list
            @Override
            protected void onPostExecute(JSONObject result) {
                super.onPostExecute(result);
                spellCountText.setText(rvSpells.getAdapter().getItemCount() + " spells");
            }
        }.execute(Constants.API_ADDRESS + charClass + "spells/" + nameSearch);
    }

    /**
     * Set up the spinner filter to select spells by character class
     */
    private void setUpSpinner() {
        charClass = "";

        // Load the classes from the API with an adapter
        ArrayList<String> classArray = new ArrayList<>();
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classArray);
        AsyncJSONClassListData asyncJSONClassListData = (AsyncJSONClassListData) new AsyncJSONClassListData(classAdapter).execute(Constants.API_ADDRESS + "classes/");

        // Link the adapter and the spinner
        classSpinner.setAdapter(classAdapter);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Add behaviors on selection
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (classSpinner.getAdapter().getItem(position).equals("all")) {
                    charClass = "";
                    // Show search by name (in case it was hidden)
                    layoutNameSearch.setVisibility(View.VISIBLE);
                } else {
                    charClass = "classes/" + classSpinner.getAdapter().getItem(position) + "/";
                    // Hide search by name (not support by API with class)
                    layoutNameSearch.setVisibility(View.GONE);
                    nameSearch = "";
                    searchNameBar.setText("");
                }
                getSpellListFromApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Set up the search bar
     */
    private void setUpSearchBar() {
        nameSearch = "";

        searchNameBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    nameSearch = "?name=" + v.getText();
                    getSpellListFromApi();
                    handled = true;
                }
                return handled;
            }
        });
    }

    /**
     * Use the info (picture Uri) from the image selection
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // Get the path of the picture
            Uri targetUri = data.getData();
            String textTargetUri = targetUri.toString();
            Log.i("Load Picture", textTargetUri);
            // Load the picture
            Bitmap bmp = loadPicture(targetUri);

            profile.setProfilePicture(bmp);
            save();
        }
    }

    /**
     * @param targetUri a picture path
     * @return the picture as a Bitmap and update GUI
     */
    private Bitmap loadPicture(Uri targetUri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
            myProfilePicture.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * //TODO Load and display a user profile
     * If it doesn't exist, create a blank new one
     */
    private void loadMySpellList() {
        profile = load("profilePic.data");
        if (profile == null) {
            profile = new MySpellList();
        } else {
            myProfilePicture.setImageBitmap(profile.getProfilePicture());
        }
    }

    /**
     * //TODO save profile
     */
    private void save() {
        FileManager fm = new FileManager();
        fm.serialize(profile, "profilePic.data", getApplicationContext());
    }

    /**
     * //TODO load profile
     */
    private MySpellList load(String filename) {
        FileManager fm = new FileManager();
        return fm.deserialize(filename, getApplicationContext());
    }

    /**
     * Ask user for permission if not granted
     */
    private void getPermissionFromUser() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.MANAGE_DOCUMENTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.MANAGE_DOCUMENTS},
                    1);
            return;
        }
    }
}
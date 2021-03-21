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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dnd_project.AsyncTasks.AsyncJSONSpellListData;
import com.example.dnd_project.Constants;
import com.example.dnd_project.FileManager;
import com.example.dnd_project.Models.MySpellList;
import com.example.dnd_project.R;
import com.example.dnd_project.SpellAdapter;
import com.example.dnd_project.Models.SpellSimple;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button mySpellButton;
    RecyclerView rvSpells;
    TextView spellCountText;
    EditText searchNameBar;
    List<SpellSimple> spellList;
    SpellAdapter spellAdapter;
    ImageView myProfilePicture;
    Spinner classSpinner;

    String charClass, nameSearch;

    MySpellList profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        classSpinner = (Spinner) findViewById(R.id.classSpinner);
        searchNameBar = (EditText) findViewById(R.id.searchNameBar);
        myProfilePicture = (ImageView) findViewById(R.id.myProfilePicture);
        rvSpells = findViewById(R.id.rvSpells);
        spellCountText = findViewById(R.id.spellCountText);
        spellList = new ArrayList<SpellSimple>();

        spellAdapter = new SpellAdapter(spellList);
        rvSpells.setAdapter(spellAdapter);
        rvSpells.setLayoutManager(new LinearLayoutManager(this));

        getPermissionFromUser();
        profil = load("profilePic.data");
        if (profil == null) {
            profil = new MySpellList();
        }
        else {
            myProfilePicture.setImageBitmap(profil.getProfilePicture());
        }

        setUpSpinner();
        setUpSearchBar();
        getSpellListFromApi();

        mySpellButton = findViewById(R.id.mySpellButton);
        mySpellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new SpellActvity
                // startActivity(new Intent(MainActivity.this, SpellActivity.class));
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);

            }

        });
    }

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

    private void setUpSpinner() {
        charClass = "";

        ArrayList<String> classArray = new ArrayList<>();
        classArray.add("all");
        classArray.add("cleric");
        classArray.add("druid");
        classArray.add("paladin");
        classArray.add("wizard");
        classArray.add("warlock");
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classArray);

        classSpinner.setAdapter(classAdapter);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (classSpinner.getAdapter().getItem(position).equals("all")) {
                    charClass = "";
                } else {
                    charClass = "classes/" + classSpinner.getAdapter().getItem(position) + "/";
                }
                getSpellListFromApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

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
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            String textTargetUri = targetUri.toString();
            Log.i("Load Picture", textTargetUri);
            Bitmap bmp = loadPicture(targetUri);

            profil.setProfilePicture(bmp);
            save();
        }
    }

    private void save() {
        FileManager fm = new FileManager();
        fm.serialize(profil, "profilePic.data", getApplicationContext());
    }

    private MySpellList load(String filename) {
        FileManager fm = new FileManager();
        return fm.deserialize(filename, getApplicationContext());
    }

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
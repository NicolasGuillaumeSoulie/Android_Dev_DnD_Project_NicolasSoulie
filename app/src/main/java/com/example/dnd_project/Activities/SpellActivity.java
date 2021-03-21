package com.example.dnd_project.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dnd_project.AsyncTasks.AsyncJSONData;
import com.example.dnd_project.R;
import com.example.dnd_project.Models.Spell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SpellActivity extends AppCompatActivity {
    String url = "https://www.dnd5eapi.co/api/spells/";
    TextView spellName, spellLevel, spellSchool, spellDesc, spellCasting, spellComponent, spellDuration, spellHighLevel, spellMaterial, spellRange;
    CheckBox spellConcentration, spellRitual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_description);

        try {
            url += getIntent().getStringExtra("EXTRA_SPELL_INDEX");
        } catch (Exception e) {
            url += "acid-arrow";
        }


        spellName = findViewById(R.id.spellName);
        spellLevel = findViewById(R.id.spellLevel);
        spellSchool = findViewById(R.id.spellSchool);
        spellDesc = findViewById(R.id.spellDesc);
        spellCasting = findViewById(R.id.spellCasting);
        spellComponent = findViewById(R.id.spellComponent);
        spellConcentration = findViewById(R.id.spellConcetration);
        spellDuration = findViewById(R.id.spellDuration);
        spellHighLevel = findViewById(R.id.spellHighLevel);
        spellMaterial = findViewById(R.id.spellMaterial);
        spellRange = findViewById(R.id.spellRange);
        spellRitual = findViewById(R.id.spellRitual);

        AsyncJSONData asyncJSONData = (AsyncJSONData) new AsyncJSONData().execute(url);
        JSONObject spellJSON;
        Spell spell;
        try {
            spellJSON = asyncJSONData.get();
            spell = new Spell(spellJSON);
            updateSpell(spell);
            Log.i("", spell.toString());
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateSpell(Spell spell) {
        spellName.setText(spell.getSpellName());
        spellLevel.setText(spell.getSpellLevel());
        spellSchool.setText(spell.getSpellSchool());
        spellDesc.setText(spell.getSpellDesc());
        spellCasting.setText(spell.getSpellCasting());
        spellComponent.setText(spell.getSpellComponent());
        spellConcentration.setChecked(spell.getSpellConcentration());
        spellDuration.setText(spell.getSpellDuration());
        spellHighLevel.setText(spell.getSpellHighLevel());
        spellMaterial.setText(spell.getSpellMaterial());
        spellRange.setText(spell.getSpellRange());
        spellRitual.setChecked(spell.getSpellRitual());
        ;
    }

}

package com.example.dnd_project.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Spell extends SpellSimple {

    String spellLevel;
    String spellSchool;
    String spellDesc;
    String spellCasting;
    String spellComponent;
    boolean spellConcentration;
    String spellDuration;
    String spellHighLevel;
    String spellMaterial;
    String spellRange;
    boolean spellRitual;

    public Spell(String name, String index) {
        super(name,index);
    }

    public Spell(JSONObject jsonSpell) throws JSONException {
        spellIndex = jsonSpell.getString("index");
        spellName = jsonSpell.getString("name");
        spellLevel = jsonSpell.getString("level");
        spellSchool = jsonSpell.getJSONObject("school").getString("name");
        spellDesc = jsonArrayToString(jsonSpell.getJSONArray("desc"), true);
        spellCasting = jsonSpell.getString("casting_time");
        spellRange = jsonSpell.getString("range");
        try {
            spellComponent = jsonArrayToString(jsonSpell.getJSONArray("components"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //spellComponent = jsonSpell.getString("components");
        try {
            spellConcentration = jsonSpell.getBoolean("concentration");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            spellDuration = jsonSpell.getString("duration");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            spellHighLevel = jsonArrayToString(jsonSpell.getJSONArray("higher_level"), true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            spellMaterial = jsonSpell.getString("material");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            spellRitual = jsonSpell.getBoolean("ritual");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String jsonArrayToString(JSONArray array) throws JSONException {
        return jsonArrayToString(array, false);
    }

    private String jsonArrayToString(JSONArray array, boolean paragraph) throws JSONException {
        String str = "";
        for (int i = 0; i < array.length(); i++) {
            if (paragraph) str += "\t";
            str += array.getString(i);
            str += paragraph ? "\n" : ", ";
            // Log.i("Spell", array.getString(i));
        }
        return str;
    }

    public String getSpellLevel() {
        return spellLevel;
    }

    public void setSpellLevel(String spellLevel) {
        this.spellLevel = spellLevel;
    }

    public String getSpellSchool() {
        return spellSchool;
    }

    public void setSpellSchool(String spellSchool) {
        this.spellSchool = spellSchool;
    }

    public String getSpellDesc() {
        return spellDesc;
    }

    public void setSpellDesc(String spellDesc) {
        this.spellDesc = spellDesc;
    }

    public String getSpellCasting() {
        return spellCasting;
    }

    public void setSpellCasting(String spellCasting) {
        this.spellCasting = spellCasting;
    }

    public String getSpellComponent() {
        return spellComponent;
    }

    public void setSpellComponent(String spellComponent) {
        this.spellComponent = spellComponent;
    }

    public boolean getSpellConcentration() {
        return spellConcentration;
    }

    public void setSpellConcentration(boolean spellConcentration) {
        this.spellConcentration = spellConcentration;
    }

    public String getSpellDuration() {
        return spellDuration;
    }

    public void setSpellDuration(String spellDuration) {
        this.spellDuration = spellDuration;
    }

    public String getSpellHighLevel() {
        return spellHighLevel;
    }

    public void setSpellHighLevel(String spellHighLevel) {
        this.spellHighLevel = spellHighLevel;
    }

    public String getSpellMaterial() {
        return spellMaterial;
    }

    public void setSpellMaterial(String spellMaterial) {
        this.spellMaterial = spellMaterial;
    }

    public String getSpellRange() {
        return spellRange;
    }

    public void setSpellRange(String spellRange) {
        this.spellRange = spellRange;
    }

    public boolean getSpellRitual() {
        return spellRitual;
    }

    public void setSpellRitual(boolean spellRitual) {
        this.spellRitual = spellRitual;
    }


}

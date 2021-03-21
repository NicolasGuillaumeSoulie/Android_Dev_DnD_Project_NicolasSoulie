package com.example.dnd_project.AsyncTasks;

import android.util.Log;

import com.example.dnd_project.SpellAdapter;
import com.example.dnd_project.Models.SpellSimple;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An AsyncTask to retrieve JSON info for a list of spells
 */
public class AsyncJSONSpellListData extends AsyncJSONData {
    // The adapter who will receive the spells recovered from the API
    SpellAdapter spellAdapter;

    public AsyncJSONSpellListData(SpellAdapter adapter) {
        spellAdapter = adapter;
    }

    /**
     * @param strings a list of url
     * @return the JSON answer of the first url
     * Work as AsyncJSONData but add a step to save spells in the adapter
     */
    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject result = super.doInBackground(strings);
        try {
            JSONArray spellListJSON = result.getJSONArray("results");
            spellAdapter.clear();
            for (int i = 0; i < spellListJSON.length(); i++) {
                String name = spellListJSON.getJSONObject(i).getString("name");
                String index = spellListJSON.getJSONObject(i).getString("index");
                SpellSimple spell = new SpellSimple(name, index);
                // Log.i("Load spell list: ", spell.toString());
                spellAdapter.add(spell);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Notify the adapter of the changes when it is over
     *
     * @param result the JSON answer
     */
    @Override
    protected void onPostExecute(JSONObject result) {
        spellAdapter.notifyDataSetChanged();
    }
}

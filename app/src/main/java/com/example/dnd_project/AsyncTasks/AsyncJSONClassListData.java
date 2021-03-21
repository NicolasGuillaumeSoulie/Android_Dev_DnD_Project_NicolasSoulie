package com.example.dnd_project.AsyncTasks;

import android.widget.ArrayAdapter;

import com.example.dnd_project.Models.SpellSimple;
import com.example.dnd_project.SpellAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AsyncJSONClassListData extends AsyncJSONData {
    // The adapter who will receive the spells recovered from the API
    ArrayAdapter<String> classAdapter;

    public AsyncJSONClassListData(ArrayAdapter<String> adapter) {
        classAdapter = adapter;
    }

    /**
     * Put the classes recovered from the API into the adapter
     * Notify the adapter of the changes when it is over
     *
     * @param result the JSON answer
     */
    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            JSONArray spellListJSON = result.getJSONArray("results");
            classAdapter.clear();
            classAdapter.add("all");
            for (int i = 0; i < spellListJSON.length(); i++) {
                String name = spellListJSON.getJSONObject(i).getString("index");
                // Log.i("Load spell list: ", spell.toString());
                classAdapter.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        classAdapter.notifyDataSetChanged();
    }
}

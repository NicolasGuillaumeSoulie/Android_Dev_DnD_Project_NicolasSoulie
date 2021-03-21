package com.example.dnd_project.AsyncTasks;

import android.util.Log;

import com.example.dnd_project.SpellAdapter;
import com.example.dnd_project.Models.SpellSimple;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AsyncJSONSpellListData extends AsyncJSONData {
    SpellAdapter spellAdapter;

    public AsyncJSONSpellListData(SpellAdapter adapter){
        spellAdapter = adapter;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject result = super.doInBackground(strings);
        try {
            JSONArray spellListJSON = result.getJSONArray("results");
            spellAdapter.clear();
            for(int i = 0; i<spellListJSON.length();i++){
                String name = spellListJSON.getJSONObject(i).getString("name");
                String index = spellListJSON.getJSONObject(i).getString("index");
                SpellSimple spell = new SpellSimple(name,index);
                // Log.i("Load spell list: ", spell.toString());
                spellAdapter.add(spell);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        spellAdapter.notifyDataSetChanged();
    }
}

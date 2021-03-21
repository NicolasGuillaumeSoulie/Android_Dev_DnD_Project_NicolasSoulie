package com.example.dnd_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnd_project.Activities.SpellActivity;
import com.example.dnd_project.Models.SpellSimple;

import java.util.List;

/**
 * A RecyclerView Adapter for the spell lists
 */
public class SpellAdapter extends RecyclerView.Adapter<SpellAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView spellItemName;
        public Button spellAddButton, spellDetailsButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            spellItemName = itemView.findViewById(R.id.spellItemName);
            spellAddButton = itemView.findViewById(R.id.spellAddButton);
            spellDetailsButton = itemView.findViewById(R.id.spellDetailsButton);
        }
    }

    // The spell list and context
    private List<SpellSimple> spellList;
    Context context;

    public SpellAdapter(List<SpellSimple> spellList) {
        this.spellList = spellList;
    }

    /**
     * Empty the spell list
     */
    public void clear() {
        spellList.clear();
    }

    /**
     * Add a spell to the spell list
     *
     * @param spell
     */
    public void add(SpellSimple spell) {
        spellList.add(spell);
    }

    /**
     * @return the number of spell in the list
     */
    @Override
    public int getItemCount() {
        return spellList.size();
    }

    @NonNull
    @Override
    public SpellAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get the context
        context = parent.getContext();

        // Set up the ViewHolder from the item_spell layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View spellView = inflater.inflate(R.layout.item_spell, parent, false);
        ViewHolder viewHolder = new ViewHolder(spellView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpellAdapter.ViewHolder holder, int position) {
        // Get the spell of the row
        SpellSimple spell = spellList.get(position);

        // Set the GUI for said spell
        holder.spellItemName.setText(spell.getSpellName());
        holder.spellDetailsButton.setOnClickListener(new View.OnClickListener() {
            // Set up the detail button so that it open a SpellActivity with the details of the spell
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpellActivity.class);
                intent.putExtra("EXTRA_SPELL_INDEX", spell.getSpellIndex());
                context.startActivity(intent);
            }
        });
    }


}

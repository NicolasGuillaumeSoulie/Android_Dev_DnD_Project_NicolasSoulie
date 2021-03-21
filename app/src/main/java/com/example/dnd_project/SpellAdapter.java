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

    private List<SpellSimple> spellList;
    Context context;

    public SpellAdapter(List<SpellSimple> spellList) {
        this.spellList = spellList;
    }

    public void clear() {
        spellList.clear();
    }

    public void add(SpellSimple spell) {
        spellList.add(spell);
    }

    @NonNull
    @Override
    public SpellAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View spellView = inflater.inflate(R.layout.item_spell, parent, false);

        ViewHolder viewHolder = new ViewHolder(spellView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpellAdapter.ViewHolder holder, int position) {
        SpellSimple spell = spellList.get(position);

        holder.spellItemName.setText(spell.getSpellName());
        holder.spellDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpellActivity.class);
                intent.putExtra("EXTRA_SPELL_INDEX", spell.getSpellIndex());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return spellList.size();
    }
}

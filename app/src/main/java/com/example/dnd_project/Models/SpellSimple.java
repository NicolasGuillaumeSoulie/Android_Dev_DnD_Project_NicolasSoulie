package com.example.dnd_project.Models;

/**
 * The most basic info about a spell.
 * Used for the spell lists.
 */
public class SpellSimple implements java.io.Serializable {
    protected String spellIndex;
    protected String spellName;

    public SpellSimple(String name, String index) {
        spellIndex = index;
        spellName = name;
    }

    public SpellSimple() {
    }

    public String getSpellIndex() {
        return spellIndex;
    }

    public void setSpellIndex(String spellIndex) {
        this.spellIndex = spellIndex;
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }
}

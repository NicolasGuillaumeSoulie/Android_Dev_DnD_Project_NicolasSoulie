package com.example.dnd_project.Models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * //TODO A class to save user profile
 */
public class MySpellList implements java.io.Serializable{
    String name, charClass;
    Bitmap profilePicture;
    List<SpellSimple> spellList;

    public MySpellList(){
        name = "No name";
        charClass = "Chose class";
        spellList = new ArrayList<>();
    }
    public boolean hasProfilePicture(){
        return profilePicture!=null;
    }

    public List<SpellSimple> getSpellList() {
        return spellList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharClass() {
        return charClass;
    }

    public void setCharClass(String charClass) {
        this.charClass = charClass;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }
}

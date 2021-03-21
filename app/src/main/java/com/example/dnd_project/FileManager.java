package com.example.dnd_project;

import android.content.Context;
import android.net.Uri;

import com.example.dnd_project.Models.MySpellList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * //TODO A class dedicated to saving & loading files
 */
public class FileManager {
    String defaultFilename = "myCharSpellSheets.spells";

    /**
     * Save to file
     */
    public void serialize(MySpellList ob, String filename, Context context) {
        try {
            FileOutputStream fileOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ob);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Load from file
     */
    public MySpellList deserialize(String filename, Context context) {
        MySpellList myCharSpellSheets = null;
        try {
            FileInputStream fileIn = context.openFileInput(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            myCharSpellSheets = (MySpellList) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return myCharSpellSheets;
    }
}

package net.sekmetech.database;

import java.util.ArrayList;

/**
 * Created by huseyin.sekmenoglu on 9.2.2016.
 * getters and setters for languages
 */
public class Language {

    private String Name;
    private ArrayList<LanguageChild> Items;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<LanguageChild> getItems() {
        return Items;
    }

    public void setItems(ArrayList<LanguageChild> Items) {
        this.Items = Items;
    }
}

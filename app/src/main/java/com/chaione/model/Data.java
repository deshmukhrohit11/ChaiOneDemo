package com.chaione.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

/**
 * The Class Data.
 */
public class Data {

    /**
     * The text.
     */
    private String text;

    /**
     * The user.
     */
    private User user;

    /**
     * Creates the data collection.
     *
     * @param jsonArray the json array
     * @return the array list
     */
    public static ArrayList<Data> createDataCollection(JsonArray jsonArray) {
        final ArrayList<Data> dataList = new ArrayList<>(1);
        final Gson gson = new GsonBuilder().serializeNulls().create();

        for (int i = 0, size = jsonArray.size(); i < size; i++) {
            final JsonElement jsonElement = jsonArray.get(i);
            Data data = gson.fromJson(jsonElement, Data.class);
            dataList.add(i, data);
        }
        return dataList;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the text.
     *
     * @param text the new text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

}

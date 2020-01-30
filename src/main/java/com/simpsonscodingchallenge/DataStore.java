package com.simpsonscodingchallenge;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.simpsonscodingchallenge.phrase.Phrase;

public class DataStore implements Serializable {

    private static volatile DataStore dataStore;
    private static Map<String, Character> characters;
    private static Map<String, Phrase> phrases;

    public static DataStore getInstance() {
        if (dataStore == null) {
            synchronized (DataStore.class) {
                if (dataStore == null) {
                    dataStore = new DataStore();
                }
            }
        }
        return dataStore;
    }

    public Map<String, Character> getCharacters() {
        return characters;
    }

    public Map<String, Phrase> getPhrases() {
        return phrases;
    }

    private DataStore() {
        if (dataStore != null) {
            throw new RuntimeException("Don't createOrUpdate by reflection!");
        }
        characters = new HashMap<>();
        phrases = new HashMap<>();
    }
}


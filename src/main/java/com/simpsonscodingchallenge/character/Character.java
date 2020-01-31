package com.simpsonscodingchallenge.character;

import org.json.simple.JSONObject;

public class Character {
    public String firstName;
    public String lastName;
    public String picture;
    public Integer age;
    private final String id;

    public String getId() {
        return id;
    }

    Character(String id, String firstName, String lastName, String picture, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.age = age;
    }

    Character(JSONObject json) {
        this.id = (String)json.get("id");
        this.firstName = (String)json.get("firstName");
        this.lastName = (String)json.get("lastName");
        this.picture = (String)json.get("picture");
        this.age = ((Long)json.get("age")).intValue();
    }

}

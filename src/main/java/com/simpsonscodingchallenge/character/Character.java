package com.simpsonscodingchallenge.character;

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

}

package com.simpsonscodingchallenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.util.Collection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CharacterRepositoryAdapterTest {

    private CharacterRepositoryAdapter adapter = new CharacterRepositoryAdapter();

    @Test
    @Order(1)
    public void createNewCharacter() {
        // GIVEN
        Character character = new Character(null, "Homer", "Simpson", "http://www.trbimg.com/img-573a089a/turbine/ct-homer-simpson-live-pizza-debate-met-0517-20160516", 43);
        try {
        // WHEN
            adapter.createOrUpdate(character);
        // THEN
            Collection<Character> characters = adapter.find(new Character(null,"Homer", "Simpson", null, null));
            Assertions.assertEquals(1, characters.size());
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    public void updateCharacter() {
        // GIVEN
        Character query = new Character(null, "Homer","Simpson", "http://www.trbimg.com/img-573a089a/turbine/ct-homer-simpson-live-pizza-debate-met-0517-20160516", 43);
        try {
            Collection<Character> characters = adapter.find(query);
            Character character = characters.stream().findFirst().get();
            character.age = 44;
            character.lastName ="Smith";
        // WHEN
            adapter.createOrUpdate(character);
        // THEN
            Assertions.assertEquals(1,characters.size());
            Assertions.assertEquals(44, characters.stream().findFirst().get().age);
            Assertions.assertEquals("Smith", characters.stream().findFirst().get().lastName);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(3)
    public void searchCharacter() {
        // GIVEN
        Character query = new Character(null, "Homer",null, null, null);
         // WHEN
        Collection<Character> characters = adapter.find(query);
        Character homer = characters.stream().findFirst().get();

        Character queryById = new Character(homer.getId(), null,null, null, null);
        Collection<Character> charactersById = adapter.find(queryById);
        // THEN
        Assertions.assertEquals(1,characters.size());
        Assertions.assertEquals(44, homer.age);
        Assertions.assertEquals("Smith", homer.lastName);
        Assertions.assertEquals("Homer", homer.firstName);

        Assertions.assertEquals(1,charactersById.size());
        Assertions.assertEquals(44, charactersById.stream().findFirst().get().age);
        Assertions.assertEquals("Smith", charactersById.stream().findFirst().get().lastName);
        Assertions.assertEquals("Homer", charactersById.stream().findFirst().get().firstName);
    }

    @Test
    @Order(4)
    public void deleteCharacter() {
        // GIVEN
        Character query = new Character(null, "Homer",null, null, null);

        Character homer = adapter.find(query).stream().findFirst().get();
        // WHEN
        adapter.delete(homer.getId());
        // THEN
        Collection<Character> characters = adapter.find(query);
        Assertions.assertEquals(0,characters.size());
    }

}

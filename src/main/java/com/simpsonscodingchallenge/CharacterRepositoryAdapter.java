package com.simpsonscodingchallenge;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CharacterRepositoryAdapter implements CharacterRepositoryPort {

    private DataStore dataStore = DataStore.getInstance();

    @Override
    public Collection<Character> find(final Character query) {
        Character character = trimCharacter(query);
        Collection<Character> characters = dataStore.getCharacters().values().stream()
            .filter(it -> (StringUtils.isEmpty(character.getId()) || it.getId().contains(character.getId()))
                    && (StringUtils.isEmpty(character.firstName) || it.firstName.contains(character.firstName))
                    && (StringUtils.isEmpty(character.lastName) || it.lastName.contains(character.lastName))
                    && (StringUtils.isEmpty(character.age) || it.age.equals(character.age))
            ).collect(Collectors.toList());
        return characters;
    }

    @Override
    public Character createOrUpdate(final Character characterToUpdate) throws BusinessException {
        validateCharacter(characterToUpdate);
        Character character = trimCharacter(characterToUpdate);
        if (StringUtils.isEmpty(character.getId())) {
            String id = UUID.randomUUID().toString().replaceAll("-","").substring(0, 24);
            Character newCharacter = new Character(id, character.firstName, character.lastName, character.picture, character.age);
            dataStore.getCharacters().put(id, newCharacter);
            return newCharacter;
        } else {
            dataStore.getCharacters().put(character.getId(), character);
            return character;
        }
    }

    @Override
    public void delete(final String id) {
        dataStore.getCharacters().remove(id);
    }

    private void validateCharacter(final Character character) throws BusinessException {
        if (StringUtils.isEmpty(character.firstName) || StringUtils.isEmpty(character.lastName) ||
                StringUtils.isEmpty(character.picture) || character.age == null) {
            throw new BusinessException("All fields must be not empty!");
        }
    }

    private Character trimCharacter(final Character character) {
        String id = StringUtils.isEmpty(character.getId()) ? character.getId() : character.getId().trim();
        String firstName = StringUtils.isEmpty(character.firstName) ? character.firstName : character.firstName.trim();
        String lastName = StringUtils.isEmpty(character.lastName) ? character.lastName : character.lastName.trim();
        String picture = StringUtils.isEmpty(character.picture) ? character.picture: character.picture.trim();
        return new Character(id, firstName, lastName, picture, character.age);
    }
}

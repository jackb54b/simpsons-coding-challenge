package com.simpsonscodingchallenge.character;

import com.simpsonscodingchallenge.BusinessException;

import java.util.Collection;

interface CharacterRepositoryPort {

    Collection<Character> find(Character character);
    Character createOrUpdate(Character character) throws BusinessException;
    void delete(String id);
}

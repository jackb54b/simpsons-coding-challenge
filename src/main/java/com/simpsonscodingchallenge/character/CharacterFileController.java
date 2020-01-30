package com.simpsonscodingchallenge.character;

import com.simpsonscodingchallenge.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CharacterFileController {
    private CharacterRepositoryPort repositoryAdapter;

    @Autowired
    public CharacterFileController(CharacterRepositoryPort repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    @PostConstruct
    public void loadCharactersFromFile() throws BusinessException {
        //repositoryAdapter.createOrUpdate(null);
    }

}


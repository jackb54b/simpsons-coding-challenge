package com.simpsonscodingchallenge.character;

import com.simpsonscodingchallenge.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterRestController {

    private CharacterRepositoryPort repositoryAdapter;

    @Autowired
    public CharacterRestController(CharacterRepositoryPort repositoryAdapter){
        this.repositoryAdapter = repositoryAdapter;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Character> createOrUpdate(@RequestBody Character character) throws BusinessException {
        Character newCharacter = repositoryAdapter.createOrUpdate(character);
        return new ResponseEntity(newCharacter, HttpStatus.OK);
    }

    @GetMapping(path= "/{id}")
    public ResponseEntity<java.lang.Character> get(@PathVariable String id) {
        Character query = new Character(id, null, null, null, null);
        Character character = repositoryAdapter.find(query).stream().findFirst().orElse(null);
        return new ResponseEntity(character, HttpStatus.OK);
    }

    @PostMapping(path = "/search", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Character>> search(@RequestBody Character query) {
        Collection<Character> characters = repositoryAdapter.find(query);
        return new ResponseEntity(characters, HttpStatus.OK);
    }

    @DeleteMapping(path= "/{id}")
    public void delete(@PathVariable String id) {
        repositoryAdapter.delete(id);
    }
}

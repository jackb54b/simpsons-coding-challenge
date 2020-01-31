package com.simpsonscodingchallenge.character;

import com.simpsonscodingchallenge.BusinessException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CharacterFileController {

    @Value("${data.folder}")
    private String dataFolder;

    private CharacterRepositoryPort repositoryAdapter;

    @Autowired
    public CharacterFileController(CharacterRepositoryPort repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    @PostConstruct
    public void loadCharactersFromFile() throws Exception {
        String fileContent = new String(Files.readAllBytes(Paths.get(dataFolder, "characters.json")));
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonFile = (JSONObject) jsonParser.parse(fileContent);
        List<JSONObject> charactersJSON = (List<JSONObject>) jsonFile.get("data");
        charactersJSON.stream().forEach(it -> {
            try {
                repositoryAdapter.createOrUpdate(new Character(it));
            } catch (BusinessException e) {
                e.printStackTrace();
                throw new RuntimeException("Problem with parsing characters.json", e);
            }
        });
    }

}


package core.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.repository.PlayListRepository;
import domain.entities.Playlist;
import java.io.File;
import java.io.IOException;
import java.util.List;
import json.modules.PlaylistGenerationModule;

/**
 * PlaylistRepositoryFileImplementation.
 */
public class PlayListRepositoryFileImpl extends BaseFileRepository implements PlayListRepository {
  private static ObjectMapper mapper = new ObjectMapper();
  private String filePath;

  /**
   * Checks the json file for input.
   */
  public List<Playlist> findAll() throws IOException {
    mapper.registerModule(new PlaylistGenerationModule());
    String rawJson = this.loadStringData(filePath);
    return mapper.readValue(rawJson, new TypeReference<List<Playlist>>() {
    });
  }

  /**
   * Saves input to JSON.
   */
  public void saveAll(List<Playlist> toSave) {
    mapper.registerModule(new PlaylistGenerationModule());
    String rawJson = null;
    try {
      rawJson = mapper.writeValueAsString(toSave);
    } catch (JsonProcessingException e) {
      System.err.println("Couldn't write object to json");
      e.printStackTrace();
    }
    try {
      this.saveStringData(filePath, rawJson);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This function deletes the JSON file. Needed for the testing of the service
   * classes.
   * 
   * @param filename the JSON file to be deleted.
   */
  public void deleteJsonPlaylistFile(String filename) {
    File myObj = new File(filename);
    if (myObj.delete()) {
      System.out.println("Deleted the file: " + myObj.getName());
    } else {
      System.out.println("Failed to delete the file.");
    }
  }

  /**
   * Returns object mapper.
   * 
   * @return the object mapper.
   */
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper().registerModule(new PlaylistGenerationModule());
  }

  /**
   * Decided what file to read from.
   * 
   * @param filePath the filePath.
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
}

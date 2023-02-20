package core.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.repository.SongRepository;
import domain.entities.Song;
import java.io.File;
import java.io.IOException;
import java.util.List;
import json.modules.PlaylistGenerationModule;

/**
 * Song Repository File Implementation.
 * Extends the BaseFileRepsoitory.
 * Implements SongRepository.
 */
public class SongRepositoryFileImpl extends BaseFileRepository implements SongRepository {
  private static ObjectMapper mapper = new ObjectMapper();
  private String filepath;

  /**
   * Searches through the JSON file.
   */
  public List<Song> findAll() throws IOException {
    mapper.registerModule(new PlaylistGenerationModule());
    String rawJson = this.loadStringData(filepath);
    return mapper.readValue(rawJson, new TypeReference<List<Song>>() {
    });
  }

  /**
   * Saves input in JSON.
   */
  public void saveAll(List<Song> toSave) {
    mapper.registerModule(new PlaylistGenerationModule());
    String rawJson = null;
    try {
      rawJson = mapper.writeValueAsString(toSave);
    } catch (JsonProcessingException e) {
      System.err.println("Couldn't write object to json");
      e.printStackTrace();
    }
    try {
      this.saveStringData(filepath, rawJson);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets the file path.
   * 
   */
  public void setFilePath(String path) {
    this.filepath = path;
  }

  /**
   * This method deletes the JSON file. It is needed for correct testing of the
   * service classes.
   * 
   * @param filename which is the JSON file location.
   */
  public void deleteJsonSongFile(String filename) {
    File myObj = new File(filename);
    if (myObj.delete()) {
      System.out.println("Deleted the file: " + myObj.getName());
    } else {
      System.out.println("Failed to delete the file.");
    }
  }
}

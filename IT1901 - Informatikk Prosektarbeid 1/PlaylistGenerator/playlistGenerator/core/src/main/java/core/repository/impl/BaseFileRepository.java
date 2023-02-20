package core.repository.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * BaseFileRepository.
 */
public abstract class BaseFileRepository {

  /**
   * This function Loads the data from the JSON file.
   * 
   * @param relativePath is the JSON path.
   * @returns the string data.
   * @throws IOException if relativePath not valid.
   */

  protected String loadStringData(String relativePath) throws IOException {
    relativePath = "\\" + relativePath;
    Path filePath = Path.of(System.getProperty("user.home") + relativePath);
    return Files.readString(filePath);
  }

  /**
   * This method saves that to the JSON file.
   * 
   * @param relativePath is the JSON path.
   * @param toSave       the String to be saved.
   * @throws IOException if relativePath is not valid.
   */

  protected void saveStringData(String relativePath, String toSave) throws IOException {
    if (relativePath == null) {
      throw new IllegalArgumentException("can't pass relative path null");
    }
    relativePath = "\\" + relativePath;
    Path filePath = Path.of(System.getProperty("user.home"), relativePath);

    BufferedWriter out = Files.newBufferedWriter(filePath);
    out.write(toSave);
    out.close();
  }
}

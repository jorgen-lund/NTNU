package json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import domain.entities.Song;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Deserializes the text from the json file into objects that the code can use.
 */
public class SongDeserializer extends JsonDeserializer<Song> {

  /*
   * Deserializer for song. Converts json nodes into useable code. format: {
   * "name": "Sad Song", "artist": "We The Kings", "rating": ["8"], "moods":
   * ["sad", "old"] }.
   * 
   */

  @Override
  public Song deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Deserializer for song. Converts json nodes into useable code.
   * 
   * @param jsonNode base class node for json
   * @return song object. If there is an error, null is returned.
   * @throws IOException             throws IOException if unable to find json
   *                                 file
   * @throws JsonProcessingException exception if there are problems parsing.
   */
  public Song deserialize(JsonNode jsonNode) throws IOException, JsonProcessingException {
    if (jsonNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) jsonNode;
      this.validateJsonData(objectNode);

      String name = objectNode.get("name").asText();
      String artist = objectNode.get("artist").asText();
      ArrayList<Double> ratings = new ArrayList<Double>();
      JsonNode ratingNode = objectNode.get("ratings");
      for (JsonNode rating : ratingNode) {
        ratings.add(rating.asDouble());

      }

      ArrayList<String> moods = new ArrayList<String>();

      JsonNode moodsNode = objectNode.get("moods");
      for (JsonNode mood : moodsNode) { // ((ArrayNode) moodsNode)?
        moods.add(mood.asText());
      }
      Song song = new Song(name, artist, ratings, moods);
      return song;
    }
    return null;
  }

  /**
   * Validates the json data.
   * 
   * @param objectNode takes the objectnode from json and checks if the data is
   *                   correct
   */

  private void validateJsonData(ObjectNode objectNode) {
    if (!(objectNode.get("name") instanceof TextNode)) {
      throw new Error("Error in json data 7");
    }
    if (!(objectNode.get("artist") instanceof TextNode)) {
      throw new Error("Error in json data 8");
    }
    if (!(objectNode.get("moods") instanceof ArrayNode)) {
      throw new Error("Error in json data 10");
    }
  }
}

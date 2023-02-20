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
import domain.entities.Playlist;
import domain.entities.Song;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads from the json file and converts the text data to objects that the code
 * can use.
 */
public class PlaylistDeserializer extends JsonDeserializer<Playlist> {

  private SongDeserializer songDeserializer = new SongDeserializer();

  /**
   * Deserializer for playlist. Converts json nodes into useable code. format:
   * {"playlist: "
   * ["name": "Sad Song", "artist": "We The Kings", "rating": ["8"], "moods":
   * ["sad", "old"]], "name": "myPlaylist", "minRating": 0.0, "maxRating": 10.0,
   * "moods": ["Sad", "old"]}.
   */

  @Override
  public Playlist deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Translates json nodes to objects that the code can use.
   * 
   * @param jsonNode base class node for json
   * @return playlistobject if parsed correctly. Null of there was an errors
   * @throws IOException             throws IOException if unable to find json
   *                                 file
   * @throws JsonProcessingException exception if there are problems parsing.
   */

  public Playlist deserialize(JsonNode jsonNode) throws JsonProcessingException, IOException {
    if (jsonNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) jsonNode;
      this.validateJsonStructure(objectNode);

      ArrayList<Song> songs = new ArrayList<Song>();
      JsonNode playlistsNode = objectNode.get("playlist");
      for (JsonNode playlistNode : playlistsNode) {
        Song song = songDeserializer.deserialize(playlistNode);
        if (song != null) {
          songs.add(song);
        }
      }

      String name = objectNode.get("name").asText();
      Double minrating = objectNode.get("minrating").asDouble();
      Double maxrating = objectNode.get("maxrating").asDouble();

      ArrayList<String> moods = new ArrayList<String>();
      JsonNode moodsNode = objectNode.get("mood");
      for (JsonNode mood : moodsNode) {
        moods.add(mood.asText());
      }

      Playlist playlist = new Playlist(name, moods, minrating, maxrating, songs);
      return playlist;
    }

    return null;
  }

  /**
   * Validates the json objectnodes.
   * 
   * @param objectNode takes the objectnode from json and checks if the data is
   *                   correct
   */

  private void validateJsonStructure(ObjectNode objectNode) {
    if (!(objectNode.get("playlist") instanceof ArrayNode)) {
      throw new Error("Error in json data 1");
    }
    if (!(objectNode.get("name") instanceof TextNode)) {
      throw new Error("Error in json data 2");
    }
    if (!(objectNode.get("mood") instanceof ArrayNode)) {
      throw new Error("Error in json data 5");
    }
  }
}

package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import domain.entities.Playlist;
import domain.entities.Song;
import java.io.IOException;

/**
 * Class that converts objects to textstruture to store while program is not
 * active.
 */
public class PlaylistSerializer extends JsonSerializer<Playlist> {

  /**
   * Function that converts objects into textstructures.
   * 
   * @param playlist           playlist object
   * @param jgen               json generator
   * @param serializerProvider controlls that serializers are able to serializing
   *                           spesisfic types.
   * @throws IOExeption if unable to fin json file
   */
  @Override
  public void serialize(Playlist playlist, JsonGenerator jgen, SerializerProvider serializerProvider)
      throws IOException {

    /*
     * format: { "playlist": [ ... ], "name": "all_my_music", "minrating": "6",
     * "maxrating": "10", "mood": ["sad", "happy"] }
     */

    jgen.writeStartObject();
    jgen.writeArrayFieldStart("playlist");
    for (Song song : playlist.getPlaylist()) {
      jgen.writeObject(song);
    }
    jgen.writeEndArray();

    jgen.writeStringField("name", playlist.getName());
    jgen.writeNumberField("minrating", playlist.getMinRating());
    jgen.writeNumberField("maxrating", playlist.getMaxRating());

    jgen.writeArrayFieldStart("mood");
    for (String mood : playlist.getMoods()) {
      jgen.writeString(mood);
    }
    jgen.writeEndArray();

    jgen.writeEndObject();
  }
}

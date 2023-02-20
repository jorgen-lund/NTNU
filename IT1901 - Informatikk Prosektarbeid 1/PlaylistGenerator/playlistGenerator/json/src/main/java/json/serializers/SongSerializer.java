package json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import domain.entities.Song;
import java.io.IOException;

/**
 * Serializes song objects into text structure for persistance.
 */

public class SongSerializer extends JsonSerializer<Song> {

  /**
   * Function that converts objects into textstructures.
   * 
   * @param song               song object
   * @param jgen               json generator
   * @param serializerProvider controlls that serializers are able to serializing
   *                           spesisfic types.
   * @throws IOExeption if unable to find json file
   */
  @Override
  public void serialize(Song song, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

    /*
     * format: { "name": "Sad Song", "artist": "We The Kings", "ratings": ["8"],
     * "moods": ["sad", "old"] }
     */

    jgen.writeStartObject();
    jgen.writeStringField("name", song.getName());
    jgen.writeStringField("artist", song.getArtist());

    jgen.writeArrayFieldStart("ratings");
    for (Double rating : song.getRatings()) {
      jgen.writeNumber(rating);
    }
    jgen.writeEndArray();

    jgen.writeArrayFieldStart("moods");
    for (String mood : song.getMood()) {
      jgen.writeString(mood);
    }
    jgen.writeEndArray();

    jgen.writeEndObject();
  }
}

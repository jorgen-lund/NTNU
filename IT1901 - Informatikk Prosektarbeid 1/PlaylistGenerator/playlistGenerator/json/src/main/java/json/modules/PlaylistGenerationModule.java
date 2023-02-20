package json.modules;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import domain.entities.Playlist;
import domain.entities.Song;
import json.deserializers.PlaylistDeserializer;
import json.deserializers.SongDeserializer;
import json.serializers.PlaylistSerializer;
import json.serializers.SongSerializer;

/**
 * Extends Simple Module.
 */
public class PlaylistGenerationModule extends SimpleModule {
  private static final long serialVersionUID = 1L;
  private static final String NAME = "PlaylistGenerationModule";
  private static final VersionUtil VERSION_UTIL = new VersionUtil() {
  };

  /**
   * Serialize and deserialize.
   */
  public PlaylistGenerationModule() {
    super(NAME, VERSION_UTIL.version());
    addSerializer(Playlist.class, new PlaylistSerializer());
    addSerializer(Song.class, new SongSerializer());
    addDeserializer(Playlist.class, new PlaylistDeserializer());
    addDeserializer(Song.class, new SongDeserializer());
  }
}

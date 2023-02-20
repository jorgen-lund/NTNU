package ui;

import java.io.IOException;
import java.util.ArrayList;

import domain.entities.Song;

public interface SongServiceAccess {

  ArrayList<Song> getAllSongs() throws IOException;

  // this could come back as a sting (serialized by songService?) or as an object,
  // do we need it as an object?
  Song getSong(String playlistName) throws IOException;

  boolean addSong(String title, String artist, Double rating, ArrayList<String> moods);

  String getSongsStringed() throws IllegalArgumentException, IOException;

  String getSongStats();
}

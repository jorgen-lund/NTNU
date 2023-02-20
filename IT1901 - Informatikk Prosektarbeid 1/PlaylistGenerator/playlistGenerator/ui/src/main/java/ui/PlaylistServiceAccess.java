package ui;

import java.io.IOException;
import java.util.ArrayList;

import domain.entities.Playlist;
import domain.entities.Song;

public interface PlaylistServiceAccess {

  boolean addPlaylist(String name, ArrayList<String> moods, double minrating, double maxrating,
      ArrayList<Song> allSongs);

  ArrayList<Playlist> getAllPlaylists() throws IOException;

  String getPlaylistString(String name) throws IllegalArgumentException, IOException;

  String getPlaylistNames() throws IOException;

  String getPlaylistStats() throws IOException;
}

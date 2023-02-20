package ui;

import domain.entities.Playlist;
import domain.entities.Song;
import json.modules.PlaylistGenerationModule;
import core.services.PlaylistService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PlaylistServiceRemoteAccess implements PlaylistServiceAccess {

  private final URI endpointBaseUri;
  private final ObjectMapper objectMapper;
  private PlaylistService playlistService;

  public PlaylistServiceRemoteAccess(URI endpointBaseUri) {
    this.endpointBaseUri = endpointBaseUri;
    this.objectMapper = new ObjectMapper().registerModule(new PlaylistGenerationModule());
  }

  /**
   * Creates a URI with the input String.
   * 
   * @param relPath
   * @return URI
   */
  private URI relUri(String relPath) {
    return endpointBaseUri.resolve(relPath);
  }

  /**
   * This method checks if there is already a PlaylistService saved, if so it
   * returns and exits. If not, it creates a GET request for the playlistService
   * content it needs it also defines what it wants as a response from the server.
   * 
   * @throws IOException
   */
  public void storePlaylistService() throws IOException {

    try {
      String relPath = "/springboot/playlists";
      HttpRequest request = HttpRequest.newBuilder(relUri(relPath)).header("Accept", "application/json").GET().build();
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      final String responseString = response.body();
      this.playlistService = objectMapper.readValue(responseString, PlaylistService.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Checks that the local playlistService is not null in the
   * storePlaylistService() method, if yes gets a service from the server. Gets a
   * list og all playlists saved in the PlaylistService.
   * 
   * @return ArrayList<Playlist>
   */
  @Override
  public ArrayList<Playlist> getAllPlaylists() throws IOException {
    storePlaylistService();
    return playlistService.getPlaylists();
  }

  /**
   * This writes the playlist given as input to a json file, and creates a put
   * request to the server where it includes the playlist. It the PUT request was
   * succesfull it returns true, it thrown it returns false.
   * 
   * @param playlist
   * @return boolean
   */
  @Override
  public boolean addPlaylist(String name, ArrayList<String> moods, double minrating, double maxrating,
      ArrayList<Song> allSongs) {
    try {
      storePlaylistService();
      this.playlistService.addPlaylist(name, moods, minrating, maxrating, allSongs);
      String json = objectMapper.writeValueAsString(playlistService);
      String relPath = "/springboot/new/playlists";
      HttpRequest request = HttpRequest.newBuilder(relUri(relPath)).header("Accept", "application/json")
          .header("Content-Type", "application/json").PUT(BodyPublishers.ofString(json)).build();
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      Boolean added = objectMapper.readValue(responseString, Boolean.class);
      if (added) {
        return true;
      }
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Creates a Sting containing a list of the songs added to the most playlists in
   * the PlaylistService.
   * Checks that the service has been read from the server, if not a GET req. is
   * created.
   * 
   * @return String
   * @throws IOException
   */
  @Override
  public String getPlaylistStats() throws IOException {
    storePlaylistService();
    return this.playlistService.returnSongSortInstanceStringedNew();
  }

  /**
   * Checks that the PlaylistService is not empty, if so GETs from server.
   * Gets the playlist with input string as name and returns it as a String on the
   * format
   * Playlist Name: playlist name
   * Name: name, Artist: artist, Rating: avg rating, Moods: [moods].
   * 
   * @param name of playlust to be found
   * @return playlist content in String
   */
  @Override
  public String getPlaylistString(String name) throws IllegalArgumentException, IOException {
    storePlaylistService();
    return playlistService.returnPlaylistString(name);
  }

  /**
   * Checks that the PlaylistService is not empty, if so GETs from server.
   * Returns a String with the names of all saved playlists.
   * 
   * @return String playlistnames
   */
  @Override
  public String getPlaylistNames() throws IOException {
    storePlaylistService();
    return playlistService.returnPlaylistNames();
  }
}

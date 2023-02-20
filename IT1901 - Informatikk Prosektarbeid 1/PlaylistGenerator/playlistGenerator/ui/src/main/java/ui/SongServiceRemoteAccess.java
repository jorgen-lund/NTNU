package ui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.services.SongService;
import domain.entities.Song;
import json.modules.PlaylistGenerationModule;

public class SongServiceRemoteAccess implements SongServiceAccess {

  private final URI endpointBaseUri;
  private final ObjectMapper objectMapper;
  private SongService songService;

  public SongServiceRemoteAccess(URI endpointBaseUri) {
    this.endpointBaseUri = endpointBaseUri;
    objectMapper = new ObjectMapper().registerModule(new PlaylistGenerationModule());
  }

  /**
   * Creates a URI with the given string input.
   * 
   * @param relPath
   * @return URI
   */
  private URI relUri(String relPath) {
    return endpointBaseUri.resolve(relPath);
  }

  /**
   * This method is for getting a SongService remotely. If the local SongService
   * already is not empty, it returns. An Http GET reuqest is built, and the and
   * the local SongService object is set to be read from the response.
   * 
   * @throws RuntimeException
   */
  public void storeSongService() {
    String relPath = "/springboot/songs";
    HttpRequest request = HttpRequest.newBuilder(relUri(relPath)).header("Accept", "application/json").GET()
        .build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      final String responseString = response.body();
      this.songService = objectMapper.readValue(responseString, SongService.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets a song remotely which has the specified input as a name. Creates a GET
   * request to the server. Returns a Song object is the song can be found, null
   * if not.
   * 
   * @return Song with input name
   */
  public Song getSong(String songName) {
    if (this.songService != null) {
      String relPath = "/songs/" + songName;
      HttpRequest request = HttpRequest.newBuilder(relUri(relPath)).header("Accept", "application/json").GET().build();
      try {
        final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
            HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), Song.class);
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  /**
   * Returns a list of all songs that are stored in the local SongService.
   * Checks first that it is not empty, if so GETs from the server.
   * 
   * @return list of all songs.
   */
  @Override
  public ArrayList<Song> getAllSongs() {
    storeSongService();
    return this.songService.getSongs();
  }

  /**
   * Creates a put request to the server for adding a new song to be saved, gets a
   * boolean response.
   * 
   * @return boolean true if the save was successfull.
   */
  @Override
  public boolean addSong(String title, String artist, Double rating, ArrayList<String> moods) {
    try {
      storeSongService();
      this.songService.addSong(title, artist, rating, moods);
      String json = objectMapper.writeValueAsString(songService);
      String relPath = "/springboot/new/songs";
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
   * Gets a string of all the songs, formatted to be displayed.
   * 
   * @return String
   */
  @Override
  public String getSongsStringed() throws IllegalArgumentException, IOException {
    storeSongService();
    return this.songService.returnSongsAsStrings();
  }

  /**
   * Gets a string of all the songs sorted after highest ratings, in decreasing
   * order.
   * 
   * @return String
   */
  @Override
  public String getSongStats() {
    storeSongService();
    return this.songService.returnSongSortRatingStringed();
  }
}

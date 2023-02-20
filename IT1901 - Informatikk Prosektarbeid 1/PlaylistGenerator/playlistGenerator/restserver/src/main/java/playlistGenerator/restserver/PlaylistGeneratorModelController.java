package playlistGenerator.restserver;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.services.SongService;
import core.services.PlaylistService;
import domain.entities.Playlist;

/**
 * The service implementation.
 */

@RestController
@RequestMapping(PlaylistGeneratorModelController.PLAYLISTGENERATOR_MODEL_SERVICE_PATH)
public class PlaylistGeneratorModelController {

  public static final String PLAYLISTGENERATOR_MODEL_SERVICE_PATH = "springboot";

  @Autowired
  private PlaylistGeneratorModelService playlistGeneratorModelService;

  /**
   * Gets the playlist with input name.
   *
   * @param name the name of the Playlist.
   * @return Playlist the corresponding Playlist object, null if none is found.
   */
  @GetMapping(path = "/playlists/{name}")
  public Playlist getPlaylist(@PathVariable("name") String name) {
    var playlistService = this.playlistGeneratorModelService
        .getPlaylistGeneratorModel("springbootserver-playlists.json");
    return playlistService.findPlaylist(name);
  }

  /**
   * Gets the whole PlaylistService from the server.
   * 
   * @return PlaylistSetvice
   */
  @GetMapping(path = "/playlists")
  public PlaylistService getPlaylistGeneratorModel() {
    return playlistGeneratorModelService.getPlaylistGeneratorModel("springbootserver-playlists.json");
  }

  @GetMapping(path = "/songs")
  public SongService getSongPlaylistGeneratorModel() {
    return this.playlistGeneratorModelService.getSongPlaylistGeneratorModel("springbootserver-songs.json");
  }

  /**
   * Takes in a whole playlist service to save in PlaylistGeneratorModelService.
   * 
   * @param playlistService the playlistService that will be saved.
   */
  @PutMapping(path = "/new/playlists")
  public boolean addPlaylistService(@RequestBody PlaylistService playlistService) {
    return this.playlistGeneratorModelService.setPlaylistGeneratorModel(playlistService);
  }

  /**
   * takes in a whole song service to save, saves it in
   * PlaylistGeneratorModelService.
   * 
   * @param songService the songService.
   * @throws IOException when invalid input.
   */
  @PutMapping(path = "/new/songs")
  public boolean addSongService(@RequestBody SongService songService) throws IOException {
    return this.playlistGeneratorModelService.setSongPlaylistGeneratorModel(songService);
  }
}

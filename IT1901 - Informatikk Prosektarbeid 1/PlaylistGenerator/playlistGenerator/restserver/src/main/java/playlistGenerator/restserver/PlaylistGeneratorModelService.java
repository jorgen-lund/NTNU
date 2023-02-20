package playlistGenerator.restserver;

import java.io.IOException;

import org.springframework.stereotype.Service;

import core.services.PlaylistService;
import core.services.SongService;

@Service
public class PlaylistGeneratorModelService {

  private PlaylistService playlistService;
  private SongService songService;

  /**
   * Initializes the service with a specific PlaylistService and SongService.
   *
   * @param PlaylistService the PlaylistService
   * @param SongService     the SongService
   */
  public PlaylistGeneratorModelService(PlaylistService playlistService, SongService songService) {
    this.playlistService = new PlaylistService(playlistService);
    this.songService = new SongService(songService);
  }

  /**
   * Empty constructor creates a default PlaylistService.
   */
  public PlaylistGeneratorModelService() {
    this(createDefaultPlaylistGeneratorModel("springbootserver-playlists.json"),
        createDefaultSongPlaylistGeneratorModel("springbootserver-songs.json"));
  }

  public PlaylistService getPlaylistGeneratorModel(String urlPlaylist) {
    return new PlaylistService(urlPlaylist);
  }

  public SongService getSongPlaylistGeneratorModel(String urlSong) {
    return new SongService(urlSong);
  }

  public boolean setPlaylistGeneratorModel(PlaylistService playlistService) {
    this.playlistService = new PlaylistService(playlistService);
    return true;
  }

  public boolean setSongPlaylistGeneratorModel(SongService songService) throws IOException {
    this.songService = new SongService(songService);
    return true;
  }

  private static PlaylistService createDefaultPlaylistGeneratorModel(String url) {
    return new PlaylistService(url);
  }

  private static SongService createDefaultSongPlaylistGeneratorModel(String url) {
    return new SongService(url);
  }

  /**
   * Saves the SOngService and PlaylistService to disk.
   * Should be called after each update.
   * 
   * @throws IOException
   */
  public void autoSavePlaylistGeneratorModel() throws IOException {
    if (playlistService != null) {
      this.playlistService.saveAllInFile();
    }
    if (songService != null) {
      this.songService.saveAllInFile();
    }
  }
}

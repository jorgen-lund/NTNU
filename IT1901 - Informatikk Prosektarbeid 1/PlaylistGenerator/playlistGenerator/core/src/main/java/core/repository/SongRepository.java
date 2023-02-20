package core.repository;

import domain.entities.Song;
import java.io.IOException;
import java.util.List;

/**
 * Interface song repository.
 */
public interface SongRepository {
    public void saveAll(List<Song> playlists);

    public List<Song> findAll() throws IOException;

    public void setFilePath(String string);
}

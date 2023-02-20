package core.repository;

import domain.entities.Playlist;
import java.io.IOException;
import java.util.List;

/**
 * Interface playlist repository.
 */
public interface PlayListRepository {
    public void saveAll(List<Playlist> playlists);

    public List<Playlist> findAll() throws IOException;
}

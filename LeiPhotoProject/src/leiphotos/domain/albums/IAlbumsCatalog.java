package leiphotos.domain.albums;

import leiphotos.domain.facade.IPhoto;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Interface representing catalogs of albums.
 */
public interface IAlbumsCatalog {

    /**
     * Creates a new album with the given name if it doesn't exist.
     *
     * @param albumName the name for the new album
     * @return true if the album was created
     */
    boolean createAlbum(String albumName);

    /**
     * Creates a new smart album with the given name and criteria.
     *
     * @param albumName the name for the new album
     * @param criteria  the predicate for auto-selecting photos
     * @return true if the album was created
     */
    boolean createSmartAlbum(String albumName, Predicate<IPhoto> criteria);

    /**
     * Removes the album with the given name if it exists.
     *
     * @param albumName the name of the album to remove
     * @return true if the album was removed
     */
    boolean deleteAlbum(String albumName);

    /**
     * Checks if an album with the given name exists.
     *
     * @param albumName the name to check
     * @return true if the album exists
     */
    boolean containsAlbum(String albumName);

    /**
     * Returns the album with the given name, or null.
     *
     * @param albumName the name of the album
     * @return the album, or null
     */
    IAlbum getAlbum(String albumName);

    /**
     * Returns the names of all albums.
     *
     * @return set of album names
     */
    Set<String> getAlbumNames();
}

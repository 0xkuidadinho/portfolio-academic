package leiphotos.domain.views;

import leiphotos.domain.facade.IPhoto;

import java.util.Comparator;
import java.util.List;

/**
 * Interface representing views of photo libraries.
 * Views filter and sort photos according to a predicate and comparator.
 */
public interface ILibraryView {

    /**
     * Sets the comparator used for ordering photos in this view.
     *
     * @param c the comparator to use
     */
    void setComparator(Comparator<IPhoto> c);

    /**
     * Returns the number of photos that belong to this view.
     *
     * @return the number of photos
     */
    int numberOfPhotos();

    /**
     * Returns the photos in this view, ordered by the current comparator.
     *
     * @return the ordered list of photos
     */
    List<IPhoto> getPhotos();

    /**
     * Returns the photos in this view that match the given regular expression,
     * ordered by the current comparator.
     *
     * @param regexp the regular expression
     * @return the matching photos
     */
    List<IPhoto> getMatches(String regexp);
}

package leiphotos.domain.core;

import leiphotos.domain.core.events.PhotoAddedLibraryEvent;
import leiphotos.domain.core.events.PhotoChangedLibraryEvent;
import leiphotos.domain.core.events.PhotoLibraryEvent;
import leiphotos.domain.core.events.PhotoRemovedLibraryEvent;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.AbsSubject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the main photo library of the application.
 * Extends AbsSubject to notify observers when photos are added,
 * removed or changed.
 */
public class MainLibrary extends AbsSubject<PhotoLibraryEvent> implements Library {

    private final List<IPhoto> photos;

    /**
     * Creates a new empty MainLibrary.
     */
    public MainLibrary() {
        super();
        this.photos = new ArrayList<>();
    }

    @Override
    public int getNumberOfPhotos() {
        return photos.size();
    }

    /**
     * Adds a photo to this library if not already present.
     * Notifies observers with a PhotoAddedLibraryEvent.
     *
     * @param photo the photo to add
     * @return true if the photo was added
     */
    @Override
    public boolean addPhoto(IPhoto photo) {
        if (photo != null && !photos.contains(photo)) {
            photos.add(photo);
            emitEvent(new PhotoAddedLibraryEvent(photo, this));
            return true;
        }
        return false;
    }

    /**
     * Removes a photo from this library.
     * Notifies observers with a PhotoRemovedLibraryEvent.
     *
     * @param photo the photo to remove
     * @return true if the photo was removed
     */
    @Override
    public boolean deletePhoto(IPhoto photo) {
        if (photos.remove(photo)) {
            emitEvent(new PhotoRemovedLibraryEvent(photo, this));
            return true;
        }
        return false;
    }

    @Override
    public Collection<IPhoto> getPhotos() {
        return Collections.unmodifiableList(new ArrayList<>(photos));
    }

    @Override
    public Collection<IPhoto> getMatches(String regexp) {
        return photos.stream()
                .filter(p -> p.matches(regexp))
                .collect(Collectors.toList());
    }

    /**
     * Toggles the favourite status of the given photos and
     * notifies observers for each changed photo.
     *
     * @param selectedPhotos the photos to toggle
     */
    public void toggleFavourite(Iterable<IPhoto> selectedPhotos) {
        for (IPhoto photo : selectedPhotos) {
            if (photos.contains(photo)) {
                photo.toggleFavourite();
                emitEvent(new PhotoChangedLibraryEvent(photo, this));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("***** MAIN PHOTO LIBRARY: ")
          .append(getNumberOfPhotos()).append(" photos *****\n");
        List<IPhoto> sorted = new ArrayList<>(photos);
        sorted.sort((a, b) -> a.file().getPath().compareTo(b.file().getPath()));
        for (IPhoto p : sorted) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }
}

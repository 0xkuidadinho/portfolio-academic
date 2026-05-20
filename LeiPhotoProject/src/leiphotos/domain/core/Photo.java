package leiphotos.domain.core;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.RegExpMatchable;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Represents a mutable photo in the application.
 * Each photo records the path to the file on disk, its metadata,
 * title, date added to the library, favourite status and size.
 * Implements IPhoto and RegExpMatchable.
 */
public class Photo implements IPhoto, RegExpMatchable {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String title;
    private final LocalDateTime addedDate;
    private final PhotoMetadata metadata;
    private final File file;
    private final long size;
    private boolean favourite;

    /**
     * Creates a new Photo.
     *
     * @param title     the title of the photo
     * @param addedDate the date the photo was added to the library
     * @param metadata  the photo metadata
     * @param file      the path to the photo file on disk
     */
    public Photo(String title, LocalDateTime addedDate,
                 PhotoMetadata metadata, File file) {
        this.title = title;
        this.addedDate = addedDate;
        this.metadata = metadata;
        this.file = file;
        this.size = file.length();
        this.favourite = false;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public LocalDateTime capturedDate() {
        return metadata.date();
    }

    @Override
    public LocalDateTime addedDate() {
        return addedDate;
    }

    @Override
    public boolean isFavourite() {
        return favourite;
    }

    @Override
    public void toggleFavourite() {
        this.favourite = !this.favourite;
    }

    @Override
    public Optional<? extends GPSCoordinates> getPlace() {
        return metadata.location();
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public File file() {
        return file;
    }

    /**
     * Matches this photo against the given regular expression.
     * Matching is based on the title, the file path and the metadata.
     *
     * @param regexp the regular expression to match against
     * @return true if title, file path or metadata matches
     */
    @Override
    public boolean matches(String regexp) {
        return Pattern.matches(regexp, title) ||
               Pattern.matches(regexp, file.getName()) ||
               Pattern.matches(regexp, file.getPath()) ||
               metadata.matches(regexp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(file, photo.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("File:").append(file.getPath()).append("\n");
        sb.append(" Title:").append(title);
        sb.append(" Added:").append(addedDate.format(FORMATTER));
        sb.append(" Size:").append(String.format("%,d", size)).append("\n");
        sb.append(" ").append(metadata);
        sb.append(" ").append(favourite ? "FAV" : "");
        return sb.toString();
    }
}

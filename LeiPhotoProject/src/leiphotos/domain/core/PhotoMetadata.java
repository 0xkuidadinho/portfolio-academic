package leiphotos.domain.core;

import leiphotos.utils.RegExpMatchable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Record representing the metadata of a photo.
 * Currently supports location, date, camera and manufacturer.
 * Implements RegExpMatchable based on location, camera and manufacturer.
 *
 * @param location     the GPS location (optional)
 * @param date         the date the photo was captured
 * @param camera       the camera model used
 * @param manufacturer the manufacturer of the camera
 */
public record PhotoMetadata(Optional<GPSLocation> location, LocalDateTime date,
                            String camera, String manufacturer)
        implements RegExpMatchable {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Checks if this metadata matches the given regular expression.
     * Matching is based on location description, camera and manufacturer.
     *
     * @param regexp the regular expression to match against
     * @return true if any of location, camera or manufacturer matches
     */
    @Override
    public boolean matches(String regexp) {
        boolean locationMatch = location.isPresent() && location.get().matches(regexp);
        boolean cameraMatch = camera != null && Pattern.matches(regexp, camera);
        boolean manufacturerMatch = manufacturer != null && Pattern.matches(regexp, manufacturer);
        return locationMatch || cameraMatch || manufacturerMatch;
    }

    @Override
    public String toString() {
        String locationStr = location.isPresent() ? location.get().toString() : "No Location";
        String dateStr = date != null ? date.format(FORMATTER) : "";
        String cameraStr = camera != null ? camera : "";
        String manufacturerStr = manufacturer != null ? manufacturer : "";
        return "[" + locationStr + ", " + dateStr + ", " + cameraStr + ", " + manufacturerStr + "]";
    }
}

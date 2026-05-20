package leiphotos.domain.core;

import leiphotos.domain.metadatareader.JpegMetadataReader;
import leiphotos.domain.metadatareader.JpegMetadataReaderFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Enum singleton that creates Photo objects.
 * Uses JpegMetadataReaderFactory to read JPEG metadata.
 */
public enum PhotoFactory {

    INSTANCE;

    /**
     * Creates a Photo from the given title and file path.
     *
     * @param title           the title for the photo
     * @param pathToPhotoFile the path to the JPEG file
     * @return a new Photo
     * @throws FileNotFoundException if the file does not exist
     */
    public Photo createPhoto(String title, String pathToPhotoFile)
            throws FileNotFoundException {
        File file = new File(pathToPhotoFile);
        if (!file.exists()) {
            throw new FileNotFoundException(
                    "File " + pathToPhotoFile + " not found or could not be open");
        }

        PhotoMetadata metadata;
        try {
            JpegMetadataReader reader =
                    JpegMetadataReaderFactory.INSTANCE.createMetadataReader(file);

            // Build GPS location if available
            Optional<GPSLocation> location;
            double[] coords = reader.getGpsLocation();
            if (coords != null && coords.length >= 2) {
                location = Optional.of(
                        new GPSLocation(coords[0], coords[1], ""));
            } else {
                location = Optional.empty();
            }

            // Get capture date
            LocalDateTime date = reader.getDate();
            if (date == null) {
                date = LocalDateTime.of(1970, 1, 1, 0, 0);
            }

            String camera = reader.getCamera() != null ? reader.getCamera() : "";
            String manufacturer = reader.getManufacturer() != null
                    ? reader.getManufacturer() : "";

            metadata = new PhotoMetadata(location, date, camera, manufacturer);

        } catch (FileNotFoundException e) {
            throw e;
        } catch (Exception e) {
            metadata = new PhotoMetadata(Optional.empty(),
                    LocalDateTime.of(1970, 1, 1, 0, 0), "", "");
        }

        return new Photo(title, LocalDateTime.now(), metadata, file);
    }
}

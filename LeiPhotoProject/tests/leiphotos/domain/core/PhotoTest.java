package leiphotos.domain.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import leiphotos.domain.facade.GPSCoordinates;

class PhotoTest {

	@Test
	void testCreatePhotoWithoutGPS() {
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		PhotoMetadata meta = new PhotoMetadata(java.util.Optional.empty(), expectedCapturedDate, "test camara",
				"test manufaturer");
		File expectedFile = new File("test.jpg");
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		Photo photo = new Photo(expectedTitle, expectedAddedDate, meta, expectedFile);

		LocalDateTime actualCapturedDate = photo.capturedDate();
		assertEquals(expectedCapturedDate, actualCapturedDate);

		LocalDateTime actualAddedDate = photo.addedDate();
		assertEquals(expectedAddedDate, actualAddedDate);

		boolean isFavourite = photo.isFavourite();
		assertFalse(isFavourite);

		String actualTitle = photo.title();
		assertEquals(expectedTitle, actualTitle);

		Optional<? extends GPSCoordinates> actualPlace = photo.getPlace();
		assertEquals(Optional.empty(), actualPlace);
	}

	@Test
	void testCreatePhotoWithGPS() {
		GPSLocation location = new GPSLocation(38.75, -9.18, "SLB");
		PhotoMetadata meta = new PhotoMetadata(Optional.of(location), LocalDateTime.now(), "", "");
		Photo photo = new Photo("", LocalDateTime.now(), meta, new File("test1.jpg"));
		
		Optional<? extends GPSCoordinates> lugar = photo.getPlace();
		assertEquals(Optional.of(location), lugar);
	}

	@Test
	void testToggleFavourite() {
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		PhotoMetadata meta = new PhotoMetadata(java.util.Optional.empty(), expectedCapturedDate, "test camara",
				"test manufaturer");
		File expectedFile = new File("test.jpg");
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		Photo photo = new Photo(expectedTitle, expectedAddedDate, meta, expectedFile);

		photo.toggleFavourite();
		boolean isFavouriteAfterToggle1 = photo.isFavourite();
		photo.toggleFavourite();
		boolean isFavouriteAfterToggle2 = photo.isFavourite();

		// Assert
		assertTrue(isFavouriteAfterToggle1);
		assertFalse(isFavouriteAfterToggle2);
	}

	@Test
	void testSize() { // requires the use of a mock file class
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		PhotoMetadata meta = new PhotoMetadata(java.util.Optional.empty(), expectedCapturedDate, "test camara",
				"test manufaturer");
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg", expectedSize);
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		Photo photoTest = new Photo(expectedTitle, expectedAddedDate, meta, expectedFile);
		long actualSize = photoTest.size();
		
		assertEquals(expectedSize, actualSize);
	}

	@Test
	void testNoMatches() {
		String regexp = "Exp.*";
		PhotoMetadata meta = new PhotoMetadata(java.util.Optional.empty(), LocalDateTime.now(), "", "");
		Photo photo = new Photo("Test Photo", LocalDateTime.now(), meta, new File("test.jpg"));

		boolean matches = photo.matches(regexp);
		assertFalse(matches);
	}

	@Test
	void testMatchesTitle() {
		String regexp = "Photo Test";
		PhotoMetadata meta = new PhotoMetadata(java.util.Optional.empty(), LocalDateTime.now(), "", "");
		Photo photoTest = new Photo("Photo Test", LocalDateTime.now(), meta, new File("test.jpg"));
		
		assertTrue(photoTest.matches(regexp));
		
	}

	@Test
	void testMatchesFile() {
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		PhotoMetadata meta = new PhotoMetadata(java.util.Optional.empty(), expectedCapturedDate, "test camara",
				"test manufaturer");
		File expectedFile = new File("test.jpg");
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		Photo photoTest = new Photo(expectedTitle, expectedAddedDate, meta, expectedFile);
		
		assertEquals(expectedFile, photoTest.file());
	}

	@Test
	void testEquals() {
		File file1 = new File("test1.jpg");
		File file2 = new File("test2.jpg");
		File file3 = new File("test1.jpg");

		PhotoMetadata meta = new PhotoMetadata(java.util.Optional.empty(), LocalDateTime.now(), "", "");
		Photo photo1 = new Photo("", LocalDateTime.now(), meta, file1);
		Photo photo2 = new Photo("", LocalDateTime.now(), meta, file2);
		Photo photo3 = new Photo("", LocalDateTime.now(), meta, file3);
		
		boolean equal1 = photo1.equals(photo2);
		boolean equal2 = photo1.equals(photo3);
		boolean equal3 = photo2.equals(photo3);
		
		assertFalse(equal1);
		assertTrue(equal2);
		assertFalse(equal3);
	}

	@Test
	void testHashCode() {
		File file1 = new File("test1.jpg");
		File file2 = new File("test2.jpg");

		PhotoMetadata meta = new PhotoMetadata(java.util.Optional.empty(),
				LocalDateTime.now(), "", "");

		Photo photo1 = new Photo("Photo A", LocalDateTime.now(), meta, file1);
		Photo photo2 = new Photo("Photo B", LocalDateTime.now(), meta, new File("test1.jpg"));
		Photo photo3 = new Photo("Photo C", LocalDateTime.now(), meta, file2);
		
		assertEquals(photo1.hashCode(), photo2.hashCode());
		assertNotEquals(photo1.hashCode(), photo3.hashCode());
	}

}
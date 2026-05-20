package leiphotos.domain.core;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import leiphotos.domain.facade.IPhoto;

class RecentlyDeletedLibraryTest {

	private static final int SECONDS_IN_TRASH = 3; //CHANGE ME
	private static final int SECONDS_TO_CHECK = 1;  //CHANGE ME
	
	private RecentlyDeletedLibrary library;

	@BeforeEach
	void setUp() {
	    library = new RecentlyDeletedLibrary(SECONDS_IN_TRASH, SECONDS_TO_CHECK);
	}

	@Test
	void testAddPhoto() {
		MockPhoto photo = new MockPhoto(new File("Test.jpg"));

		assertTrue(library.addPhoto(photo));
		assertTrue(library.getPhotos().contains(photo));
		assertEquals(1, library.getNumberOfPhotos());
	}

	@Test
	void testAddExistingPhoto() {
		MockPhoto photo = new MockPhoto(new File("Test.jpg"));

		assertTrue(library.addPhoto(photo));
		assertFalse(library.addPhoto(photo));
		assertEquals(1, library.getNumberOfPhotos());
	}


	@Test
	void testDeletePhoto() {
		MockPhoto photo = new MockPhoto(new File("Test.jpg"));
		library.addPhoto(photo);
		
		assertTrue(library.deletePhoto(photo));
		assertEquals(0, library.getNumberOfPhotos());
		assertFalse(library.getPhotos().contains(photo));
		
	}

	@Test
	void testDeleteNotExistingPhoto() {
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		library.addPhoto(photo1);
		
		assertFalse(library.deletePhoto(photo2));
	}


	@Test
	void testDeleteAll() {
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);

		assertTrue(library.deleteAll());
		assertEquals(0, library.getNumberOfPhotos());
	}

	@Test
	void testGetMatchesEmpty() {
		Collection<IPhoto> matches = library.getMatches(".*");
		assertNotNull(matches);
		
		assertTrue(matches.isEmpty());
	}

	@Test
	void testGetMatchesNotEmpty() {
		MockPhoto photoY = new MockPhoto(new File("Y.jpg"),true);
		MockPhoto photoN = new MockPhoto(new File("N.jpg"),false);
		library.addPhoto(photoY);
		library.addPhoto(photoN);
		Collection<IPhoto> matches = library.getMatches(".*");
		
		assertFalse(matches.isEmpty());	
	}

	@Test
	void testAutomaticDelete() throws InterruptedException {
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		Thread.sleep(SECONDS_IN_TRASH * 1000);
		Collection<IPhoto> photos = library.getPhotos();
		
		assertTrue(photos.isEmpty());
		
	}


	@Test
	void testAutomaticDeleteNoEffectTooSoon() {
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		Collection<IPhoto> photos = library.getPhotos();
		
		assertEquals(2, library.getNumberOfPhotos());
		assertTrue(photos.contains(photo1));
		assertTrue(photos.contains(photo2));
	}

	@Test
	void testAutomaticDeleteNoEffectCheckedJustBefore() throws InterruptedException {    	
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		Thread.sleep(SECONDS_TO_CHECK * 1000);
		Collection<IPhoto> photos = library.getPhotos();
		
		assertEquals(2, photos.size());
	    assertTrue(photos.contains(photo1));
	    assertTrue(photos.contains(photo2));
	}
	
	@Test
	void testAddNullPhoto() {
	    assertFalse(library.addPhoto(null));
	    assertEquals(0, library.getNumberOfPhotos());
	}

	@Test
	void testDeleteAllEmpty() {
	    assertFalse(library.deleteAll());
	}
	
	@Test
	void testAddAfterDeleteAll() {
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		library.addPhoto(photo1);
		library.deleteAll();

		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		assertTrue(library.addPhoto(photo2));
		assertEquals(1, library.getNumberOfPhotos());
		assertTrue(library.getPhotos().contains(photo2));
	}

}
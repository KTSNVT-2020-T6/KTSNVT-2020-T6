package main.kts.service;

import static main.kts.constants.ImageConstants.*;
import static main.kts.constants.RateConstants.FALSE_RATE_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.ImageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ImageServiceUnitTest {

	@Autowired
	private ImageService imageService;
	
	@MockBean
	private ImageRepository imageRepository;
	
	@MockBean
	private CulturalOfferRepository culturalOfferRepository;
	
	@Before 
	public void setup() {
		Set<Image> images = new HashSet<Image>();
		List<Image> imagesList = new ArrayList<Image>();
		CulturalOffer existingCulturalOffer = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		images.add(new Image(DB_IMAGE_NAME, DB_IMAGE_RELATIVE_PATH));
		imagesList.add(new Image(DB_IMAGE_NAME, DB_IMAGE_RELATIVE_PATH));
		existingCulturalOffer.setImage(images);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Image> imagePage = new PageImpl<>(imagesList, pageable, PAGEABLE_TOTAL_ELEMENTS);


		given(imageRepository.findByActive(true)).willReturn(imagesList);
		given(imageRepository.findByActive(pageable, true)).willReturn(imagePage);

		Image image = new Image(NEW_IMAGE_NAME, NEW_IMAGE_RELATIVE_PATH);
		Image updateImage = new Image(NEW_IMAGE_NAME, UPDATED_IMAGE_RELATIVE_PATH);

		Image savedImage= new Image(NEW_IMAGE_NAME, NEW_IMAGE_RELATIVE_PATH);
		savedImage.setId(IMAGE_ID);
		Image updatedImage = new Image(NEW_IMAGE_NAME, UPDATED_IMAGE_RELATIVE_PATH);
		updatedImage.setId(IMAGE_ID);


		Image imageFound = new Image(DB_IMAGE_ID, DB_IMAGE_NAME, DB_IMAGE_RELATIVE_PATH);
		
		CulturalOffer savedCO = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		
		given(culturalOfferRepository.findById(DB_CULTURAL_OFFER_ID)).willReturn(Optional.of(existingCulturalOffer));
		given(culturalOfferRepository.findById(FALSE_CULTURAL_OFFER_ID)).willReturn(null);
		given(culturalOfferRepository.save(existingCulturalOffer)).willReturn(savedCO);
		
		given(imageRepository.findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID)).willReturn(imagesList);
		
		//given(imageRepository.findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID)).willReturn(new ArrayList<Image>());
		
		given(imageRepository.save(image)).willReturn(savedImage);
		given(imageRepository.save(updateImage)).willReturn(updatedImage);
		given(imageRepository.findById(IMAGE_ID)).willReturn(Optional.of(image));
		given(imageRepository.findById(FALSE_IMAGE_ID)).willReturn(Optional.empty());

		doNothing().when(imageRepository).delete(savedImage);

	}
	
	@Test
	public void testFindAll() {
		List<Image> found = imageService.findAll();

		verify(imageRepository, times(1)).findByActive(true);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Image> found = imageService.findAll(pageable);

		verify(imageRepository, times(1)).findByActive(pageable, true);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}
	
	@Test
	public void testCreate() throws Exception {
		Image image = new Image(NEW_IMAGE_NAME,NEW_IMAGE_RELATIVE_PATH);
		Image created = imageService.create(image);
		verify(imageRepository, times(1)).save(image);

		assertEquals(NEW_IMAGE_RELATIVE_PATH, created.getRelativePath());
	}

	@Test
	public void testUpdate() throws Exception {
		Image image = new Image(NEW_IMAGE_NAME, UPDATED_IMAGE_RELATIVE_PATH);
		Image created = imageService.update(image, IMAGE_ID);

		verify(imageRepository, times(1)).findById(IMAGE_ID);
		verify(imageRepository, times(1)).save(image);
		

		assertEquals(UPDATED_IMAGE_RELATIVE_PATH, created.getRelativePath());
	}


	@Test
	public void testDelete() throws Exception {
		Image image = new Image(NEW_IMAGE_NAME, NEW_IMAGE_RELATIVE_PATH);
		imageService.delete(IMAGE_ID);

		verify(imageRepository, times(1)).findById(IMAGE_ID);
		verify(imageRepository, times(1)).save(image);

	}

	@Test
	public void testDelete_GivenFalseId() throws Exception {
		Throwable exception = assertThrows(Exception.class, () -> {
			imageService.delete(FALSE_IMAGE_ID);
		});
		verify(imageRepository, times(1)).findById(FALSE_IMAGE_ID);
		assertEquals("Image with given id doesn't exist", exception.getMessage());
	}}

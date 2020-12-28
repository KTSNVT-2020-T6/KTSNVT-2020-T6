package main.kts.controller;
import static main.kts.constants.ImageConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import main.kts.dto.ImageDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.model.Image;
import main.kts.repository.ImageRepository;
import main.kts.service.ImageService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ImageControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ImageRepository imageRepository;

	private String accessToken;

	@Before
	public void loginAdmin() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO(ADMIN_EMAIL_LOGIN, ADMIN_PASSWORD_LOGIN), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}
	
	
	@Test
    public void testGetAllImages() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

        ResponseEntity<ImageDTO[]> responseEntity =
                restTemplate.getForEntity("/api/image",
                        ImageDTO[].class);

        ImageDTO[] images = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_SIZE, images.length);
        assertEquals(IMAGE_RELATIVE_PATH, images[0].getRelativePath());
    }

	@Test
    public void testGetAllImagesPageable() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<ImageDTO[]> responseEntity = restTemplate.exchange("/api/image?page=0&size=2", HttpMethod.GET,
				request, ImageDTO[].class);
        ImageDTO[] images = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_SIZE_PAGABLE, images.length);
        assertEquals(IMAGE_RELATIVE_PATH, images[0].getRelativePath());
    }
	
	@Test
    public void testGetById() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<ImageDTO> responseEntity =
                restTemplate.exchange("/api/image/1",HttpMethod.GET,
        				request, ImageDTO.class);

        ImageDTO image = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(image);
        assertEquals(IMAGE_RELATIVE_PATH, image.getRelativePath());
    }
	
	@Test
	public void testGetById_GivenFalseId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<ImageDTO> responseEntity = restTemplate.exchange("/api/image/" + DB_FALSE_IMAGE_ID, HttpMethod.GET,
				request, ImageDTO.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}
	
    @Test
    @Transactional
    @Rollback(true)
    public void testCreateImage() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("file", new FileSystemResource("C:\\Users\\Korisnik\\Pictures\\Saved Pictures\\untitled.jpg")); // load file into parameter
		headers.set("Content-Type", "multipart/form-data"); // we are sending a form
		headers.set("Accept", "text/plain"); // looks like you want a string back

		int size = imageService.findAll().size(); 
		// Fire!
		ResponseEntity<String> responseEntity = restTemplate.exchange(
		    "/api/image",
		    HttpMethod.POST,
		    new HttpEntity<MultiValueMap<String, Object>>(parameters, headers),
		    String.class
		);
    	
    	
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Image> images = imageService.findAll();
        assertEquals(size + 1, images.size());       
        assertEquals(IMAGE_REL_PATH, images.get(images.size()-1).getRelativePath());

        imageService.delete(images.get(images.size()-1).getId());
    }
    

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateImage() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        ResponseEntity<ImageDTO> responseEntity =
                restTemplate.exchange("/api/image/1", HttpMethod.PUT,
                        new HttpEntity<ImageDTO>(new ImageDTO(IMAGE_NAME_NEW, DB_IMAGE_RELATIVE_PATH), headers),
                        ImageDTO.class);

        ImageDTO imageDTO = responseEntity.getBody();

        // provera odgovora servera
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(imageDTO);
        assertEquals(DB_IMAGE_ID, imageDTO.getId());
        assertEquals(DB_IMAGE_RELATIVE_PATH, imageDTO.getRelativePath());

        Image image = imageService.findOne(DB_IMAGE_ID);
        assertEquals(DB_IMAGE_ID, image.getId());
        assertEquals(DB_IMAGE_RELATIVE_PATH, image.getrelativePath());

        image.setName(DB_IMAGE_NAME);
        imageService.update(image, image.getId());

    }
    
    @Test
	public void testUpdateImage_GivenNonexistentId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", NEW_IMAGE_NAME);
		jsonObject.put("relativePath", NEW_IMAGE_RELATIVE_PATH);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/image/" + DB_FALSE_IMAGE_ID, HttpMethod.PUT,
				request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
    
    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteImage() throws Exception {    	
    	List<Image> images = imageService.findAll();
        int size = imageService.findAll().size();
        HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		
		ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/image/" + DELETE_ID,
                        HttpMethod.DELETE, new HttpEntity<Object>(null,headers), Void.class);
		
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        assertEquals(size - 1, imageService.findAll().size());

    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteImage_GivenNonexistentId() throws Exception {    	
        HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		
		ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/image/" + DB_FALSE_IMAGE_ID,
                        HttpMethod.DELETE, new HttpEntity<Object>(null,headers), Void.class);
		
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }
}

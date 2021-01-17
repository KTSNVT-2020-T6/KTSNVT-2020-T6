package main.kts.controller;

import static main.kts.constants.PostConstants.*;
import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

import main.kts.dto.PostDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PostControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostService postService;

	private String accessToken;

	@Before
	public void login() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO(ADMIN_EMAIL_LOGIN, ADMIN_PASSWORD_LOGIN), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}

	@Test
	public void testGetAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<PostDTO[]> responseEntity = restTemplate.exchange("/api/post", HttpMethod.GET, request,
				PostDTO[].class);
		PostDTO[] posts = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_SIZE, posts.length);
	}

	@Test
	public void testGetPage() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<PostDTO[]> responseEntity = restTemplate.exchange("/api/post?page=0&size=2", HttpMethod.GET,
				request, PostDTO[].class);
		PostDTO[] posts = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_SIZE, posts.length);
		assertEquals(DB_POST_TEXT, posts[0].getText());
	}

	@Test
	public void testGetById() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/api/post/" + DB_POST_ID, HttpMethod.GET,
				request, PostDTO.class);
		PostDTO post = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_POST_ID, post.getId());
		assertEquals(DB_POST_TEXT, post.getText());
	}

	@Test
	public void testGetById_GivenFalseId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/api/post/" + DB_FALSE_POST_ID, HttpMethod.GET,
				request, PostDTO.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreate() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", NEW_POST_TEXT);
		jsonObject.put("date", NEW_DATE_FORMAT);
		JSONObject image = new JSONObject();
		image.put("id", DB_IMAGE_ID);
		image.put("name", DB_IMAGE_NAME);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH);
		jsonObject.put("imageDTO", image);
		jsonObject.put("culturalOfferId", 1L);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.postForEntity("/api/post", request, PostDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(NEW_POST_TEXT, responseEntity.getBody().getText());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", UPDATED_POST_TEXT);
		jsonObject.put("date", NEW_DATE_FORMAT);
		JSONObject image = new JSONObject();
		image.put("id", DB_IMAGE_ID);
		image.put("name", DB_IMAGE_NAME);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH);
		jsonObject.put("imageDTO", image);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/api/post/" + DB_POST_ID, HttpMethod.PUT,
				request, PostDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(UPDATED_POST_TEXT, responseEntity.getBody().getText());

	}

	@Test
	public void testUpdate_GivenNonexistentId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", UPDATED_POST_TEXT);
		jsonObject.put("date", NEW_DATE_FORMAT);
		JSONObject image = new JSONObject();
		image.put("id", DB_IMAGE_ID);
		image.put("name", DB_IMAGE_NAME);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH);
		jsonObject.put("imageDTO", image);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/api/post/" + DB_FALSE_POST_ID, HttpMethod.PUT,
				request, PostDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}


	@Test
	@Transactional
	@Rollback(true)
	public void testDelete() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/post/" + DELETE_ID, HttpMethod.DELETE,
				request, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testDelete_GivenNonexistentId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/post/" + DB_FALSE_POST_ID,
				HttpMethod.DELETE, request, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
}
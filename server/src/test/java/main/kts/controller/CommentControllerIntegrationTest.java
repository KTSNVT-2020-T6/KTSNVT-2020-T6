package main.kts.controller;

import static main.kts.constants.CommentConstants.*;
import static main.kts.constants.PostConstants.DB_IMAGE_ID;
import static main.kts.constants.PostConstants.DB_IMAGE_NAME;
import static main.kts.constants.PostConstants.DB_IMAGE_RELATIVE_PATH;
import static org.junit.Assert.assertEquals;

import java.util.List;

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

import main.kts.dto.CommentDTO;
import main.kts.dto.RateDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.service.CommentService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CommentControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CommentService commentService;

	private String accessToken;

	@Before
	public void login() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO(EMAIL_LOGIN, PASSWORD_LOGIN), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}

	@Test
	public void testGetAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CommentDTO[]> responseEntity = restTemplate.exchange("/api/comment", HttpMethod.GET, request,
				CommentDTO[].class);
		CommentDTO[] comments = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_SIZE, comments.length);
		assertEquals(DB_COMMENT_TEXT, comments[0].getText());
	}

	@Test
	public void testGetPage() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CommentDTO[]> responseEntity = restTemplate.exchange("/api/comment?page=0&size=2", HttpMethod.GET,
				request, CommentDTO[].class);
		CommentDTO[] comments = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_SIZE, comments.length);
	}

	@Test
	public void testGetCommentsByCOId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CommentDTO[]> responseEntity = restTemplate.exchange("/api/comment/culturaloffer_comments/" + DB_CULTURAL_OFFER_ID, HttpMethod.GET,
				request, CommentDTO[].class);
		CommentDTO[] comments = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		//assertEquals(DB_COMMENT_ID, comment.getId());
		//assertEquals(DB_COMMENT_TEXT, comment.getText());
	}
	
	@Test
	public void testGetById() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange("/api/comment/" + DB_COMMENT_ID, HttpMethod.GET,
				request, CommentDTO.class);
		CommentDTO comment = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_COMMENT_ID, comment.getId());
		assertEquals(DB_COMMENT_TEXT, comment.getText());
	}

	@Test
	public void testGetById_GivenFalseId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange("/api/comment/" + DB_FALSE_COMMENT_ID, HttpMethod.GET,
				request, CommentDTO.class);

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
		jsonObject.put("text", NEW_COMMENT_TEXT);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("nameSurname", USER_NAME_SURNAME);
		jsonObject.put("userId", DB_USER_ID);
		JSONObject image = new JSONObject();
		image.put("id", DB_IMAGE_IDD);
		image.put("name", DB_IMAGE_NAME);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH);
		jsonObject.put("imageDTO", image);
		JSONObject imageUser = new JSONObject();
		image.put("id", DB_IMAGE_ID_USER);
		image.put("name", DB_IMAGE_NAME_USER);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH_USER);
		jsonObject.put("userImage", imageUser);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID);

		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.postForEntity("/api/comment", request, CommentDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(NEW_COMMENT_TEXT, responseEntity.getBody().getText());

	}

	@Test
	public void testCreate_GivenNonexistentUserId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", NEW_COMMENT_TEXT);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("nameSurname", USER_NAME_SURNAME);
		jsonObject.put("userId", DB_FALSE_USER_ID);
		JSONObject image = new JSONObject();
		image.put("id", DB_IMAGE_IDD);
		image.put("name", DB_IMAGE_NAME);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH);
		jsonObject.put("imageDTO", image);
		JSONObject imageUser = new JSONObject();
		image.put("id", DB_IMAGE_ID_USER);
		image.put("name", DB_IMAGE_NAME_USER);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH_USER);
		jsonObject.put("userImage", imageUser);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/comment", request, String.class);
		System.out.println(responseEntity);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());


	}

	@Test
	public void testCreate_GivenNonexistentCulturalOfferId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", NEW_COMMENT_TEXT);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("nameSurname", USER_NAME_SURNAME);
		jsonObject.put("userId", DB_USER_ID);
		JSONObject image = new JSONObject();
		image.put("id", DB_IMAGE_IDD);
		image.put("name", DB_IMAGE_NAME);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH);
		jsonObject.put("imageDTO", image);
		JSONObject imageUser = new JSONObject();
		image.put("id", DB_IMAGE_ID_USER);
		image.put("name", DB_IMAGE_NAME_USER);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH_USER);
		jsonObject.put("userImage", imageUser);
		jsonObject.put("culturalOfferId", DB_FALSE_CULTURAL_OFFER_ID );
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/comment", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());


	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", UPDATED_COMMENT_TEXT);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("nameSurname", USER_NAME_SURNAME);
		jsonObject.put("userId", DB_USER_ID);
		JSONObject image = new JSONObject();
		image.put("id", DB_IMAGE_IDD);
		image.put("name", DB_IMAGE_NAME);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH);
		jsonObject.put("imageDTO", image);
		JSONObject imageUser = new JSONObject();
		image.put("id", DB_IMAGE_ID_USER);
		image.put("name", DB_IMAGE_NAME_USER);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH_USER);
		jsonObject.put("userImage", imageUser);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange("/api/comment/" + COMMENT_ID, HttpMethod.PUT, request,
				CommentDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(UPDATED_COMMENT_TEXT, responseEntity.getBody().getText());

	}

	@Test
	public void testUpdate_GivenNonexistentId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", UPDATED_COMMENT_TEXT);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("nameSurname", USER_NAME_SURNAME);
		jsonObject.put("userId", DB_USER_ID);
		JSONObject image = new JSONObject();
		image.put("id", DB_IMAGE_IDD);
		image.put("name", DB_IMAGE_NAME);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH);
		jsonObject.put("imageDTO", image);
		JSONObject imageUser = new JSONObject();
		image.put("id", DB_IMAGE_ID_USER);
		image.put("name", DB_IMAGE_NAME_USER);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH_USER);
		jsonObject.put("userImage", imageUser);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comment/" + DB_FALSE_COMMENT_ID, HttpMethod.PUT,
				request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdate_GivenNonexistentCulturalOfferId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", UPDATED_COMMENT_TEXT);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("nameSurname", USER_NAME_SURNAME);
		jsonObject.put("userId", DB_USER_ID);
		JSONObject image = new JSONObject();
		image.put("id", DB_IMAGE_IDD);
		image.put("name", DB_IMAGE_NAME);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH);
		jsonObject.put("imageDTO", image);
		JSONObject imageUser = new JSONObject();
		image.put("id", DB_IMAGE_ID_USER);
		image.put("name", DB_IMAGE_NAME_USER);
		image.put("relativePath", DB_IMAGE_RELATIVE_PATH_USER);
		jsonObject.put("userImage", imageUser);
		jsonObject.put("culturalOfferId", DB_FALSE_CULTURAL_OFFER_ID);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comment/" + COMMENT_ID, HttpMethod.PUT, request,
				String.class);

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

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comment/" + DELETE_ID, HttpMethod.DELETE,
				request, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testDelete_GivenNonexistentId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comment/" + DB_FALSE_COMMENT_ID,
				HttpMethod.DELETE, request, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
}
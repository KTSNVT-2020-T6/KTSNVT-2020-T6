package main.kts.controller;

import static main.kts.constants.RateConstants.*;
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

import main.kts.dto.RateDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.service.RateService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RateControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private RateService rateService;

	private String accessToken;

	@Before
	public void login() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO(USER_EMAIL, PASSWORD), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}

	@Test
	public void testGetAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<RateDTO[]> responseEntity = restTemplate.exchange("/api/rate", HttpMethod.GET, request,
				RateDTO[].class);
		RateDTO[] rates = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_SIZE, rates.length);
		assertEquals(DB_RATE_NUMBER, rates[0].getNumber());
	}

	@Test
	public void testGetPage() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<RateDTO[]> responseEntity = restTemplate.exchange("/api/rate?page=0&size=2", HttpMethod.GET,
				request, RateDTO[].class);
		RateDTO[] rates = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_SIZE, new Integer(rates.length));
		assertEquals(DB_RATE_NUMBER, rates[0].getNumber());
	}

	@Test
	public void testGetById() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<RateDTO> responseEntity = restTemplate.exchange("/api/rate/" + DB_RATE_ID, HttpMethod.GET,
				request, RateDTO.class);
		RateDTO rate = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_RATE_ID, rate.getId());
		assertEquals(DB_RATE_NUMBER, rate.getNumber());
	}

	@Test
	public void testGetById_GivenFalseId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<RateDTO> responseEntity = restTemplate.exchange("/api/rate/" + DB_FALSE_RATE_ID, HttpMethod.GET,
				request, RateDTO.class);

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
		jsonObject.put("number", NEW_RATE_NUMBER);
		jsonObject.put("registredUserId", DB_USER_ID);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID3);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<RateDTO> responseEntity = restTemplate.postForEntity("/api/rate", request, RateDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(NEW_RATE_NUMBER, responseEntity.getBody().getNumber());

	}

	@Test
	public void testCreate_GivenWrongNumber() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("number", DB_WRONG_NUMBER);
		jsonObject.put("registredUserId", DB_USER_ID);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID3);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/rate", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Object not valid", responseEntity.getBody());

	}

	@Test
	public void testCreate_GivenNonexistentUserId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("number", NEW_RATE_NUMBER);
		jsonObject.put("registredUserId", DB_FALSE_USER_ID);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID3);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/rate", request, String.class);
		System.out.println(responseEntity);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Cannot create rate: id not found", responseEntity.getBody());

	}

	@Test
	public void testCreate_GivenNonexistentCulturalOfferId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("number", NEW_RATE_NUMBER);
		jsonObject.put("registredUserId", DB_USER_ID);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID2);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/rate", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Cannot create rate: id not found", responseEntity.getBody());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("number", UPDATED_RATE_NUMBER);
		jsonObject.put("registredUserId", DB_USER_ID);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<RateDTO> responseEntity = restTemplate.exchange("/api/rate/" + RATE_ID, HttpMethod.PUT, request,
				RateDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(UPDATED_RATE_NUMBER, responseEntity.getBody().getNumber());

	}

	@Test
	public void testUpdate_GivenWrongNumber() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("number", DB_WRONG_NUMBER);
		jsonObject.put("registredUserId", DB_USER_ID);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/rate/" + RATE_ID, HttpMethod.PUT, request,
				String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Object not valid", responseEntity.getBody());
	}

	@Test
	public void testUpdate_GivenNonexistentId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("number", NEW_RATE_NUMBER);
		jsonObject.put("registredUserId", DB_USER_ID);
		jsonObject.put("culturalOfferId", DB_CULTURAL_OFFER_ID);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/rate/" + DB_FALSE_RATE_ID, HttpMethod.PUT,
				request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdate_GivenNonexistentCulturalOfferId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("number", NEW_RATE_NUMBER);
		jsonObject.put("registredUserId", DB_USER_ID);
		jsonObject.put("culturalOfferId", DB_FALSE_CULTURAL_OFFER_ID);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/rate/" + RATE_ID, HttpMethod.PUT, request,
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

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/rate/" + RATE_ID, HttpMethod.DELETE,
				request, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testDelete_GivenNonexistentId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/rate/" + DB_FALSE_RATE_ID,
				HttpMethod.DELETE, request, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
}

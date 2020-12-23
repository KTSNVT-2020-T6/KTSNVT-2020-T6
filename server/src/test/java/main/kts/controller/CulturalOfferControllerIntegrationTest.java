package main.kts.controller;

import static main.kts.constants.CulturalOfferConstants.*;
import static main.kts.constants.RateConstants.DB_CULTURAL_OFFER_ID3;
import static main.kts.constants.RateConstants.DB_FALSE_RATE_ID;
import static main.kts.constants.RateConstants.DB_RATE_ID;
import static main.kts.constants.RateConstants.DB_RATE_NUMBER;
import static main.kts.constants.RateConstants.DB_USER_ID;
import static main.kts.constants.RateConstants.NEW_RATE_NUMBER;
import static main.kts.constants.RateConstants.PAGEABLE_SIZE;
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

import main.kts.dto.CulturalOfferDTO;
import main.kts.dto.RateDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.service.CulturalOfferService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CulturalOfferService culturalOfferService;

	private String accessToken;
	
	@Before
	public void login() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO(ADMIN_EMAIL, ADMIN_PASSWORD), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}
	
	@Test
	public void testGetAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CulturalOfferDTO[]> responseEntity = restTemplate.exchange("/api/culturaloffer", HttpMethod.GET, request,
				CulturalOfferDTO[].class);
		CulturalOfferDTO[] culturalOffers = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_SIZE, culturalOffers.length);
		assertEquals(DB_NAME, culturalOffers[0].getName());
	}
	
	@Test
	public void testGetPage() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CulturalOfferDTO[]> responseEntity = restTemplate.exchange("/api/culturaloffer?page=0&size=2", HttpMethod.GET,
				request, CulturalOfferDTO[].class);
		CulturalOfferDTO[] culturalOffers = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_SIZE, new Integer(culturalOffers.length));
		assertEquals(DB_NAME, culturalOffers[0].getName());
	}

	@Test
	public void testGetById() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/api/culturaloffer/" + CO_ID, HttpMethod.GET,
				request, CulturalOfferDTO.class);
		CulturalOfferDTO culturalOffer = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_RATE_ID, culturalOffer.getId());
		assertEquals(DB_NAME, culturalOffer.getName());
	}
	
	@Test
	public void testGetById_GivenFalseId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/api/culturaloffer/" + FALSE_ID, HttpMethod.GET,
				request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}
	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testCreate() throws Exception {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("name", NEW_CO_NAME);
//		jsonObject.put("description", NEW_CO_DESCRIPTION);
//		jsonObject.put("city", NEW_CO_CITY);
//		jsonObject.put("date", NEW_CO_DATE);
//		jsonObject.put("lat", NEW_CO_LAT);
//		jsonObject.put("lon", NEW_CO_LON);
//		
//		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
//
//		ResponseEntity<RateDTO> responseEntity = restTemplate.postForEntity("/api/rate", request, RateDTO.class);
//
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertEquals(NEW_RATE_NUMBER, responseEntity.getBody().getNumber());
//
//	}
}

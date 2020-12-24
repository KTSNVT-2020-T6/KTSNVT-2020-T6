package main.kts.controller;

import static main.kts.constants.CulturalOfferConstants.ADMIN_EMAIL;
import static main.kts.constants.CulturalOfferConstants.ADMIN_PASSWORD;
import static main.kts.constants.CulturalOfferConstants.CO_ID;
import static main.kts.constants.CulturalOfferConstants.DB_CAT_DESC;
import static main.kts.constants.CulturalOfferConstants.DB_CAT_NAME;
import static main.kts.constants.CulturalOfferConstants.DB_CITY;
import static main.kts.constants.CulturalOfferConstants.DB_CONTENT;
import static main.kts.constants.CulturalOfferConstants.DB_CO_DATE;
import static main.kts.constants.CulturalOfferConstants.DB_CO_ID;
import static main.kts.constants.CulturalOfferConstants.DB_CO_NAME;
import static main.kts.constants.CulturalOfferConstants.DB_FALSE_CO_ID;
import static main.kts.constants.CulturalOfferConstants.DB_FALSE_TYPE_ID;
import static main.kts.constants.CulturalOfferConstants.DB_NAME;
import static main.kts.constants.CulturalOfferConstants.DB_SIZE;
import static main.kts.constants.CulturalOfferConstants.DB_SIZE_BY_CITY;
import static main.kts.constants.CulturalOfferConstants.DB_SIZE_BY_CONTENT;
import static main.kts.constants.CulturalOfferConstants.DB_TYPE_DESC;
import static main.kts.constants.CulturalOfferConstants.DB_TYPE_ID;
import static main.kts.constants.CulturalOfferConstants.DB_TYPE_NAME;
import static main.kts.constants.CulturalOfferConstants.DB_UPDATE_ID;
import static main.kts.constants.CulturalOfferConstants.FALSE_ID;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_CITY;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_DESCRIPTION;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_LAT;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_LON;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_NAME;
import static main.kts.constants.CulturalOfferConstants.NEW_DATE_FORMAT;
import static main.kts.constants.CulturalOfferConstants.PAGEABLE_SIZE;
import static main.kts.constants.CulturalOfferConstants.WRONG_LAT;
import static main.kts.constants.CulturalOfferConstants.WRONG_LON;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
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

import main.kts.dto.CategoryDTO;
import main.kts.dto.CulturalOfferDTO;
import main.kts.dto.ImageDTO;
import main.kts.dto.TypeDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.model.CulturalOffer;
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
	public void loginAdmin() {
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
		assertEquals(DB_CO_ID, culturalOffer.getId());
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
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreate() throws Exception {
		int size = culturalOfferService.findAll().size();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("name", NEW_CO_NAME);
//		jsonObject.put("description", NEW_CO_DESCRIPTION);
//		jsonObject.put("city", NEW_CO_CITY);
//		jsonObject.put("date", NEW_DATE_FORMAT);
//		jsonObject.put("lat", NEW_CO_LAT);
//		jsonObject.put("lon", NEW_CO_LON);
//		JSONObject typeObject = new JSONObject();
//		typeObject.put("id", DB_TYPE_ID);
//		typeObject.put("name", DB_TYPE_NAME);
//		typeObject.put("description", DB_TYPE_DESC);
//		jsonObject.put("typeDTO", typeObject);
//		JSONObject categoryObject = new JSONObject();
//		categoryObject.put("id", 1L);
//		categoryObject.put("name", DB_CAT_NAME);
//		categoryObject.put("description", DB_CAT_DESC);
//		typeObject.put("categoryDTO", categoryObject);
//		JSONArray imagesObject = new JSONArray();
//		jsonObject.put("imageDTO", imagesObject);
		
		CategoryDTO cat = new CategoryDTO(1L, DB_CAT_NAME, DB_CAT_DESC);
		TypeDTO type = new TypeDTO(DB_TYPE_ID, DB_TYPE_NAME, DB_TYPE_DESC, cat);
		CulturalOfferDTO co = new CulturalOfferDTO(NEW_CO_NAME, NEW_CO_DESCRIPTION, NEW_CO_CITY, new Date(), NEW_CO_LAT, NEW_CO_LON, type, new HashSet<ImageDTO>());
		
		
		HttpEntity<Object> request = new HttpEntity<Object>(co, headers);

		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.postForEntity("/api/culturaloffer", request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(NEW_CO_NAME, responseEntity.getBody().getName());
		
		List<CulturalOffer> coDTO = culturalOfferService.findAll();
        assertEquals(size + 1, coDTO.size());

     // uklanjamo dodatu kategoriju
        culturalOfferService.delete(responseEntity.getBody().getId());

	}
	
	
	@Test
	public void testCreate_GivenEmptyType() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", NEW_CO_NAME);
		jsonObject.put("description", NEW_CO_DESCRIPTION);
		jsonObject.put("city", NEW_CO_CITY);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("lat", NEW_CO_LAT);
		jsonObject.put("lon", NEW_CO_LON);
		JSONObject typeObject = new JSONObject();
		jsonObject.put("typeDTO", typeObject);
		JSONArray imagesObject = new JSONArray();
		jsonObject.put("imageDTO", imagesObject);
		
		
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.postForEntity("/api/culturaloffer", request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

	}
	
	@Test
	public void testCreate_GivenFalseTypeId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", NEW_CO_NAME);
		jsonObject.put("description", NEW_CO_DESCRIPTION);
		jsonObject.put("city", NEW_CO_CITY);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("lat", NEW_CO_LAT);
		jsonObject.put("lon", NEW_CO_LON);
		JSONObject typeObject = new JSONObject();
		typeObject.put("id", DB_FALSE_TYPE_ID);
		typeObject.put("name", DB_TYPE_NAME);
		typeObject.put("description", DB_TYPE_DESC);
		jsonObject.put("typeDTO", typeObject);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", 1L);
		categoryObject.put("name", DB_CAT_NAME);
		categoryObject.put("description", DB_CAT_DESC);
		typeObject.put("categoryDTO", categoryObject);
		JSONArray imagesObject = new JSONArray();
		jsonObject.put("imageDTO", imagesObject);
		
		
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.postForEntity("/api/culturaloffer", request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

	}
	
	@Test
	public void testCreate_GivenWrongDateFormat() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", NEW_CO_NAME);
		jsonObject.put("description", NEW_CO_DESCRIPTION);
		jsonObject.put("city", NEW_CO_CITY);
		jsonObject.put("date", DB_CO_DATE);
		jsonObject.put("lat", NEW_CO_LAT);
		jsonObject.put("lon", NEW_CO_LON);
		JSONObject typeObject = new JSONObject();
		typeObject.put("id", DB_TYPE_ID);
		typeObject.put("name", DB_TYPE_NAME);
		typeObject.put("description", DB_TYPE_DESC);
		jsonObject.put("typeDTO", typeObject);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", 1L);
		categoryObject.put("name", DB_CAT_NAME);
		categoryObject.put("description", DB_CAT_DESC);
		typeObject.put("categoryDTO", categoryObject);
		JSONArray imagesObject = new JSONArray();
		jsonObject.put("imageDTO", imagesObject);
		
		
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.postForEntity("/api/culturaloffer", request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

	}
	
	@Test
	public void testCreate_GivenLatitudeOutOfBounds() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", NEW_CO_NAME);
		jsonObject.put("description", NEW_CO_DESCRIPTION);
		jsonObject.put("city", NEW_CO_CITY);
		jsonObject.put("date", DB_CO_DATE);
		jsonObject.put("lat", WRONG_LAT);
		jsonObject.put("lon", NEW_CO_LON);
		JSONObject typeObject = new JSONObject();
		typeObject.put("id", DB_TYPE_ID);
		typeObject.put("name", DB_TYPE_NAME);
		typeObject.put("description", DB_TYPE_DESC);
		jsonObject.put("typeDTO", typeObject);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", 1L);
		categoryObject.put("name", DB_CAT_NAME);
		categoryObject.put("description", DB_CAT_DESC);
		typeObject.put("categoryDTO", categoryObject);
		JSONArray imagesObject = new JSONArray();
		jsonObject.put("imageDTO", imagesObject);
		
		
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.postForEntity("/api/culturaloffer", request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

	}
	
	@Test
	public void testCreate_GivenLongitudeOutOfBounds() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", NEW_CO_NAME);
		jsonObject.put("description", NEW_CO_DESCRIPTION);
		jsonObject.put("city", NEW_CO_CITY);
		jsonObject.put("date", DB_CO_DATE);
		jsonObject.put("lat", NEW_CO_LAT);
		jsonObject.put("lon", WRONG_LON);
		JSONObject typeObject = new JSONObject();
		typeObject.put("id", DB_TYPE_ID);
		typeObject.put("name", DB_TYPE_NAME);
		typeObject.put("description", DB_TYPE_DESC);
		jsonObject.put("typeDTO", typeObject);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", 1L);
		categoryObject.put("name", DB_CAT_NAME);
		categoryObject.put("description", DB_CAT_DESC);
		typeObject.put("categoryDTO", categoryObject);
		JSONArray imagesObject = new JSONArray();
		jsonObject.put("imageDTO", imagesObject);
		
		
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);

		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.postForEntity("/api/culturaloffer", request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("name", DB_CO_NAME); // unique
//		jsonObject.put("description", NEW_CO_DESCRIPTION);
//		jsonObject.put("city", NEW_CO_CITY);
//		jsonObject.put("date", NEW_DATE_FORMAT);
//		jsonObject.put("lat", NEW_CO_LAT);
//		jsonObject.put("lon", NEW_CO_LON);
//		JSONObject typeObject = new JSONObject();
//		typeObject.put("id", DB_TYPE_ID);
//		typeObject.put("name", DB_TYPE_NAME);
//		typeObject.put("description", DB_TYPE_DESC);
//		jsonObject.put("typeDTO", typeObject);
//		JSONObject categoryObject = new JSONObject();
//		categoryObject.put("id", 1L);
//		categoryObject.put("name", DB_CAT_NAME);
//		categoryObject.put("description", DB_CAT_DESC);
//		typeObject.put("categoryDTO", categoryObject);
//		JSONArray imagesObject = new JSONArray();
//		jsonObject.put("imageDTO", imagesObject);
		
		CategoryDTO cat = new CategoryDTO(1L, DB_CAT_NAME, DB_CAT_DESC);
		TypeDTO type = new TypeDTO(DB_TYPE_ID, DB_TYPE_NAME, DB_TYPE_DESC, cat);
		CulturalOfferDTO co = new CulturalOfferDTO(DB_CO_NAME, NEW_CO_DESCRIPTION, NEW_CO_CITY, new Date(), NEW_CO_LAT, NEW_CO_LON, type, new HashSet<ImageDTO>());
		
		
		HttpEntity<Object> request = new HttpEntity<Object>(co, headers);
		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/api/culturaloffer/"+DB_UPDATE_ID, HttpMethod.PUT, request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_CO_NAME, responseEntity.getBody().getName());
		assertEquals(DB_TYPE_ID, responseEntity.getBody().getTypeDTO().getId());

	}
	
	@Test
	public void testUpdate_GivenFalseId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", DB_CO_NAME); // unique
		jsonObject.put("description", NEW_CO_DESCRIPTION);
		jsonObject.put("city", NEW_CO_CITY);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("lat", NEW_CO_LAT);
		jsonObject.put("lon", NEW_CO_LON);
		JSONObject typeObject = new JSONObject();
		typeObject.put("id", DB_TYPE_ID);
		typeObject.put("name", DB_TYPE_NAME);
		typeObject.put("description", DB_TYPE_DESC);
		jsonObject.put("typeDTO", typeObject);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", 1L);
		categoryObject.put("name", DB_CAT_NAME);
		categoryObject.put("description", DB_CAT_DESC);
		typeObject.put("categoryDTO", categoryObject);
		JSONArray imagesObject = new JSONArray();
		jsonObject.put("imageDTO", imagesObject);
		
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/api/culturaloffer/"+DB_FALSE_CO_ID, HttpMethod.PUT, request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	
	}
	
	@Test
	public void testUpdate_GivenEmptyType() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", DB_CO_NAME); // unique
		jsonObject.put("description", NEW_CO_DESCRIPTION);
		jsonObject.put("city", NEW_CO_CITY);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("lat", NEW_CO_LAT);
		jsonObject.put("lon", NEW_CO_LON);
		JSONObject typeObject = new JSONObject();
		jsonObject.put("typeDTO", typeObject);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", 1L);
		categoryObject.put("name", DB_CAT_NAME);
		categoryObject.put("description", DB_CAT_DESC);
		typeObject.put("categoryDTO", categoryObject);
		JSONArray imagesObject = new JSONArray();
		jsonObject.put("imageDTO", imagesObject);
		
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/api/culturaloffer/"+DB_CO_ID, HttpMethod.PUT, request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	
	}
	
	@Test
	public void testUpdate_GivenWrongLatitude() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", DB_CO_NAME); // unique
		jsonObject.put("description", NEW_CO_DESCRIPTION);
		jsonObject.put("city", NEW_CO_CITY);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("lat", WRONG_LAT);
		jsonObject.put("lon", NEW_CO_LON);
		JSONObject typeObject = new JSONObject();
		typeObject.put("id", DB_TYPE_ID);
		typeObject.put("name", DB_TYPE_NAME);
		typeObject.put("description", DB_TYPE_DESC);
		jsonObject.put("typeDTO", typeObject);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", 1L);
		categoryObject.put("name", DB_CAT_NAME);
		categoryObject.put("description", DB_CAT_DESC);
		typeObject.put("categoryDTO", categoryObject);
		JSONArray imagesObject = new JSONArray();
		jsonObject.put("imageDTO", imagesObject);
		
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/api/culturaloffer/"+DB_CO_ID, HttpMethod.PUT, request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	
	}
	@Test
	public void testUpdate_GivenWrongLongitude() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", DB_CO_NAME); // unique
		jsonObject.put("description", NEW_CO_DESCRIPTION);
		jsonObject.put("city", NEW_CO_CITY);
		jsonObject.put("date", NEW_DATE_FORMAT);
		jsonObject.put("lat", NEW_CO_LAT);
		jsonObject.put("lon", WRONG_LON);
		JSONObject typeObject = new JSONObject();
		typeObject.put("id", DB_TYPE_ID);
		typeObject.put("name", DB_TYPE_NAME);
		typeObject.put("description", DB_TYPE_DESC);
		jsonObject.put("typeDTO", typeObject);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", 1L);
		categoryObject.put("name", DB_CAT_NAME);
		categoryObject.put("description", DB_CAT_DESC);
		typeObject.put("categoryDTO", categoryObject);
		JSONArray imagesObject = new JSONArray();
		jsonObject.put("imageDTO", imagesObject);
		
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/api/culturaloffer/"+DB_CO_ID, HttpMethod.PUT, request, CulturalOfferDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	
	}
	
    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() throws Exception {
        int size = culturalOfferService.findAll().size();
        
        HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/culturaloffer/" + DB_UPDATE_ID,
                        HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, culturalOfferService.findAll().size());
    }
    
    @Test
    public void testDelete_GivenFalseId() throws Exception {
        int size = culturalOfferService.findAll().size();
        
        HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/api/culturaloffer/" + DB_FALSE_CO_ID,
                        HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(size, culturalOfferService.findAll().size());
    }
    
	@Test
	public void testGetFromCity() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CulturalOfferDTO[]> responseEntity = restTemplate.exchange("/api/culturaloffer/from_city/"+DB_CITY, HttpMethod.GET, request,
				CulturalOfferDTO[].class);
		CulturalOfferDTO[] culturalOffers = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_SIZE_BY_CITY, culturalOffers.length);
		assertEquals(DB_CITY, culturalOffers[0].getCity().toUpperCase());
		
	}
	
	@Test
	public void testGetByContent() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<CulturalOfferDTO[]> responseEntity = restTemplate.exchange("/api/culturaloffer/content/"+DB_CONTENT, HttpMethod.GET, request,
				CulturalOfferDTO[].class);
		CulturalOfferDTO[] culturalOffers = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_SIZE_BY_CONTENT, culturalOffers.length);
		
	}
	
}

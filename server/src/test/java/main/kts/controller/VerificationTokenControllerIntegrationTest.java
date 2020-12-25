package main.kts.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.service.VerificationTokenService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class VerificationTokenControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static final String TOKEN = "926b2ee9-e220-4960-b17c-a49593e59d28";
	private static final String INVALID_TOKEN = "926b2ee9-e220-4960-b17c-a49593e59d50";
	
	
	@Test
	public void testConfirmRegistration() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/verification/"+TOKEN,
				String.class);
		String response = responseEntity.getBody();

		assertEquals("ok", response);
	}
	
	@Test
	public void testConfirmRegistration_InvalidToken() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/verification/"+INVALID_TOKEN,
				String.class);
		String response = responseEntity.getBody();

		assertEquals("redirect: access denied", response);
	}
	
	
}

package main.kts.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@SuiteClasses({AdminServiceUnitTest.class, RegisteredServiceUserUnitTest.class, RateServiceUnitTest.class,
	CulturalOfferServiceUnitTest.class, ImageServiceUnitTest.class, CategoryServiceUnitTest.class, 
	PostServiceUnitTest.class, CommentServiceUnitTest.class, AdminServiceIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteServices {

}

package main.kts.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@SuiteClasses({AdminServiceUnitTest.class, RegisteredUserServiceUnitTest.class, RateServiceUnitTest.class,
	CulturalOfferServiceUnitTest.class, ImageServiceUnitTest.class,TypeServiceUnitTest.class, CategoryServiceUnitTest.class, 
	PostServiceUnitTest.class, CommentServiceUnitTest.class, AuthorityServiceUnitTest.class,
	UserServiceIntegrationTest.class, AdminServiceIntegrationTest.class
	, RegisteredUserServiceIntegrationTest.class, RateServiceIntegrationTest.class, TypeServiceIntegrationTest.class, 
	CategoryServiceIntegrationTest.class, CulturalOfferServiceIntegrationTest.class, ImageServiceIntegrationTest.class, 
	PostServiceIntegrationTest.class, CommentServiceIntegrationTest.class, AuthorityServiceIntegrationTest.class })
@TestPropertySource("classpath:test.properties")
public class SuiteServices {

}

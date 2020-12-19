package main.kts;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import main.kts.repository.AdminRepositoryIntegrationTest;
import main.kts.service.AdminServiceIntegrationTest;
import main.kts.service.AdminServiceUnitTest;
import main.kts.service.CategoryServiceUnitTest;
import main.kts.service.CommentServiceUnitTest;
import main.kts.service.CulturalOfferServiceUnitTest;
import main.kts.service.ImageServiceUnitTest;
import main.kts.service.PostServiceUnitTest;
import main.kts.service.RateServiceUnitTest;
import main.kts.service.RegisteredUserUnitTest;

@RunWith(Suite.class)
@SuiteClasses({AdminServiceUnitTest.class, RegisteredUserUnitTest.class, RateServiceUnitTest.class,
	CulturalOfferServiceUnitTest.class, ImageServiceUnitTest.class, CategoryServiceUnitTest.class, 
	PostServiceUnitTest.class, CommentServiceUnitTest.class,
	AdminRepositoryIntegrationTest.class, AdminServiceIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {


}

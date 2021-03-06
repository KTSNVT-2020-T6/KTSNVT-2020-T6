package main.kts.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;


@RunWith(Suite.class)
@SuiteClasses({RateControllerIntegrationTest.class,CulturalOfferControllerIntegrationTest.class, TypeControllerIntegrationTest.class,CategoryControllerIntegrationTest.class, ImageControllerIntegrationTest.class, PostControllerIntegrationTest.class, CommentControllerIntegrationTest.class,
	UserControllerIntegrationTest.class,AdminControllerIntegrationTest.class,
	RegisteredUserControllerIntegrationTest.class,AuthorityControllerIntegrationTest.class ,AuthenticationControllerIntegrationTest.class, VerificationTokenControllerIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteControllers {

}

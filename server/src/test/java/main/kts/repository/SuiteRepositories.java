package main.kts.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;


@RunWith(Suite.class)
@SuiteClasses({	AdminRepositoryIntegrationTest.class, RegisteredUserRepositoryIntegrationTest.class, RateRepositoryIntegrationTest.class, CulturalOfferRepositoryIntegrationTest.class, ImageRepositoryIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteRepositories {

}

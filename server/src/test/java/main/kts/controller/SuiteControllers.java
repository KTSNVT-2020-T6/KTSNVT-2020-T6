package main.kts.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;


@RunWith(Suite.class)
@SuiteClasses({	AdminControllerIntegrationTest.class, RegisteredUserControllerIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteControllers {

}

package main.kts;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import main.kts.controller.AdminControllerIntegrationTest;


@RunWith(Suite.class)
@SuiteClasses({	AdminControllerIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteControllers {

}

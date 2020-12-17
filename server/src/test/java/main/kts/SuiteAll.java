package main.kts;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import main.kts.service.AdminServiceUnitTest;
import main.kts.service.RegisteredUserUnitTest;

@RunWith(Suite.class)
@SuiteClasses({RegisteredUserUnitTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {


}

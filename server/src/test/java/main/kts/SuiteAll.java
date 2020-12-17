package main.kts;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import main.kts.service.AdminServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({AdminServiceUnitTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {


}

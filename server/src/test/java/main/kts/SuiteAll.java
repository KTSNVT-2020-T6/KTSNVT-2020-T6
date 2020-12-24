package main.kts;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import main.kts.repository.SuiteRepositories;
import main.kts.controller.SuiteControllers;
import main.kts.service.SuiteServices;

@RunWith(Suite.class)
@SuiteClasses({SuiteRepositories.class,SuiteControllers.class,SuiteServices.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {


}

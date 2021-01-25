package main.kts.e2e;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;


import main.kts.pages.LoginPage;

public class LoginE2ETest {

    private WebDriver driver;

    private LoginPage loginPage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void LogInTestSuccess() throws InterruptedException {

        driver.get("https://localhost:4200/login");

        justWait();

        loginPage.getEmail().sendKeys("admin@gmail.com");
        loginPage.getPassword().sendKeys("asdf");
        loginPage.getLoginBtn().click();
        justWait();
        loginPage.ensureIsNotVisibleEmail();
        
        assertEquals("https://localhost:4200/", driver.getCurrentUrl());

    }
    
    @Test
    public void LogInTestWrongPassword() throws InterruptedException {

        driver.get("https://localhost:4200/login");

        justWait();

        loginPage.getEmail().sendKeys("admin@gmail.com");

        loginPage.getPassword().sendKeys("asdfghjk");

        loginPage.getLoginBtn().click();

        String toast = loginPage.ensureIsDisplayedToast();
        
        assertEquals("Wrong password or username", toast);
        assertEquals("https://localhost:4200/login", driver.getCurrentUrl());

    }

    private void justWait() throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(3000);
        }
    }
}

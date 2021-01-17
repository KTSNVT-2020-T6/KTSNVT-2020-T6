package main.kts.e2e;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import main.kts.pages.VerificationPage;

public class VerificationE2ETest {
	private WebDriver driver;

	private VerificationPage verificationPage;
    private String VERIFICATION_TOKEN = "926b2ee9-e220-4960-b17c-a49593e59d28";
    private String FALSE_VERIFICATION_TOKEN = "235n2ee9-e220-4960-b17c-a49593e59d28";
    
    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        verificationPage = PageFactory.initElements(driver, VerificationPage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
    
    @Test
    public void VerificationSuccess() throws InterruptedException {

        driver.get("http://localhost:4200/verification/"+VERIFICATION_TOKEN);
        justWait(2000);
        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());

    }
    
    @Test
    public void VerificationInvalidToken() throws InterruptedException {

        driver.get("http://localhost:4200/verification/"+FALSE_VERIFICATION_TOKEN);
        justWait(2000);
        assertEquals("Token is not valid or expired!", verificationPage.getVerificationMessage().getText());
        assertEquals("http://localhost:4200/verification/"+FALSE_VERIFICATION_TOKEN, driver.getCurrentUrl());

    }
    
    private void justWait(int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }
    
}

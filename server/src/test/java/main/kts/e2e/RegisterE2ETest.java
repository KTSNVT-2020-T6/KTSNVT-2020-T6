package main.kts.e2e;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import main.kts.pages.RegisterPage;

public class RegisterE2ETest {
	 private WebDriver driver;

	    private RegisterPage registerPage;

	    @Before
	    public void setUp() {

	        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
	        driver = new ChromeDriver();

	        driver.manage().window().maximize();
	        registerPage = PageFactory.initElements(driver, RegisterPage.class);
	    }

	    @After
	    public void tearDown() {
	        driver.quit();
	    }

	    @Test
	    public void SignUpTestSuccess() throws InterruptedException {

	        driver.get("http://localhost:4200/register");

	        justWait(1000);
	        
	        registerPage.getFirstName().sendKeys("Jana");
	        
	        registerPage.getLastName().sendKeys("Mara");

	        registerPage.getEmail().sendKeys("janamara@gmail.com");

	        registerPage.getPassword().sendKeys("asdf");

	        registerPage.getPasswordRepeat().sendKeys("asdf");
	        
	        registerPage.getRegisterBtn().click();
	        
	        justWait(15000);

	        registerPage.ensureIsNotVisibleEmail();

	        assertEquals("http://localhost:4200/home", driver.getCurrentUrl());

	    }
	    
	    @Test
	    public void SignUpTestEmailExists() throws InterruptedException {

	        driver.get("http://localhost:4200/register");

	        justWait(1000);
	        
	        registerPage.getFirstName().sendKeys("Jana");
	        
	        registerPage.getLastName().sendKeys("Mara");

	        registerPage.getEmail().sendKeys("admin@gmail.com");

	        registerPage.getPassword().sendKeys("asdf");

	        registerPage.getPasswordRepeat().sendKeys("asdf");
	        
	        registerPage.getRegisterBtn().click();
	        
	        String toast = registerPage.ensureIsDisplayedToast();
	        
	        assertEquals("Email already exists!", toast);
	        assertEquals("http://localhost:4200/register", driver.getCurrentUrl());

	    }

	    private void justWait(int milliseconds) throws InterruptedException {
	        synchronized (driver)
	        {
	            driver.wait(milliseconds);
	        }
	    }
}

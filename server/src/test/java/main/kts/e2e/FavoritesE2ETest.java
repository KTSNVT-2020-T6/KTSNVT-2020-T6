package main.kts.e2e;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import main.kts.pages.FavoritesPage;
import main.kts.pages.LoginPage;

public class FavoritesE2ETest {
	private WebDriver driver;

    private FavoritesPage favouritesPage;
    private LoginPage loginPage;
    
    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
         
        driver.get ("https://localhost:4200/login");
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getEmail().sendKeys("at@gmail.com");;
        loginPage.getPassword().sendKeys("asdf");;
        loginPage.getLoginBtn().click();
        justWait(5000);
        
        favouritesPage = PageFactory.initElements(driver, FavoritesPage.class);
        
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }
    
    @Test
    public void OpenInNewTest() throws InterruptedException {
    	
    	driver.get("https://localhost:4200/favorites");

        justWait(1000);
        
        favouritesPage.ensureIsDisplayedGoToPageBtn();
        favouritesPage.getGoToPage().click();
        assertEquals("https://localhost:4200/culturaloffer/22", driver.getCurrentUrl());
    }
    
    @Test
    public void UnsubscribeTest() throws InterruptedException {
    	justWait(2000);
    	driver.get("https://localhost:4200/favorites");
    	justWait(2000);
    	favouritesPage.ensureIsDisplayedUnsubscribeBtn();
        favouritesPage.getUnsubscribeBtn().click();
        favouritesPage.ensureIsNotVisibleGoToPageBtn();
        assertEquals("https://localhost:4200/favorites", driver.getCurrentUrl());

    }
    
    private void justWait(int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }
}

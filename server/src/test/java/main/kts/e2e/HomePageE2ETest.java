package main.kts.e2e;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import main.kts.pages.HomePage;
import main.kts.pages.LoginPage;

public class HomePageE2ETest {
	private WebDriver driver;

    private HomePage homePage;
    private LoginPage loginPage;
    
    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
         
        driver.get ("https://localhost:4200/login");
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getEmail().sendKeys("admin@gmail.com");;
        loginPage.getPassword().sendKeys("asdf");;
        loginPage.getLoginBtn().click();
        justWait(2000);
           
        homePage = PageFactory.initElements(driver, HomePage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void AddCulturalOfferTestSuccess() throws InterruptedException {

        driver.get("https://localhost:4200/");

        justWait(5000);
        
        homePage.ensureIsDisplayedAddNewBtn();
        homePage.getAddNewBtn().click();
        justWait(1000);
        homePage.getAddNewCOBtn().click();
        homePage.ensureIsDisplayedNameInput();
        
        homePage.getCategorySelect().click();
        homePage.getCategoryOption().click();
        justWait(2000);
        homePage.getTypeSelect().click();
        homePage.getTypeOption().click();
        
        homePage.getCoName().sendKeys("Galerija Fresaka");
        homePage.getCoDescription().sendKeys("gde je bila maja?");
        
        homePage.getGeocoder().sendKeys("Novi Sad");
        justWait(1000);
        homePage.getGeocoder().sendKeys(Keys.DOWN);
        homePage.getGeocoder().sendKeys(Keys.RETURN);
                
        homePage.getDatePicker().click();
        homePage.getTodaysDate().click();
        justWait(5000);
        homePage.getSubmitCO().click();
        
        justWait(1000);
        
        homePage.ensureIsNotVisibleName();        
        assertEquals("https://localhost:4200/", driver.getCurrentUrl());

    }
    
    @Test
    public void AddCulturalOfferTestError() throws InterruptedException {

        driver.get("https://localhost:4200/");

        justWait(5000);
        
        homePage.ensureIsDisplayedAddNewBtn();
        homePage.getAddNewBtn().click();
        justWait(1000);
        homePage.getAddNewCOBtn().click();
        homePage.ensureIsDisplayedNameInput();
        
        homePage.getCategorySelect().click();
        homePage.getCategoryOption().click();
        justWait(1500);
        homePage.getTypeSelect().click();
        homePage.getTypeOption().click();
        
        homePage.getCoName().sendKeys("Galerija Fresaka Nova");
        homePage.getCoDescription().sendKeys("gde je bila maja?");
        
                
        homePage.getDatePicker().click();
        homePage.getTodaysDate().click();
        justWait(1000);
        homePage.getSubmitCO().click();
        justWait(1000);
        String toast = homePage.ensureIsDisplayedToast();
        assertEquals("Location is required.", toast);
        assertEquals("https://localhost:4200/", driver.getCurrentUrl());

    }

    @Test
    public void AddAdminTestSuccess() throws InterruptedException {

    	driver.get("https://localhost:4200/");

        justWait(4000);
        
        homePage.ensureIsDisplayedAddNewBtn();
        homePage.getAddNewBtn().click();
        justWait(1000);
        homePage.getAddNewAdminBtn().click();
        homePage.ensureIsDisplayedEmail();
        
        homePage.getFirstName().sendKeys("Jana");       
        homePage.getLastName().sendKeys("Mara");
        homePage.getEmail().sendKeys("janamara123@gmail.com");
        homePage.getPassword().sendKeys("asdf");
        homePage.getPasswordRepeat().sendKeys("asdf");
        homePage.getRegisterBtn().click();
        
        justWait(3000);

        homePage.ensureIsNotVisibleEmail();

        assertEquals("https://localhost:4200/", driver.getCurrentUrl());

    }
    
    @Test
    public void AddAdminTestEmailExists() throws InterruptedException {

    	driver.get("https://localhost:4200/");

        justWait(4000);
        
        homePage.ensureIsDisplayedAddNewBtn();
        homePage.getAddNewBtn().click();
        homePage.getAddNewAdminBtn().click();
        homePage.ensureIsDisplayedEmail();
        
        homePage.getFirstName().sendKeys("Jana");
        
        homePage.getLastName().sendKeys("Mara");

        homePage.getEmail().sendKeys("admin@gmail.com");

        homePage.getPassword().sendKeys("asdf");

        homePage.getPasswordRepeat().sendKeys("asdf");
        
        homePage.getRegisterBtn().click();
        
        String toast = homePage.ensureIsDisplayedToast();
        
        assertEquals("Email already exists!", toast);

        assertEquals("https://localhost:4200/", driver.getCurrentUrl());

    }
    
    @Test
    public void OpenInNewTest() throws InterruptedException {
    	
    	driver.get("https://localhost:4200/");

        justWait(4000);
        
        homePage.ensureIsDisplayedGoToBtn();
        homePage.getGoToPage().click();
        assertEquals("https://localhost:4200/culturaloffer/1", driver.getCurrentUrl());
    }
    
    @Test
    public void ChangePageTestSuccess() throws InterruptedException {

        driver.get("https://localhost:4200/");

        justWait(7000);
        
        homePage.ensureIsDisplayedGoToBtn();
        homePage.getPage2().click();
        justWait(1000);
        homePage.ensureIsDisplayedPage3();
                
        assertEquals("https://localhost:4200/", driver.getCurrentUrl());

    }
    
    @Test
    public void FilterSuccess() throws InterruptedException {
    	
    	driver.get("https://localhost:4200/");

        justWait(1000);
        
        homePage.ensureIsDisplayedFilterBtn();
        homePage.getFilter().click();
        homePage.getSearchContent().click();
        homePage.getSearchContent().sendKeys("meep");
        justWait(500);
        homePage.getSubmitSearch().click();
        homePage.ensureIsDisplayedGoToBtn();
        homePage.ensureIsNotVisiblePage3();
        assertEquals("https://localhost:4200/", driver.getCurrentUrl());
    }
    
    @Test
    public void FilterCombinedSuccess() throws InterruptedException {
    	
    	driver.get("https://localhost:4200/");

        justWait(1000);
        
        homePage.ensureIsDisplayedFilterBtn();
        homePage.getFilter().click();
        homePage.getSearchContent().click();
        homePage.getSearchContent().sendKeys("meep");
        justWait(500);
        homePage.getSearchCity().sendKeys("Court");
        justWait(500);
        homePage.getSubmitSearch().click();
        homePage.ensureIsDisplayedGoToBtn();
        homePage.ensureIsNotVisiblePage3();
        assertEquals("https://localhost:4200/", driver.getCurrentUrl());
    }
    
    @Test
    public void FilterCitySuccess() throws InterruptedException {
    	
    	driver.get("https://localhost:4200/");

        justWait(1000);
        
        homePage.ensureIsDisplayedFilterBtn();
        homePage.getFilter().click();
        homePage.getSearchCity().click();
   
        justWait(500);
        homePage.getSearchCity().sendKeys("Court");
        justWait(500);
        homePage.getSubmitSearch().click();
        homePage.ensureIsDisplayedGoToBtn();
        homePage.ensureIsNotVisiblePage3();
        assertEquals("https://localhost:4200/", driver.getCurrentUrl());
    }


    @Test
    public void FilterRefresh() throws InterruptedException {
    	
    	driver.get("https://localhost:4200/");

        justWait(1000);
        
        homePage.ensureIsDisplayedFilterBtn();
        homePage.getFilter().click();
        homePage.getSearchContent().click();
        homePage.getSearchContent().sendKeys("meep");
        justWait(500);
        homePage.getSubmitSearch().click();
        justWait(1000);
        homePage.getRefresh().click();
        homePage.ensureIsDisplayedPage2();
        assertEquals("https://localhost:4200/", driver.getCurrentUrl());
    }
    
    @Test
    public void LogoutUserTest() throws InterruptedException {

    	driver.get("https://localhost:4200/");
    	justWait(4000);
    	
        homePage.ensureIsDisplayedAddNewBtn();
        homePage.getSignOut().click();
        justWait(1000);

        assertEquals("https://localhost:4200/login", driver.getCurrentUrl());

    }
    
    
    private void justWait(int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }
    
}

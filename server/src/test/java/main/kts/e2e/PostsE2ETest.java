package main.kts.e2e;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import main.kts.pages.LoginPage;
import main.kts.pages.PostsPage;

public class PostsE2ETest {
	 private WebDriver driver;

	    private PostsPage postsPage;
	    private LoginPage loginPage;

	    @Before
	    public void setUp() throws InterruptedException {

	        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	         
	        driver.get ("http://localhost:4200/login");
	        loginPage = PageFactory.initElements(driver, LoginPage.class);
	        loginPage.getEmail().sendKeys("admin@gmail.com");;
	        loginPage.getPassword().sendKeys("asdf");;
	        loginPage.getLoginBtn().click();
	        justWait(5000);
	        
	        postsPage = PageFactory.initElements(driver, PostsPage.class);
	    }

	    @After
	    public void tearDown() {
	        driver.quit();
	    }

	    @Test
	    public void DeletePostTestSuccess() throws InterruptedException {

	        driver.get("http://localhost:4200/posts");

	        justWait(7000);
	        
	        postsPage.ensureIsDisplayedDeleteButton();
	        postsPage.getDeletePostBtn().click();
	        postsPage.ensureIsDisplayedYesButton();
	        postsPage.getYesBtn().click();
	        postsPage.ensureIsNotVisibleDeleteButton();
	               
	        assertEquals("http://localhost:4200/posts", driver.getCurrentUrl());

	    }
	    
	    @Test
	    public void ChangePageTestSuccess() throws InterruptedException {

	        driver.get("http://localhost:4200/posts");

	        justWait(7000);
	        
	        postsPage.ensureIsDisplayedDeleteButton();
	        postsPage.getPage2().click();
	        justWait(1000);
	        postsPage.ensureIsDisplayedPage3();
	                
	        assertEquals("http://localhost:4200/posts", driver.getCurrentUrl());

	    }
	    
	    private void justWait(int milliseconds) throws InterruptedException {
	        synchronized (driver)
	        {
	            driver.wait(milliseconds);
	        }
	    }
	    
}

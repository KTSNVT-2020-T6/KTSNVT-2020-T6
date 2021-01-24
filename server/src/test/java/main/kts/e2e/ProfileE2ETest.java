package main.kts.e2e;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.annotation.Rollback;

import main.kts.pages.LoginPage;
import main.kts.pages.ProfilePage;

public class ProfileE2ETest {
	private WebDriver driver;
	private ProfilePage profilePage;
	private LoginPage loginPage;
	
	 @Before
	 public void setUp() throws InterruptedException {

	     System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
	     driver = new ChromeDriver();
	     driver.manage().window().maximize();      
	     
	 }
	 public void loginUser() throws InterruptedException
	 {
		 driver.get ("http://localhost:4200/login");
	     loginPage = PageFactory.initElements(driver, LoginPage.class);
	     loginPage.getEmail().sendKeys("at@gmail.com");;
	     loginPage.getPassword().sendKeys("asdf");;
	     loginPage.getLoginBtn().click();
	     justWait(5000);
	        
	     profilePage = PageFactory.initElements(driver, ProfilePage.class);
	 }
	 
	 public void loginAdmin() throws InterruptedException
	 {
		 driver.get ("http://localhost:4200/login");
	     loginPage = PageFactory.initElements(driver, LoginPage.class);
	     loginPage.getEmail().sendKeys("admin@gmail.com");;
	     loginPage.getPassword().sendKeys("asdf");;
	     loginPage.getLoginBtn().click();
	     justWait(5000);
	        
	     profilePage = PageFactory.initElements(driver, ProfilePage.class);
	 }
	 
	 @After
	 public void tearDown() {
	     driver.quit();
	 }
	 private void justWait(int milliseconds) throws InterruptedException {
	     synchronized (driver)
	     {
	         driver.wait(milliseconds);
	     }
	 }

	 @Test
	 @Rollback
	 public void ChangePasswordSuccess() throws InterruptedException {
		 loginAdmin();
		 driver.get("http://localhost:4200/profileDetails");
		 justWait(1000);
		 profilePage.ensureIsDisplayedChangePasswordBtn();
		 profilePage.getChangePasswordBtn().click();
		 profilePage.ensureIsDisplayedOldPasswordInput();
		 
		 profilePage.getOldPassword().sendKeys("asdf");
		 justWait(500);
		 profilePage.getNewPassword().sendKeys("new password");
		 justWait(1000);
		
		 profilePage.getSubmitPasswordBtn().click();
		 
		 profilePage.ensureIsNotVisibleOldPasswordInput();
		 justWait(1000);
		 String toast = profilePage.ensureIsDisplayedToast();
		 assertEquals("New password saved! You have to sign in again.", toast);
		 //New password saved! You have to sign in again.
		 assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
		 
	 }
	
	 @Test
	 public void ChangePasswordError() throws InterruptedException {
		 loginAdmin();
		 driver.get("http://localhost:4200/profileDetails");
		 justWait(1000);
		 profilePage.ensureIsDisplayedChangePasswordBtn();
		 profilePage.getChangePasswordBtn().click();
		 profilePage.ensureIsDisplayedOldPasswordInput();
		 
		 profilePage.getOldPassword().sendKeys("asdf");
		 justWait(500);
		 profilePage.getNewPassword().sendKeys("asdf");
		 justWait(1000);
		
		 profilePage.getSubmitPasswordBtn().click();
		 
		 profilePage.ensureIsNotVisibleOldPasswordInput();
		 justWait(1000);
		 String toast = profilePage.ensureIsDisplayedToast();
		 assertEquals("Passwords are the same!", toast);
		 assertEquals("http://localhost:4200/profileDetails", driver.getCurrentUrl());
		 
	 }
	 
	 @Test
	 @Rollback
	 public void DeactivateAccountSuccess() throws InterruptedException {
		 loginUser();
		
		 driver.get("http://localhost:4200/profileDetails");
		 justWait(1000);
		 profilePage.ensureIsDisplayedDeactivateBtn();
		 profilePage.getDeactivateBtn().click();
		 profilePage.ensureIsDisplayedYesBtn();
		 justWait(1000);
		 profilePage.getYesBtn().click();
	
		 justWait(1000);
		 String toast = profilePage.ensureIsDisplayedToast();
		 assertEquals("Profile successfully deactivated.", toast);
		 assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
	 }
	 /*
	 @Test
	 public void EditProfileSuccess() throws InterruptedException {
		 loginAdmin();
		 driver.get("http://localhost:4200/profileDetails");
		 justWait(2000);
		// WebDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("editBtn"))).clear();
		 profilePage.ensureIsDisplayedEditBtn();
		 profilePage.getEditBtn().click();
		// profilePage.ensureIsDisplayedFirstNameInput();
		// justWait(1000);
		// profilePage.getFirstName().clear();
		 justWait(1000);
		// profilePage.getFirstName().click();
		 profilePage.getFirstName().clear();
		 justWait(1000);
		 profilePage.getFirstName().sendKeys("ae");
		 justWait(1000);
		 profilePage.getSubmitBtn().click();
		 
		 justWait(1000);
		 String toast = profilePage.ensureIsDisplayedToast();
		 assertEquals("Profile information saved!", toast);
		 assertEquals("http://localhost:4200/profileDetails", driver.getCurrentUrl());
	
	 }
	 */
	 @Test
	 public void EditImageProfileSuccess() throws InterruptedException {
		 loginAdmin();
		 driver.get("http://localhost:4200/profileDetails");
		 justWait(1000);
		 profilePage.ensureIsDisplayedEditBtn();
		 profilePage.getEditBtn().click();
		 justWait(2000);
		 profilePage.ensureIsDisplayedFirstNameInput();
		
		 profilePage.getUploadImage().sendKeys("C:\\Users\\Korisnik\\Desktop\\image.jpg");
		 justWait(1000);
		 profilePage.getSubmitBtn().click();
		 
		 String toast = profilePage.ensureIsDisplayedToast();
		 assertEquals("Profile information saved!", toast);
		 assertEquals("http://localhost:4200/profileDetails", driver.getCurrentUrl());
		 justWait(2000);
	 }
	 /*
	 @Test
	 public void EditProfileError() throws InterruptedException {
		 loginAdmin();
		 driver.get("http://localhost:4200/profileDetails");
		 justWait(1000);
		 profilePage.ensureIsDisplayedEditBtn();
		 profilePage.getEditBtn().click();
		 profilePage.ensureIsDisplayedFirstNameInput();
		 
		 profilePage.getEmail().clear();
		 profilePage.getEmail().sendKeys("at@gmail.com"); // email alredy exist 
		 justWait(1000);
		 profilePage.getSubmitBtn().click();
		 
		 String toast = profilePage.ensureIsDisplayedToast();
		 assertEquals("Error saving data!", toast);
		 justWait(2000);
	 }
	 */
	
}

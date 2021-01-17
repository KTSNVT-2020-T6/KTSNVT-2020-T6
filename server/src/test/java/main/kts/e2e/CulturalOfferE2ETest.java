package main.kts.e2e;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.annotation.Rollback;

import main.kts.pages.CulturalOfferPage;
import main.kts.pages.LoginPage;

public class CulturalOfferE2ETest {

    private WebDriver driver;
    private CulturalOfferPage culturalOfferPage;
    private LoginPage loginPage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        culturalOfferPage = PageFactory.initElements(driver, CulturalOfferPage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    public void loginAdmin() throws InterruptedException {
    	 
        driver.get ("http://localhost:4200/login");
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getEmail().sendKeys("admin@gmail.com");;
        loginPage.getPassword().sendKeys("asdf");;
        loginPage.getLoginBtn().click();
        justWait(5000);
    }
    
    public void loginRegisteredUser() throws InterruptedException {
   	 
        driver.get("http://localhost:4200/login");
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getEmail().sendKeys("at@gmail.com");;
        loginPage.getPassword().sendKeys("asdf");;
        loginPage.getLoginBtn().click();
        justWait(5000);
    }
    
    @Test
    @Rollback
    public void SubscribeRegUserTestSuccess() throws InterruptedException {
    	loginRegisteredUser();
    	
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        int beforeClick = Integer.parseInt(culturalOfferPage.getNumberOfSubscribed().getText());
        culturalOfferPage.ensureIsDisplayedSubscribedButton();
        culturalOfferPage.getSubscribeButton().click();
        driver.get("http://localhost:4200/culturaloffer/1");
        justWait(1000);
        int afterClick = Integer.parseInt(culturalOfferPage.getNumberOfSubscribed().getText());
       
        assertEquals(beforeClick+1, afterClick);
    }
   
    
    @Test
    @Rollback
    public void UnsubscribeRegUserTestSuccess() throws InterruptedException {
    	loginRegisteredUser();
    	
        driver.get("http://localhost:4200/culturaloffer/21");
        
        justWait(1000);
        int beforeClick = Integer.parseInt(culturalOfferPage.getNumberOfSubscribed().getText());
        culturalOfferPage.ensureIsDisplayedUnsubscribedButton();
        culturalOfferPage.getUnsubscribeButton().click();
        driver.get("http://localhost:4200/culturaloffer/21");
        justWait(1000);
        int afterClick = Integer.parseInt(culturalOfferPage.getNumberOfSubscribed().getText());
       
        assertEquals(beforeClick-1, afterClick);

    }
    @Test
    @Rollback
    public void RateFiveTestSuccess() throws InterruptedException {
    	
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        double beforeClick = Double.parseDouble(culturalOfferPage.getAverageRate().getText());
        culturalOfferPage.ensureIsDisplayedFifthStar();
        culturalOfferPage.getRateFive().click();
        justWait(1000);
        driver.get("http://localhost:4200/culturaloffer/1");
        justWait(1000);
        double afterClick = Double.parseDouble(culturalOfferPage.getAverageRate().getText());
       
        assertNotEquals(beforeClick, afterClick);

    }
    @Test
    @Rollback
    public void RateFourTestSuccess() throws InterruptedException {
    	
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        double beforeClick = Double.parseDouble(culturalOfferPage.getAverageRate().getText());
        culturalOfferPage.ensureIsDisplayedForthStar();
        culturalOfferPage.getRateFour().click();
        justWait(1000);
        driver.get("http://localhost:4200/culturaloffer/1");
        justWait(1000);
        double afterClick = Double.parseDouble(culturalOfferPage.getAverageRate().getText());
       
        assertNotEquals(beforeClick, afterClick);

    }
    @Test
    @Rollback
    public void UploadImageCommentTestSuccess() throws InterruptedException {
    	
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        culturalOfferPage.getUploadButton().sendKeys("C:\\Users\\Korisnik\\Desktop\\image.jpg");
        justWait(1000);
        assertTrue(true);
       
    }
    
    @Test
    @Rollback
    public void SendCommentWithImageTestSuccess() throws InterruptedException {
    	
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        culturalOfferPage.getNewComment().sendKeys("Komentarisem ovu ponudu sa slikom");
        culturalOfferPage.getUploadButton().sendKeys("C:\\Users\\Korisnik\\Desktop\\image.jpg");
        culturalOfferPage.ensureIsDisplayedAddCommentButton();
        culturalOfferPage.getSendCommentButton().click();
        justWait(1000);
        
        String toast = culturalOfferPage.ensureIsDisplayedToast();
        
        assertEquals("Comment send!\nSaved!", toast);
       
    }
    @Test
    @Rollback
    public void SendCommentWithoutImageTestSuccess() throws InterruptedException {
    	
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        culturalOfferPage.getNewComment().sendKeys("Komentarisem ovu ponudu bez slike");
        culturalOfferPage.ensureIsDisplayedAddCommentButton();
        culturalOfferPage.getSendCommentButton().click();
        justWait(1000);
        
        String toast = culturalOfferPage.ensureIsDisplayedToast();
        
        assertEquals("Comment send!", toast);
       
    }
    @Test
    @Rollback
    public void SendCommentJustImageTestSuccess() throws InterruptedException {
    	

    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        culturalOfferPage.getUploadButton().sendKeys("C:\\Users\\Korisnik\\Desktop\\image.jpg");
        culturalOfferPage.ensureIsDisplayedAddCommentButton();
        culturalOfferPage.getSendCommentButton().click();
        justWait(1000);
        
        String toast = culturalOfferPage.ensureIsDisplayedToast();
        
        assertEquals("Saved!", toast);
       
    }
    
    @Test
    @Rollback
    public void SendEmptyCommentTestError() throws InterruptedException {
    	
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        culturalOfferPage.ensureIsDisplayedAddCommentButton();
        culturalOfferPage.getSendCommentButton().click();
        
        String toast = culturalOfferPage.ensureIsDisplayedToast();
        assertEquals("Comment cannot be empty!", toast);
       
    }
    
    @Test
    @Rollback
    public void EditCommentTestSuccess() throws InterruptedException {
    	
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        culturalOfferPage.ensureIsDisplayedEditComment();
        culturalOfferPage.getEditCommentButton().click();
        culturalOfferPage.getEditTextCommentButton().clear();
        culturalOfferPage.getEditTextCommentButton().sendKeys("Izmenjen sadrzaj komentara");
        culturalOfferPage.getEditImageCommentButton().sendKeys("C:\\Users\\Korisnik\\Desktop\\image.jpg");
        culturalOfferPage.getEditCommentButtonSave().click();
        String toast = culturalOfferPage.ensureIsDisplayedToast();
        justWait(1000);
        assertEquals("Comment edited!\nSaved!", toast);
       
    }
    @Test
    @Rollback
    public void EditCommentJustTextTestSuccess() throws InterruptedException {
    	
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        culturalOfferPage.ensureIsDisplayedEditComment();
        culturalOfferPage.getEditCommentButton().click();
        culturalOfferPage.getEditTextCommentButton().clear();
        culturalOfferPage.getEditTextCommentButton().sendKeys("Izmenjen sadrzaj komentara, samo komentar");
        justWait(2000);
        culturalOfferPage.getEditCommentButtonSave().click();
        String toast = culturalOfferPage.ensureIsDisplayedToast();
        justWait(1000);
        assertEquals("Comment edited!", toast);
       
    }
    @Test
    @Rollback
    public void EditCommentJustImageTestSuccess() throws InterruptedException {
    
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        culturalOfferPage.ensureIsDisplayedEditComment();
        culturalOfferPage.getEditCommentButton().click();
        culturalOfferPage.getEditImageCommentButton().sendKeys("C:\\Users\\Korisnik\\Desktop\\image.jpg");
        culturalOfferPage.getEditCommentButtonSave().click();
        String toast = culturalOfferPage.ensureIsDisplayedToast();
        justWait(1000);
        assertEquals("Comment edited!\nSaved!", toast);
       
    }
    
    @Test
    @Rollback
    public void DeleteCommentTestSuccess() throws InterruptedException {
    
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        culturalOfferPage.ensureIsDisplayedDeleteComment();
        culturalOfferPage.getDeleteButton().click();
        culturalOfferPage.getYesDeleteButton().click();
        culturalOfferPage.ensureIsNotVisibleYesButton();
        
        assertEquals("http://localhost:4200/culturaloffer/1", driver.getCurrentUrl());
       
    }
    
    @Test
    public void ChangePageTestSuccess() throws InterruptedException {
    	loginRegisteredUser();
        driver.get("http://localhost:4200/culturaloffer/1");

        justWait(1000);
     
        culturalOfferPage.getPage2().click();
        culturalOfferPage.ensureIsDisplayedBackPaginationButton();
        justWait(2000); 
        assertEquals("http://localhost:4200/culturaloffer/1", driver.getCurrentUrl());

    }
    
    @Test
    @Rollback
    public void DeleteCulturalOfferTestSuccess() throws InterruptedException {
    
    	loginAdmin();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        
        culturalOfferPage.ensureIsDisplayedDeleteCulturalOffer();
        culturalOfferPage.getDeleteCulturalOfferButton().click();
        culturalOfferPage.getYesDeleteButton().click();
        culturalOfferPage.ensureIsNotDisplayedDeleteCulturalOffer();
        justWait(15000);
        assertEquals("http://localhost:4200/home", driver.getCurrentUrl());
       
    }
       
    @Test
    @Rollback
    public void AddPostWithoutImageCulturalOfferTestSuccess() throws InterruptedException {
    
    	loginAdmin();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        
        culturalOfferPage.ensureIsDisplayedAddPost();
        justWait(1000);
        culturalOfferPage.getAddPostButton().click();
        justWait(1000);
        culturalOfferPage.getTextPost().sendKeys("Sadrzaj novog posta");
        justWait(1000);
        culturalOfferPage.getAddPostButtonSave().click();
        String toast = culturalOfferPage.ensureIsDisplayedToast();
        justWait(15000);
        assertEquals("Successfully added post!", toast);
        assertEquals("http://localhost:4200/culturaloffer/1", driver.getCurrentUrl());
       
    }
    @Test
    public void AddPostJustImageCulturalOfferTestError() throws InterruptedException {
    
    	loginAdmin();
        driver.get("http://localhost:4200/culturaloffer/1");
        
        justWait(1000);
        culturalOfferPage.ensureIsDisplayedAddPost();
        justWait(1000);
        culturalOfferPage.getAddPostButton().click();
        culturalOfferPage.getPostUploadImage().sendKeys("C:\\Users\\Korisnik\\Desktop\\image.jpg");
        justWait(1000);
        culturalOfferPage.ensureIsDisplayedAddPostAndNotClickable();
        assertEquals("http://localhost:4200/culturaloffer/1", driver.getCurrentUrl());
       
    }
    
    @Test
    @Rollback
    public void AddPostTextAndImageCulturalOfferTestSuccess() throws InterruptedException {
    
    	loginAdmin();
        driver.get("http://localhost:4200/culturaloffer/1");
        justWait(1000);
        
        culturalOfferPage.ensureIsDisplayedAddPost();
        justWait(1000);
        culturalOfferPage.getAddPostButton().click();
        justWait(1000);
        culturalOfferPage.getTextPost().sendKeys("Sadrzaj novog posta");
        culturalOfferPage.getPostUploadImage().sendKeys("C:\\Users\\Korisnik\\Desktop\\image.jpg");
        justWait(1000);
        culturalOfferPage.getAddPostButtonSave().click();
        String toast = culturalOfferPage.ensureIsDisplayedToast();
        justWait(15000);
        assertEquals("Successfully added post!", toast);
        assertEquals("http://localhost:4200/culturaloffer/1", driver.getCurrentUrl());
       
    }
    
    @Test
    @Rollback
    public void EditCulturalOfferTestSuccess() throws InterruptedException {
    
    	loginAdmin();
        driver.get("http://localhost:4200/culturaloffer/1");
        justWait(1000);
        
        culturalOfferPage.ensureIsDisplayedEditCulturalOffer();
        justWait(1000);
        culturalOfferPage.getEditCulturalOfferButton().click();
        justWait(1000);
    
        culturalOfferPage.getEditNameCulturalOffer().clear();
        culturalOfferPage.getEditNameCulturalOffer().sendKeys("Editovvan naazivv");
        culturalOfferPage.getEditDescriptionCulturalOffer().clear();
        culturalOfferPage.getEditDescriptionCulturalOffer().sendKeys("Editovanje opisa kulturne ponude");
        culturalOfferPage.getGeocoder().clear();
        culturalOfferPage.getGeocoder().sendKeys("Novi Sad");
        justWait(1000);
        culturalOfferPage.getGeocoder().sendKeys(Keys.DOWN);
        culturalOfferPage.getGeocoder().sendKeys(Keys.RETURN);
        justWait(1000);
        culturalOfferPage.getDatePickerToggleEditCOButton().click();
        culturalOfferPage.getTodaysDate().click();
        justWait(2500);
       
        culturalOfferPage.getEditTypeCulturalOffer().click();
        culturalOfferPage.getSelectType().click();
        justWait(2500);

        culturalOfferPage.getEditImageCulturalOfferButton().sendKeys("C:\\Users\\Korisnik\\Desktop\\image.jpg");
        justWait(1000);
        culturalOfferPage.getEditCultOfferSaveButton().click();
        justWait(1000);
        String toast = culturalOfferPage.ensureIsDisplayedToast();
        justWait(15000);
        assertEquals("Cultural offer information saved!", toast);
       
    }
    
    private void justWait(int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }
}

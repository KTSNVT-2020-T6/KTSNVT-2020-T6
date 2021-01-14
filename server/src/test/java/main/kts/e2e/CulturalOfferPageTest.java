package main.kts.e2e;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import main.kts.pages.CulturalOfferPage;

public class CulturalOfferPageTest {
	public static final String BASE_URL = "http://localhost:4200";

    private WebDriver driver;

    private CulturalOfferPage culturalOfferPage;

    //dodati jos chrome driver verziju 
    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.navigate().to(BASE_URL + "/culturaloffer/{id}");

        driver.manage().window().maximize();
        culturalOfferPage = PageFactory.initElements(driver, CulturalOfferPage.class);
    }

}

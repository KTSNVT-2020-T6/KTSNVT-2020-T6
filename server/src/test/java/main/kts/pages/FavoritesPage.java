package main.kts.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FavoritesPage {

	private WebDriver driver;
	
	// navbar buttons

    @FindBy(id = "unsubscribe29")
    private WebElement unsubscribeBtn;

    @FindBy(id = "goToPage22")
    private WebElement goToPage;

    
    public FavoritesPage() {
    }

    public FavoritesPage(WebDriver driver) {
        this.driver = driver;
    }
    
	public WebElement getUnsubscribeBtn() {
		return unsubscribeBtn;
	}

	public void setUnsubscribeBtn(WebElement unsubscribeBtn) {
		this.unsubscribeBtn = unsubscribeBtn;
	}

	public WebElement getGoToPage() {
		return goToPage;
	}

	public void setGoToPage(WebElement goToPage) {
		this.goToPage = goToPage;
	}
    
	public void ensureIsDisplayedUnsubscribeBtn() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("unsubscribe29")));
    }
	
	public void ensureIsDisplayedGoToPageBtn() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("goToPage22")));
    }
	
	public void ensureIsNotVisibleGoToPageBtn() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("goToPage29")));
    }
	
    
}

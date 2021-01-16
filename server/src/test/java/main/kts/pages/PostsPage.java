package main.kts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostsPage {
	private WebDriver driver;

    @FindBy(id = "deletePostBtn498")
    private WebElement deletePostBtn;
    
    @FindBy(id = "yesBtn")
    private WebElement yesBtn;
    
    @FindBy(id = "page1")
    private WebElement page2;
    
//    @FindBy(xpath = "//*[contains(text(), '3')]")
//    private WebElement page3;
    
    public PostsPage() {
    }

    public PostsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedDeleteButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("deletePostBtn498")));
    }
    
    public void ensureIsDisplayedYesButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("yesBtn")));
    }
  
    public void ensureIsNotVisibleDeleteButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("deletePostBtn498")));
    }
    
    public void ensureIsNotVisibleYesButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("yesBtn")));
    }
    
    public String ensureIsDisplayedToast() {
        return (new WebDriverWait(driver, 100)).until(ExpectedConditions.presenceOfElementLocated(By.id("toast-container"))).getText();
    }
    
    public void ensureIsDisplayedPage3() {
        (new WebDriverWait(driver, 100)).until(ExpectedConditions.presenceOfElementLocated(By.id("page2")));
    }

	public WebElement getDeletePostBtn() {
		return deletePostBtn;
	}

	public void setDeletePostBtn(WebElement deletePostBtn) {
		this.deletePostBtn = deletePostBtn;
	}

	public WebElement getYesBtn() {
		return yesBtn;
	}

	public void setYesBtn(WebElement yesBtn) {
		this.yesBtn = yesBtn;
	}

	public WebElement getPage2() {
		return page2;
	}

	public void setPage2(WebElement page2) {
		this.page2 = page2;
	}
}

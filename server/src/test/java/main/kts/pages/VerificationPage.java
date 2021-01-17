package main.kts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class VerificationPage {
	private WebDriver driver;
	
	@FindBy(id="verificationMessage")
	private WebElement verificationMessage;
	
	 public String ensureIsDisplayedToast() {
	        return (new WebDriverWait(driver, 2000)).until(ExpectedConditions.presenceOfElementLocated(By.id("toast-container"))).getText();
	 }

	public WebElement getVerificationMessage() {
		return verificationMessage;
	}

	public void setVerificationMessage(WebElement verificationMessage) {
		this.verificationMessage = verificationMessage;
	} 
	 
	 
}

package main.kts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
	 private WebDriver driver;

	    @FindBy(id = "firstName")
	    private WebElement firstName;

	    @FindBy(id = "lastName")
	    private WebElement lastName;

	    @FindBy(id = "email")
	    private WebElement email;
	    
	    @FindBy(id = "password")
	    private WebElement password;
	    
	    @FindBy(id = "passwordRepeat")
	    private WebElement passwordRepeat;
	    
	    @FindBy(id = "submit")
	    private WebElement registerBtn;   

		public RegisterPage() {
	    }

	    public RegisterPage(WebDriver driver) {
	        this.driver = driver;
	    }

	    public void ensureIsDisplayedEmail() {
	        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("email")));
	    }
	    
	    public void ensureIsNotVisibleEmail() {
	        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("email")));
	    }
	    
	    public String ensureIsDisplayedToast() {
	        return (new WebDriverWait(driver, 15000)).until(ExpectedConditions.presenceOfElementLocated(By.id("toast-container"))).getText();
	    }
	    
	    public WebElement getFirstName() {
			return firstName;
		}

		public void setFirstName(WebElement firstName) {
			this.firstName = firstName;
		}

		public WebElement getLastName() {
			return lastName;
		}

		public void setLastName(WebElement lastName) {
			this.lastName = lastName;
		}

		public WebElement getEmail() {
			return email;
		}

		public void setEmail(WebElement email) {
			this.email = email;
		}

		public WebElement getPassword() {
			return password;
		}

		public void setPassword(WebElement password) {
			this.password = password;
		}

		public WebElement getPasswordRepeat() {
			return passwordRepeat;
		}

		public void setPasswordRepeat(WebElement passwordRepeat) {
			this.passwordRepeat = passwordRepeat;
		}

		public WebElement getRegisterBtn() {
			return registerBtn;
		}

		public void setRegisterBtn(WebElement registerBtn) {
			this.registerBtn = registerBtn;
		}

}

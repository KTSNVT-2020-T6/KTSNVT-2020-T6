package main.kts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
	private WebDriver driver;
	
	@FindBy(id = "changePasswordBtn")
	private WebElement changePasswordBtn;
	
	@FindBy(id = "editBtn")
	private WebElement editBtn;
	
	@FindBy(id = "deactivateBtn")
	private WebElement deactivateBtn;
	
	// modal dialog edit
	@FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "email")
    private WebElement email;
    
   @FindBy(xpath = "//*[@id=\"ngx-mat-file-input-0\"]/input")
    private WebElement uploadImage;
	
    @FindBy(id = "submitBtn")
    private WebElement submitBtn;
	
	// confirm deactivation dialog
    @FindBy(id = "yesBtn")
    private WebElement yesBtn;
    
    @FindBy(id = "noBtn")
    private WebElement noBtn;
    
	// modal dialog change password
    @FindBy(id = "oldPassword")
    private WebElement oldPassword;
    
    @FindBy(id = "newPassword")
    private WebElement newPassword;
    
    @FindBy(id = "submitPasswordBtn")
    private WebElement submitPasswordBtn;
	
	public ProfilePage() {
		super();
	}

	public ProfilePage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public String ensureIsDisplayedToast() {
        return (new WebDriverWait(driver, 100)).until(ExpectedConditions.presenceOfElementLocated(By.id("toast-container"))).getText();
    }
	
	public void ensureIsDisplayedYesBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("yesBtn")));
    }
	
	// for edit type
	public void ensureIsDisplayedEditBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("editBtn")));
    }

	public void ensureIsDisplayedFirstNameInput() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("firstName")));
    }
	public void ensureIsDisplayedSubmitEditBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("submitBtn")));
    }
	
	public void ensureIsNotVisibleSubmitEditBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("submitBtn")));
    }
	
	// for password
	public void ensureIsDisplayedOldPasswordInput() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("oldPassword")));
    }
	
	public void ensureIsNotVisibleOldPasswordInput() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("oldPassword")));
    }
	
	public void ensureIsDisplayedChangePasswordBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("changePasswordBtn")));
    }
	// for deactivation account
	public void ensureIsDisplayedDeactivateBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("deactivateBtn")));
    }
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getChangePasswordBtn() {
		return changePasswordBtn;
	}

	public void setChangePasswordBtn(WebElement changePasswordBtn) {
		this.changePasswordBtn = changePasswordBtn;
	}

	public WebElement getEditBtn() {
		return editBtn;
	}

	public void setEditBtn(WebElement editBtn) {
		this.editBtn = editBtn;
	}

	public WebElement getDeactivateBtn() {
		return deactivateBtn;
	}

	public void setDeactivateBtn(WebElement deactivateBtn) {
		this.deactivateBtn = deactivateBtn;
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

	public WebElement getUploadImage() {
		return uploadImage;
	}

	public void setUploadImage(WebElement uploadImage) {
		this.uploadImage = uploadImage;
	}

	public WebElement getSubmitBtn() {
		return submitBtn;
	}

	public void setSubmitBtn(WebElement submitBtn) {
		this.submitBtn = submitBtn;
	}

	public WebElement getYesBtn() {
		return yesBtn;
	}

	public void setYesBtn(WebElement yesBtn) {
		this.yesBtn = yesBtn;
	}

	public WebElement getNoBtn() {
		return noBtn;
	}

	public void setNoBtn(WebElement noBtn) {
		this.noBtn = noBtn;
	}

	public WebElement getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(WebElement oldPassword) {
		this.oldPassword = oldPassword;
	}

	public WebElement getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(WebElement newPassword) {
		this.newPassword = newPassword;
	}

	public WebElement getSubmitPasswordBtn() {
		return submitPasswordBtn;
	}

	public void setSubmitPasswordBtn(WebElement submitPasswordBtn) {
		this.submitPasswordBtn = submitPasswordBtn;
	}
	
	
    
}

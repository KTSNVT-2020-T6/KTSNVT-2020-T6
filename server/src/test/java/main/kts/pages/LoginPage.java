package main.kts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;

    @FindBy(id = "username")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "submit")
    private WebElement loginBtn;
    

    public LoginPage() {
    }

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedEmail() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("username")));
    }
    public void ensureIsClickableLoginBtn() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("submit")));
    }
    
    public void ensureIsNotVisibleEmail() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("username")));
    }
    
    public String ensureIsDisplayedToast() {
        return (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id("toast-container"))).getText();
    }

    public WebElement getEmail() {
        return email;
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getLoginBtn() {
        return loginBtn;
    }
}

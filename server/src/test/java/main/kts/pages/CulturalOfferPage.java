package main.kts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CulturalOfferPage {
	private WebDriver driver;
	
	@FindBy(id = "subscribe")
    private WebElement subscribeButton;

    @FindBy(id = "unsubscribe")
    private WebElement unsubscribeButton;

    @FindBy(id = "numberOfSubscribed")
	private WebElement numberOfSubscribed;
	
    @FindBy(id = "averageRate")
	private WebElement averageRate;
    
    @FindBy(id = "newComment")
	private WebElement newComment;
	
    @FindBy(id = "uploadButton")
	private WebElement uploadButton;
    
    @FindBy(id = "sendComment")
	private WebElement sendCommentButton;
    
    @FindBy(id = "addPost")
	private WebElement addPostButton;
    
    @FindBy(id = "editCulturalOffer")
   	private WebElement editCulturalOfferButton;
    
    @FindBy(id = "deleteCulturalOffer")
   	private WebElement deleteCulturalOfferButton;
    //start
    @FindBy(id = "star5")
   	private WebElement rateFive;
    
    @FindBy(id = "star4")
   	private WebElement rateFour;
  
    @FindBy(id = "editComment1")
   	private WebElement editCommentButton;
    
    @FindBy(id = "editText")
   	private WebElement editTextCommentButton;
    
    @FindBy(xpath = "//*[@id=\"ngx-mat-file-input-0\"]/input")
   	private WebElement editImageCommentButton;
    
    @FindBy(id = "editCommentButtonSave")
   	private WebElement editCommentButtonSave;
    
    @FindBy(id = "deleteComment1")
   	private WebElement deleteButton;
    
    @FindBy(id = "yesBtn")
   	private WebElement yesDeleteButton;
    
    @FindBy(id = "noBtn")
   	private WebElement noDeleteButton;
    
    @FindBy(id = "page1")
    private WebElement page2;
    
    @FindBy(id = "textPost")
    private WebElement textPost;
    
    @FindBy(id = "datePickerToggleBtn")
    private WebElement datePickerToggleBtn;
    
    @FindBy(xpath = "//*[@id=\"ngx-mat-file-input-0\"]/input")
    private WebElement postUploadImage;
    
    @FindBy(id = "submitAddingPost")
    private WebElement addPostButtonSave;
    
    @FindBy(className="mat-calendar-body-today")
    private WebElement todaysDate;
    
	public CulturalOfferPage() {}
	
	public CulturalOfferPage(WebDriver driver) {
	        this.driver = driver;
	}
	
	public void ensureIsDisplayedSubscribedButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("subscribe")));
    }
	public void ensureIsDisplayedUnsubscribedButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("unsubscribe")));
    }
	public void ensureIsDisplayedFifthStar() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("star5")));
    }
	public void ensureIsDisplayedForthStar() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("star4")));
    }
	public void ensureIsDisplayedAddCommentButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("sendComment")));
    }
    public String ensureIsDisplayedToast() {
        return (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id("toast-container"))).getText();
    }
    public String ensureIsDisplayedEditComment() {
        return (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("editComment1"))).getText();
    }
    public String ensureIsDisplayedDeleteComment() {
        return (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("deleteComment1"))).getText();
    }
    public void ensureIsNotVisibleYesButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("yesBtn")));
    }

    public void ensureIsDisplayedPage3() {
        (new WebDriverWait(driver, 100)).until(ExpectedConditions.presenceOfElementLocated(By.id("page2")));
    }
    public String ensureIsDisplayedDeleteCulturalOffer() {
        return (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("deleteCulturalOffer"))).getText();
    }
    public String ensureIsNotDisplayedDeleteCulturalOffer() {
        return (new WebDriverWait(driver, 1500)).until(ExpectedConditions.elementToBeClickable(By.id("deleteCulturalOffer"))).getText();
    }
    public String ensureIsDisplayedEditCulturalOffer() {
        return (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("editCulturalOffer"))).getText();
    }
    public String ensureIsDisplayedAddPost() {
        return (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("addPost"))).getText();
    }
	public WebElement getAddPostButton() {
		return addPostButton;
	}

	public void setAddPostButton(WebElement addPostButton) {
		this.addPostButton = addPostButton;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getSubscribeButton() {
		return subscribeButton;
	}

	public WebElement getUnsubscribeButton() {
		return unsubscribeButton;
	}

	public WebElement getNumberOfSubscribed() {
		return numberOfSubscribed;
	}

	public WebElement getAverageRate() {
		return averageRate;
	}

	public WebElement getNewComment() {
		return newComment;
	}

	public WebElement getUploadButton() {
		return uploadButton;
	}

	public WebElement getSendCommentButton() {
		return sendCommentButton;
	}

	public WebElement getEditCulturalOfferButton() {
		return editCulturalOfferButton;
	}

	public WebElement getDeleteCulturalOfferButton() {
		return deleteCulturalOfferButton;
	}

	public WebElement getRateFive() {
		return rateFive;
	}

	public void setRateFive(WebElement rateFive) {
		this.rateFive = rateFive;
	}

	public WebElement getRateFour() {
		return rateFour;
	}

	public void setRateFour(WebElement rateFour) {
		this.rateFour = rateFour;
	}

	public WebElement getEditCommentButton() {
		return editCommentButton;
	}

	public void setEditCommentButton(WebElement editCommentButton) {
		this.editCommentButton = editCommentButton;
	}

	public WebElement getEditTextCommentButton() {
		return editTextCommentButton;
	}

	public void setEditTextCommentButton(WebElement editTextCommentButton) {
		this.editTextCommentButton = editTextCommentButton;
	}

	public WebElement getEditImageCommentButton() {
		return editImageCommentButton;
	}

	public void setEditImageCommentButton(WebElement editImageCommentButton) {
		this.editImageCommentButton = editImageCommentButton;
	}

	public WebElement getEditCommentButtonSave() {
		return editCommentButtonSave;
	}

	public void setEditCommentButtonSave(WebElement editCommentButtonSave) {
		this.editCommentButtonSave = editCommentButtonSave;
	}

	public void setEditCulturalOfferButton(WebElement editCulturalOfferButton) {
		this.editCulturalOfferButton = editCulturalOfferButton;
	}

	public WebElement getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(WebElement deleteButton) {
		this.deleteButton = deleteButton;
	}

	public WebElement getYesDeleteButton() {
		return yesDeleteButton;
	}

	public void setYesDeleteButton(WebElement yesDeleteButton) {
		this.yesDeleteButton = yesDeleteButton;
	}

	public WebElement getNoDeleteButton() {
		return noDeleteButton;
	}

	public void setNoDeleteButton(WebElement noDeleteButton) {
		this.noDeleteButton = noDeleteButton;
	}

	public WebElement getPage2() {
		return page2;
	}

	public void setPage2(WebElement page2) {
		this.page2 = page2;
	}

	public WebElement getTextPost() {
		return textPost;
	}

	public void setTextPost(WebElement textPost) {
		this.textPost = textPost;
	}

	public WebElement getDatePickerToggleBtn() {
		return datePickerToggleBtn;
	}

	public void setDatePickerToggleBtn(WebElement datePickerToggleBtn) {
		this.datePickerToggleBtn = datePickerToggleBtn;
	}

	public WebElement getPostUploadImage() {
		return postUploadImage;
	}

	public void setPostUploadImage(WebElement postUploadImage) {
		this.postUploadImage = postUploadImage;
	}

	public WebElement getAddPostButtonSave() {
		return addPostButtonSave;
	}

	public void setAddPostButtonSave(WebElement addPostButtonSave) {
		this.addPostButtonSave = addPostButtonSave;
	}

	public WebElement getTodaysDate() {
		return todaysDate;
	}

	public void setTodaysDate(WebElement todaysDate) {
		this.todaysDate = todaysDate;
	}





}

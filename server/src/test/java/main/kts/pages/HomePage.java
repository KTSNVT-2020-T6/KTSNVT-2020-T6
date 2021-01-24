package main.kts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	private WebDriver driver;
	
	// navbar buttons

    @FindBy(id = "addNewBtn")
    private WebElement addNewBtn;

    @FindBy(id = "addNewCOBtn")
    private WebElement addNewCOBtn;

    @FindBy(id = "addNewAdminBtn")
    private WebElement addNewAdminBtn;
    
    @FindBy(id="signOut")
    private WebElement signOut;
    
    // add new category modal dialog
 // add new cultural offer modal dialog
    
    @FindBy(id="catSelect")
    private WebElement categorySelect;
    
    @FindBy(id="cat1")
    private WebElement categoryOption;
    
    @FindBy(id="typeSelect")
    private WebElement typeSelect;
    
    @FindBy(id="type1")
    private WebElement typeOption;
    
    @FindBy(id="coName")
    private WebElement coName;
    
    @FindBy(id="coDescription")
    private WebElement coDescription;
    
    @FindBy(className="geoapify-autocomplete-input")
    private WebElement geocoder;
    
    @FindBy(id="datePickerToggleBtn")
    private WebElement datePicker;
    
    @FindBy(className="mat-calendar-body-today")
    private WebElement todaysDate;
    
    @FindBy(id="submitCO")
    private WebElement submitCO;
    
    // add new admin modal dialog
    
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
    
    @FindBy(id = "submitAdmin")
    private WebElement registerBtn;
    
    //home page
    @FindBy(id = "goToPage1")
    private WebElement goToPage;
    
    @FindBy(id = "filter")
    private WebElement filter;
    
    @FindBy(id = "page1")
    private WebElement page2;
    
    @FindBy(id = "searchContent")
    private WebElement searchContent;
    
    @FindBy(id = "searchCity")
    private WebElement searchCity;
    
    @FindBy(id = "submitSearch")
    private WebElement submitSearch;
    
    @FindBy(id = "refresh")
    private WebElement refresh;
    
    
    
    public HomePage() {
    }

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedFilterBtn() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("filter")));
    }
    
    public void ensureIsDisplayedGoToBtn() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("goToPage1")));
    }
    
    public void ensureIsDisplayedPage3() {
        (new WebDriverWait(driver, 100)).until(ExpectedConditions.presenceOfElementLocated(By.id("page2")));
    }
    
    public void ensureIsDisplayedPage2() {
        (new WebDriverWait(driver, 100)).until(ExpectedConditions.presenceOfElementLocated(By.id("page1")));
    }
    
    public void ensureIsDisplayedAddNewBtn() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("addNewBtn")));
    }
    
    public void ensureIsDisplayedNameInput() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("coName")));
    }
    
    public void ensureIsNotVisibleName() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("coName")));
    }
    
    public void ensureIsNotVisiblePage3() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("page2")));
    }
    
    public void ensureIsDisplayedEmail() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("email")));
    }
    
    public void ensureIsNotVisibleEmail() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("email")));
    }
    
    public String ensureIsDisplayedToast() {
        return (new WebDriverWait(driver, 2000)).until(ExpectedConditions.presenceOfElementLocated(By.id("toast-container"))).getText();
    }

	public WebElement getAddNewBtn() {
		return addNewBtn;
	}

	public WebElement getSignOut() {
		return signOut;
	}

	public void setSignOut(WebElement signOut) {
		this.signOut = signOut;
	}

	public void setAddNewBtn(WebElement addNewBtn) {
		this.addNewBtn = addNewBtn;
	}

	public WebElement getAddNewCOBtn() {
		return addNewCOBtn;
	}

	public void setAddNewCOBtn(WebElement addNewCOBtn) {
		this.addNewCOBtn = addNewCOBtn;
	}

	public WebElement getAddNewAdminBtn() {
		return addNewAdminBtn;
	}

	public void setAddNewAdminBtn(WebElement addNewAdminBtn) {
		this.addNewAdminBtn = addNewAdminBtn;
	}

	public WebElement getCategorySelect() {
		return categorySelect;
	}

	public void setCategorySelect(WebElement categorySelect) {
		this.categorySelect = categorySelect;
	}

	public WebElement getTypeSelect() {
		return typeSelect;
	}

	public void setTypeSelect(WebElement typeSelect) {
		this.typeSelect = typeSelect;
	}

	public WebElement getCoName() {
		return coName;
	}

	public void setCoName(WebElement coName) {
		this.coName = coName;
	}

	public WebElement getCoDescription() {
		return coDescription;
	}

	public void setCoDescription(WebElement coDescription) {
		this.coDescription = coDescription;
	}

	public WebElement getGeocoder() {
		return geocoder;
	}

	public void setGeocoder(WebElement geocoder) {
		this.geocoder = geocoder;
	}

	public WebElement getDatePicker() {
		return datePicker;
	}

	public void setDatePicker(WebElement datePicker) {
		this.datePicker = datePicker;
	}

	public WebElement getSubmitCO() {
		return submitCO;
	}

	public WebElement getCategoryOption() {
		return categoryOption;
	}

	public void setCategoryOption(WebElement categoryOption) {
		this.categoryOption = categoryOption;
	}

	public WebElement getTypeOption() {
		return typeOption;
	}

	public WebElement getTodaysDate() {
		return todaysDate;
	}

	public void setTodaysDate(WebElement todaysDate) {
		this.todaysDate = todaysDate;
	}

	public void setTypeOption(WebElement typeOption) {
		this.typeOption = typeOption;
	}

	public void setSubmitCO(WebElement submitCO) {
		this.submitCO = submitCO;
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

	public WebElement getGoToPage() {
		return goToPage;
	}

	public void setGoToPage(WebElement goToPage) {
		this.goToPage = goToPage;
	}

	public WebElement getFilter() {
		return filter;
	}

	public void setFilter(WebElement filter) {
		this.filter = filter;
	}

	public WebElement getPage2() {
		return page2;
	}

	public void setPage2(WebElement page2) {
		this.page2 = page2;
	}

	public WebElement getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(WebElement searchContent) {
		this.searchContent = searchContent;
	}

	public WebElement getSearchCity() {
		return searchCity;
	}

	public void setSearchCity(WebElement searchCity) {
		this.searchCity = searchCity;
	}

	public WebElement getSubmitSearch() {
		return submitSearch;
	}

	public void setSubmitSearch(WebElement submitSearch) {
		this.submitSearch = submitSearch;
	}

	public WebElement getRefresh() {
		return refresh;
	}

	public void setRefresh(WebElement refresh) {
		this.refresh = refresh;
	}


    
	
	
}

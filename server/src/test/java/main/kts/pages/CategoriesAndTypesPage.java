package main.kts.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoriesAndTypesPage {
	private WebDriver driver;
	
	@FindBy(id = "addCategory")
	private WebElement addCategoryBtn;
	
	@FindBy(id = "addType")
	private WebElement addTypeBtn;
	
	@FindBy(id = "editCategory2")
	private WebElement editCategory2btn; // edit manifestation
	
	@FindBy(id = "deleteCategory4")
	private WebElement deleteCategory4btn; //delete me cat
	
	@FindBy(id = "editType3")
	private WebElement editType3btn; // edit theater
	
	@FindBy(id = "deleteType14")
	private WebElement deleteType14btn; //delete me type
	
	// delete buttons for error tests
	@FindBy(id = "deleteCategory1")
	private WebElement deleteCategory1btn;
	
	@FindBy(id = "deleteType1")
	private WebElement deleteType1btn;
	
	// new cells, exist after addition
	@FindBy(id = "newType")
	private WebElement newType;
	
	@FindBy(id = "newCat")
	private WebElement newCat;
	
	// old cells which do not exist after edit
	@FindBy(id = "manifestation")
	private WebElement menifestation;
	
	@FindBy(id = "theater")
	private WebElement theater; 
	
	// elements from modal dialog add category
	@FindBy(id = "catName")
	private WebElement catName;
	
	@FindBy(id = "catDescription")
	private WebElement catDescription;
	
	@FindBy(id = "catSubmit")
    private WebElement catSubmitBtn;
	
	// elements from modal dialog add type
	@FindBy(id = "typeName")
	private WebElement typeName;
		
	@FindBy(id = "typeDescription")
	private WebElement typeDescription;
	
	@FindBy(id="catSelect")
    private WebElement categorySelect;
    
    @FindBy(id="cat1")
    private WebElement categoryOption;
    
    @FindBy(id = "typeSubmit")
    private WebElement typeSubmitBtn;
    
    // elements from modal dialog edit type
	
    @FindBy(id = "typeEditName")
	private WebElement typeEditName;
		
	@FindBy(id = "typeEditDescription")
	private WebElement typeEditDescription;
	
	@FindBy(id="catEdtiSelect")
    private WebElement categoryEditSelect;
    
    @FindBy(id="cat1")
    private WebElement categoryEditOption;
    
    @FindBy(id = "typeEditSubmit")
    private WebElement typeEditSubmitBtn;
    
    // elements from modal dialog edit category
    
    @FindBy(id = "catEditName")
	private WebElement catEditName;
	
	@FindBy(id = "catEditDescription")
	private WebElement catEditDescription;
	
	@FindBy(id = "catEditSubmit")
    private WebElement catEditSubmitBtn;
	
	// confirm dialog
    @FindBy(id = "yesBtn")
    private WebElement yesBtn;
    
    @FindBy(id = "noBtn")
    private WebElement noBtn;
    
    public CategoriesAndTypesPage() {
    	
    }
    public CategoriesAndTypesPage(WebDriver driver) {
    	this.driver = driver;
    }
    
    public void ensureIsDisplayedYesBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("yesBtn")));
    }
    
   
    public void ensureIsDisplayedAddCategoryBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("addCategory")));
    }
    
    public void ensureIsDisplayedAddTypeBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("addType")));
    }
    
    public void ensureIsDisplayedCatNameInput() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("catName")));
    }
    
    public void ensureIsNotVisibleCatNameInput() {
    	(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("catName")));
    }
    
    public void ensureIsDisplayedTypeNameInput() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("typeName")));
    }
    public void ensureIsNotVisibleTypeNameInput() {
    	(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("typeName")));
    }
    
    public String ensureIsDisplayedToast() {
        return (new WebDriverWait(driver, 100)).until(ExpectedConditions.presenceOfElementLocated(By.id("toast-container"))).getText();
    }
    
    public void ensureIsDisplayedDeleteCategoryBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("deleteCategory4")));
    }
    public void ensureIsDisplayedDeleteTypeBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("deleteType14")));
    }
    
    public void ensureIsDisplayedDeleteCategory1Btn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("deleteCategory1")));
    }
    public void ensureIsDisplayedDeleteType1Btn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("deleteType1")));
    }
    
    public void ensureIsDisplayedEditCategoryBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("editCategory2")));
    }
    public void ensureIsDisplayedEditTypeBtn() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("editType3")));
    }
    public void ensureIsDisplayedCatEditNameInput() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("catEditName")));
    }
    public void ensureIsNotVisibleCatEditNameInput() {
    	(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("catEditName")));
    }
    public void ensureIsNotVisibleOldCatNameInput() {
    	(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("manifestation")));
    }
    public void ensureIsDisplayedTypeEditNameInput() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("typeEditName")));
    }
    public void ensureIsNotVisibleTypeEditNameInput() {
    	(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("typeEditName")));
    }
    public void ensureIsNotVisibleOldTypeNameInput() {
    	(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("theater")));
    }

    
    /*
     * public void ensureIsDisplayed() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void ensureIsDisplayed() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void ensureIsDisplayed() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void ensureIsDisplayed() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void ensureIsDisplayed() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void ensureIsDisplayed() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(locator));
    }
     * 
     */
    
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	public WebElement getAddCategoryBtn() {
		return addCategoryBtn;
	}
	public void setAddCategoryBtn(WebElement addCategoryBtn) {
		this.addCategoryBtn = addCategoryBtn;
	}
	public WebElement getAddTypeBtn() {
		return addTypeBtn;
	}
	public void setAddTypeBtn(WebElement addTypeBtn) {
		this.addTypeBtn = addTypeBtn;
	}
	public WebElement getEditCategory2btn() {
		return editCategory2btn;
	}
	public void setEditCategory2btn(WebElement editCategory2btn) {
		this.editCategory2btn = editCategory2btn;
	}
	public WebElement getDeleteCategory4btn() {
		return deleteCategory4btn;
	}
	public void setDeleteCategory4btn(WebElement deleteCategory4btn) {
		this.deleteCategory4btn = deleteCategory4btn;
	}
	public WebElement getEditType3btn() {
		return editType3btn;
	}
	public void setEditType3btn(WebElement editType3btn) {
		this.editType3btn = editType3btn;
	}
	public WebElement getDeleteType14btn() {
		return deleteType14btn;
	}
	public void setDeleteType14btn(WebElement deleteType14btn) {
		this.deleteType14btn = deleteType14btn;
	}
	public WebElement getNewType() {
		return newType;
	}
	public void setNewType(WebElement newType) {
		this.newType = newType;
	}
	public WebElement getNewCat() {
		return newCat;
	}
	public void setNewCat(WebElement newCat) {
		this.newCat = newCat;
	}
	public WebElement getMenifestation() {
		return menifestation;
	}
	public void setMenifestation(WebElement menifestation) {
		this.menifestation = menifestation;
	}
	public WebElement getTheater() {
		return theater;
	}
	public void setTheater(WebElement theater) {
		this.theater = theater;
	}
	public WebElement getCatName() {
		return catName;
	}
	public void setCatName(WebElement catName) {
		this.catName = catName;
	}
	public WebElement getCatDescription() {
		return catDescription;
	}
	public void setCatDescription(WebElement catDescription) {
		this.catDescription = catDescription;
	}
	public WebElement getCatSubmitBtn() {
		return catSubmitBtn;
	}
	public void setCatSubmitBtn(WebElement catSubmitBtn) {
		this.catSubmitBtn = catSubmitBtn;
	}
	public WebElement getTypeName() {
		return typeName;
	}
	public void setTypeName(WebElement typeName) {
		this.typeName = typeName;
	}
	public WebElement getTypeDescription() {
		return typeDescription;
	}
	public void setTypeDescription(WebElement typeDescription) {
		this.typeDescription = typeDescription;
	}
	public WebElement getCategorySelect() {
		return categorySelect;
	}
	public void setCategorySelect(WebElement categorySelect) {
		this.categorySelect = categorySelect;
	}
	public WebElement getCategoryOption() {
		return categoryOption;
	}
	public void setCategoryOption(WebElement categoryOption) {
		this.categoryOption = categoryOption;
	}
	public WebElement getTypeSubmitBtn() {
		return typeSubmitBtn;
	}
	public void setTypeSubmitBtn(WebElement typeSubmitBtn) {
		this.typeSubmitBtn = typeSubmitBtn;
	}
	public WebElement getTypeEditName() {
		return typeEditName;
	}
	public void setTypeEditName(WebElement typeEditName) {
		this.typeEditName = typeEditName;
	}
	public WebElement getTypeEditDescription() {
		return typeEditDescription;
	}
	public void setTypeEditDescription(WebElement typeEditDescription) {
		this.typeEditDescription = typeEditDescription;
	}
	public WebElement getCategoryEditSelect() {
		return categoryEditSelect;
	}
	public void setCategoryEditSelect(WebElement categoryEditSelect) {
		this.categoryEditSelect = categoryEditSelect;
	}
	public WebElement getCategoryEditOption() {
		return categoryEditOption;
	}
	public void setCategoryEditOption(WebElement categoryEditOption) {
		this.categoryEditOption = categoryEditOption;
	}
	public WebElement getTypeEditSubmitBtn() {
		return typeEditSubmitBtn;
	}
	public void setTypeEditSubmitBtn(WebElement typeEditSubmitBtn) {
		this.typeEditSubmitBtn = typeEditSubmitBtn;
	}
	public WebElement getCatEditName() {
		return catEditName;
	}
	public void setCatEditName(WebElement catEditName) {
		this.catEditName = catEditName;
	}
	public WebElement getCatEditDescription() {
		return catEditDescription;
	}
	public void setCatEditDescription(WebElement catEditDescription) {
		this.catEditDescription = catEditDescription;
	}
	public WebElement getCatEditSubmitBtn() {
		return catEditSubmitBtn;
	}
	public void setCatEditSubmitBtn(WebElement catEditSubmitBtn) {
		this.catEditSubmitBtn = catEditSubmitBtn;
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
	public WebElement getDeleteCategory1btn() {
		return deleteCategory1btn;
	}
	public void setDeleteCategory1btn(WebElement deleteCategory1btn) {
		this.deleteCategory1btn = deleteCategory1btn;
	}
	public WebElement getDeleteType1btn() {
		return deleteType1btn;
	}
	public void setDeleteType1btn(WebElement deleteType1btn) {
		this.deleteType1btn = deleteType1btn;
	}
	
    
}

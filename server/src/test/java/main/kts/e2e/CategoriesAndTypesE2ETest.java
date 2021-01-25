package main.kts.e2e;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import main.kts.pages.CategoriesAndTypesPage;
import main.kts.pages.LoginPage;
 
public class CategoriesAndTypesE2ETest {
	private WebDriver driver;
	private CategoriesAndTypesPage categoriesAndTypesPage;
	private LoginPage loginPage;
	
	 @Before
	 public void setUp() throws InterruptedException {

	     System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
	     driver = new ChromeDriver();
	     driver.manage().window().maximize();
	        
	     driver.get ("https://localhost:4200/login");
	     loginPage = PageFactory.initElements(driver, LoginPage.class);
	     loginPage.getEmail().sendKeys("admin@gmail.com");;
	     loginPage.getPassword().sendKeys("asdf");;
	     loginPage.getLoginBtn().click();
	     justWait(5000);
	        
	     categoriesAndTypesPage = PageFactory.initElements(driver, CategoriesAndTypesPage.class);
	 }
	 @After
	 public void tearDown() {
	     driver.quit();
	 }
	 private void justWait(int milliseconds) throws InterruptedException {
	     synchronized (driver)
	     {
	         driver.wait(milliseconds);
	     }
	 }
	 
	 @Test
	 public void AddCategoryTestSuccess() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedAddCategoryBtn();
		 categoriesAndTypesPage.getAddCategoryBtn().click();
		 categoriesAndTypesPage.ensureIsDisplayedCatNameInput();
		 
		 categoriesAndTypesPage.getCatName().sendKeys("newCat");
		 categoriesAndTypesPage.getCatDescription().sendKeys("newCatDescritpion");
		 justWait(500);
		 categoriesAndTypesPage.getCatSubmitBtn().click();
		 justWait(1000);
		 
		 categoriesAndTypesPage.ensureIsNotVisibleCatNameInput();
		 justWait(1000);
	     assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());
	 }
	 
	 @Test
	 public void AddCategoryTestError() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedAddCategoryBtn();
		 categoriesAndTypesPage.getAddCategoryBtn().click();
		 categoriesAndTypesPage.ensureIsDisplayedCatNameInput();
		 
		 categoriesAndTypesPage.getCatName().sendKeys("institution");
		 categoriesAndTypesPage.getCatDescription().sendKeys("newCatDescritpion");
		 justWait(500);
		 categoriesAndTypesPage.getCatSubmitBtn().click();
		 justWait(1000);
		 
		 String toast = categoriesAndTypesPage.ensureIsDisplayedToast();
		 assertEquals("Name already exists!", toast);
	     assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());

	 }
	 
	 @Test
	 public void AddTypeTestSuccess() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedAddTypeBtn();
		 categoriesAndTypesPage.getAddTypeBtn().click();
		 categoriesAndTypesPage.ensureIsDisplayedTypeNameInput();
		 
		 categoriesAndTypesPage.getCategorySelect().click();
		 categoriesAndTypesPage.getCategoryOption().click();
		 justWait(2000);
		 categoriesAndTypesPage.getTypeName().sendKeys("newType");
		 categoriesAndTypesPage.getTypeDescription().sendKeys("newTypeDescritpion");
		 justWait(500);
		 categoriesAndTypesPage.getTypeSubmitBtn().click();
		 justWait(1000);
		 
		 categoriesAndTypesPage.ensureIsNotVisibleTypeNameInput();
		 justWait(1000);
	     assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());
	 }
	 @Test
	 public void AddTypeTestError() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedAddTypeBtn();
		 categoriesAndTypesPage.getAddTypeBtn().click();
		 categoriesAndTypesPage.ensureIsDisplayedTypeNameInput();
		 
		 categoriesAndTypesPage.getCategorySelect().click();
		 categoriesAndTypesPage.getCategoryOption().click();
		 justWait(2000);
		 categoriesAndTypesPage.getTypeName().sendKeys("monument");
		 categoriesAndTypesPage.getTypeDescription().sendKeys("newTypeDescritpion");
		 justWait(500);
		 categoriesAndTypesPage.getTypeSubmitBtn().click();
		
		 justWait(1000);
		 String toast = categoriesAndTypesPage.ensureIsDisplayedToast();
		 assertEquals("Name already exists!", toast);
	     assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());
	 }
	
	 @Test
	 public void EditTypeTestSucces() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedEditTypeBtn();
		 categoriesAndTypesPage.getEditType3btn().click();
		 categoriesAndTypesPage.ensureIsDisplayedTypeEditNameInput();
		 
		 categoriesAndTypesPage.getTypeEditName().clear();
		 categoriesAndTypesPage.getTypeEditName().sendKeys("changed");
		 justWait(1000);
		 categoriesAndTypesPage.getTypeEditSubmitBtn().click();
		 categoriesAndTypesPage.ensureIsNotVisibleTypeEditNameInput();
		 assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());
		 justWait(2000);
		 categoriesAndTypesPage.ensureIsNotVisibleOldTypeNameInput();
	 }
	 
	 @Test
	 public void EditTypeTestError() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 categoriesAndTypesPage.ensureIsDisplayedEditTypeBtn();
		 categoriesAndTypesPage.getEditType3btn().click();
		 categoriesAndTypesPage.ensureIsDisplayedTypeEditNameInput();
		 
		 categoriesAndTypesPage.getTypeEditName().clear();
		 categoriesAndTypesPage.getTypeEditName().sendKeys("cinema");
		 justWait(2000);
		 categoriesAndTypesPage.getTypeEditSubmitBtn().click();
		 categoriesAndTypesPage.ensureIsNotVisibleTypeEditNameInput();
		 assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());
		 
		 categoriesAndTypesPage.ensureIsNotVisibleOldTypeNameInput();
		 
	 }
	
	 
	 @Test
	 public void EditCategoryTestSucces() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedEditCategoryBtn();
		 categoriesAndTypesPage.getEditCategory2btn().click();
		 categoriesAndTypesPage.ensureIsDisplayedCatEditNameInput();
		 
		 categoriesAndTypesPage.getCatEditName().clear();
		 categoriesAndTypesPage.getCatEditName().sendKeys("changed");
		 justWait(500);
		 categoriesAndTypesPage.getCatEditSubmitBtn().click();
		 
		 categoriesAndTypesPage.ensureIsNotVisibleCatNameInput();
		 assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());
		 justWait(2000);
		 categoriesAndTypesPage.ensureIsNotVisibleOldCatNameInput();
		 
	 }
	 
	 @Test
	 public void EditCategoryTestError() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedEditCategoryBtn();
		 categoriesAndTypesPage.getEditCategory2btn().click();
		 categoriesAndTypesPage.ensureIsDisplayedCatEditNameInput();
		 
		 categoriesAndTypesPage.getCatEditName().clear();
		 categoriesAndTypesPage.getCatEditName().sendKeys("institution");
		 justWait(500);
		 categoriesAndTypesPage.getCatEditSubmitBtn().click();
		 justWait(1000);
		 
		 String toast = categoriesAndTypesPage.ensureIsDisplayedToast();
		 assertEquals("Cannot edit category!", toast);
	     assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());
	 }
	
	 @Test
	 public void DeleteCategoryTestSucces() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedDeleteCategoryBtn();
		 categoriesAndTypesPage.getDeleteCategory4btn().click();
		 categoriesAndTypesPage.ensureIsDisplayedYesBtn();
		 justWait(1000);
		 categoriesAndTypesPage.getYesBtn().click();
		 
		 justWait(2000);
		 assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());

		 
	 }
	 
	 @Test
	 public void DeleteTypeTestSucces() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedDeleteTypeBtn();
		 categoriesAndTypesPage.getDeleteType14btn().click();
		 justWait(1000);
		 categoriesAndTypesPage.ensureIsDisplayedYesBtn();
		 categoriesAndTypesPage.getYesBtn().click();
		 justWait(2000);
		 assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());
		 
	 }
	 
	 @Test
	 public void DeleteCategoryTestError() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedDeleteCategory1Btn();
		 categoriesAndTypesPage.getDeleteCategory1btn().click();
		 categoriesAndTypesPage.ensureIsDisplayedYesBtn();
		 justWait(1000);
		 categoriesAndTypesPage.getYesBtn().click();
		 
		 justWait(2000);
		 
		 String toast = categoriesAndTypesPage.ensureIsDisplayedToast();
		 assertEquals("Cannot delete this category!", toast);
	     assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());

		 
	 }
	 
	 @Test
	 public void DeleteTypeTestError() throws InterruptedException {
		 driver.get("https://localhost:4200/categoriesAndTypes");
		 justWait(3000);
		 
		 categoriesAndTypesPage.ensureIsDisplayedDeleteType1Btn();
		 categoriesAndTypesPage.getDeleteType1btn().click();
		 justWait(1000);
		 categoriesAndTypesPage.ensureIsDisplayedYesBtn();
		 categoriesAndTypesPage.getYesBtn().click();
		 justWait(2000);
		 
		 String toast = categoriesAndTypesPage.ensureIsDisplayedToast();
		 assertEquals("Cannot delete this type!", toast);
	     assertEquals("https://localhost:4200/categoriesAndTypes", driver.getCurrentUrl());
		 
	 }
	 
}

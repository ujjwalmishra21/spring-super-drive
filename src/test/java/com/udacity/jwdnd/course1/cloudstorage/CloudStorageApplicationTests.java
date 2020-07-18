package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.InputMismatchException;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private Login loginPage;
	private Signup signupPage;
	private Home homePage;
	private HomeNotes homeNotes;
	private HomeCredentials homeCredentials;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}


	@Test
	public void signupPageTest(){
		driver.get("http://localhost:" + port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		signupPage = new Signup(driver);
		signupPage.addSignUpCredentials("Ujjwal", "Mishra", "um334", "um334");

		signupPage.submitSignupForm();
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signupPage.successMessage());

		signupPageAlreadyTest();
	}
	//Username already exists
	public void signupPageAlreadyTest(){
		driver.get("http://localhost:" + port + "/signup");
		signupPage = new Signup(driver);
		signupPage.addSignUpCredentials("Ujjwal", "Mishra", "um334", "um334");

		signupPage.submitSignupForm();
		Assertions.assertEquals("The username already exists", signupPage.errorMessage());
		loginPageTest();
	}
	//Login success
	public void loginPageTest(){
		driver.get("http://localhost:" + port + "/login");
		loginPage = new Login(driver);
		loginPage.addLoginCredentials("um334","um334");

		loginPage.submitLoginForm();

		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());
		notesTest();

	}
	public void notesTest() throws TimeoutException {

		try {
			Thread.sleep(2000);

			homePage = new Home(driver);
			homePage.navNoteSectionClick();

			WebDriverWait wait1 = new WebDriverWait(driver, 10);
			WebElement notesAdd = wait1.until(webDriver -> webDriver.findElement(By.id("btn-note-add")));

			Thread.sleep(2000);

			notesAdd.click();

			Thread.sleep(2000);

			homeNotes = new HomeNotes(driver);

			//Notes Add
			homeNotes.addNotesData("Hello","I am xyz");

			Thread.sleep(2000);

			homeNotes.submitNote();
			Thread.sleep(2000);

			homePage = new Home(driver);
			Assertions.assertEquals("Note added successfully",homePage.getSuccessMessage());

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement notesNavTab = wait.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));
			notesNavTab.click();

			homeNotes = new HomeNotes(driver);
			Thread.sleep(2000);

			Assertions.assertEquals("Hello", homeNotes.getRecentNoteTitle());
			Assertions.assertEquals("I am xyz", homeNotes.getRecentNoteDescription());

			WebDriverWait wait2 = new WebDriverWait(driver, 10);
			List<WebElement> notesEdit = wait2.until(webDriver -> webDriver.findElements(By.className("all-notes-edit")));

			notesEdit.get(notesEdit.size()-1).click();

			Thread.sleep(2000);

			//Note Editing
			homeNotes = new HomeNotes(driver);

			homeNotes.addNotesData("Hey there","How are you?");

			Thread.sleep(2000);

			homeNotes.submitNote();
			Thread.sleep(2000);

			homePage = new Home(driver);
			Assertions.assertEquals("Note updated successfully",homePage.getSuccessMessage());

			WebDriverWait wait3 = new WebDriverWait(driver, 10);
			notesNavTab = wait3.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));
			notesNavTab.click();

			homeNotes = new HomeNotes(driver);
			Thread.sleep(2000);

			Assertions.assertEquals("Hey there", homeNotes.getRecentNoteTitle());
			Assertions.assertEquals("How are you?", homeNotes.getRecentNoteDescription());

			//Note Deletion
			WebDriverWait wait4 = new WebDriverWait(driver, 10);
			List<WebElement> notesDelete = wait4.until(webDriver -> webDriver.findElements(By.className("all-notes-delete")));
			Integer prevCount = notesDelete.size();

			notesDelete.get(notesDelete.size()-1).click();
			Thread.sleep(2000);

			homePage = new Home(driver);
			Assertions.assertEquals("Note deleted",homePage.getDeletedMessage());

			notesDelete = wait4.until(webDriver -> webDriver.findElements(By.className("all-notes-delete")));

			Assertions.assertEquals(notesDelete.size(), prevCount - 1);

			Thread.sleep(2000);


		}catch (Exception e){
			System.out.println(e.getStackTrace());
		}

		credentialsTest();
	}

	public void credentialsTest(){
		try{
			Thread.sleep(2000);

			homePage = new Home(driver);
			homePage.navCredentialSectionClick();

			WebDriverWait wait1 = new WebDriverWait(driver, 10);
			WebElement credentialAdd = wait1.until(webDriver -> webDriver.findElement(By.id("btn-credential-add")));

			Thread.sleep(2000);
			//Credential Add
			credentialAdd.click();

			Thread.sleep(2000);
			homeCredentials = new HomeCredentials(driver);
			homeCredentials.addCredentialData("http://localhost:8080","um21", "um334");

			Thread.sleep(2000);

			homeCredentials.submitCredential();

			Thread.sleep(2000);

			homePage = new Home(driver);
			Assertions.assertEquals("Credential added successfully",homePage.getSuccessMessage());

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement credentenialsNavTab = wait.until(webDriver -> webDriver.findElement(By.id("nav-credentials-tab")));
			credentenialsNavTab.click();

			homeCredentials = new HomeCredentials(driver);
			Thread.sleep(2000);

			Assertions.assertEquals("http://localhost:8080", homeCredentials.getRecentCredentialURL());
			Assertions.assertEquals("um21", homeCredentials.getRecentCredentialUsername());

			//Credential Edit
			homeCredentials.credentialEditClick();
			Thread.sleep(2000);
			homeCredentials = new HomeCredentials(driver);
			Thread.sleep(2000);

			Assertions.assertEquals("http://localhost:8080", homeCredentials.getModalURL());
			Assertions.assertEquals("um21", homeCredentials.getModalUsername());
			Assertions.assertEquals("um334", homeCredentials.getModalPassword());
			Assertions.assertThrows(Error.class,()->{
				String pass = "um21aeaea";
				if(!pass.equals(homeCredentials.getModalPassword()))
					throw new Error("Passwords mismatch");

			});

			homeCredentials.addCredentialData("http://localhost:8080","um21","um1999");

			Thread.sleep(2000);
			homeCredentials.submitCredential();

			Thread.sleep(2000);

			homePage = new Home(driver);
			Assertions.assertEquals("Credential updated successfully",homePage.getSuccessMessage());

			WebDriverWait wait2 = new WebDriverWait(driver, 10);
			credentenialsNavTab = wait2.until(webDriver -> webDriver.findElement(By.id("nav-credentials-tab")));
			credentenialsNavTab.click();

			Thread.sleep(2000);

			//Credential Deletion
			WebDriverWait wait3 = new WebDriverWait(driver, 20);
			List<WebElement> credentialDelete = wait3.until(webDriver -> webDriver.findElements(By.className("all-credential-delete")));
			Integer prevCount = credentialDelete.size();

			credentialDelete.get(credentialDelete.size()-1).click();
			Thread.sleep(2000);

			homePage = new Home(driver);
			Assertions.assertEquals("Credential deleted",homePage.getDeletedMessage());

			credentialDelete = wait3.until(webDriver -> webDriver.findElements(By.className("all-credential-delete")));

			Assertions.assertEquals(credentialDelete.size(), prevCount - 1);

			Thread.sleep(2000);

		}catch (Exception e){
			System.out.println(e.getStackTrace());
		}
		logoutTest();
	}

	public void logoutTest(){
		try {
			Thread.sleep(3000);
			homePage = new Home(driver);
			homePage.logoutClick();

			loginPage = new Login(driver);
			Thread.sleep(2000);
			Assertions.assertEquals("You have been logged out", loginPage.getLogoutMessage());

		}catch(Exception e){
			System.out.println(e.getStackTrace());
		}
	}

	@Test
	public void loginPageInvalidTest() {
		driver.get("http://localhost:" + this.port + "/login");

		Assertions.assertEquals("Login", driver.getTitle());

		loginPage = new Login(driver);
		loginPage.addLoginCredentials("um23","um2ad3");

		loginPage.submitLoginForm();

		Assertions.assertEquals("Invalid username or password", loginPage.getErrorMessage());
	}
}

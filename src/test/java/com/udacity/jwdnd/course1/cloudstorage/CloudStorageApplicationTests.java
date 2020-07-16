package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private Login loginPage;
	private Signup signupPage;
	private Home homePage;
	private HomeNotes homeNotes;

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
//			driver.quit();
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

	public void signupPageAlreadyTest(){
		driver.get("http://localhost:" + port + "/signup");
		signupPage = new Signup(driver);
		signupPage.addSignUpCredentials("Ujjwal", "Mishra", "um334", "um334");

		signupPage.submitSignupForm();
		Assertions.assertEquals("The username already exists", signupPage.errorMessage());
		loginPageTest();
	}

	public void loginPageTest(){
		driver.get("http://localhost:" + port + "/login");
		loginPage = new Login(driver);
		loginPage.addLoginCredentials("um334","um334");

		loginPage.submitLoginForm();

		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());
		notesTest();

	}
	void notesTest() throws TimeoutException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement notesNavTab = wait.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));
		notesNavTab.click();
//		homePage = new Home(driver);
//		homePage.navNoteSectionClick();
//
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		WebElement notesAdd = wait1.until(webDriver -> webDriver.findElement(By.id("btn-note-add")));
		notesAdd.click();

//		WebDriverWait wait2 = new WebDriverWait(driver, 30);
//		WebElement noteTitle = wait2.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
//		WebElement noteDescription = wait2.until(ExpectedConditions.elementToBeClickable(By.id("note-description")));
//		WebElement noteSubmit = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit")));
//		noteTitle.sendKeys("Hello");
//		noteDescription.sendKeys("I am abc");
//		noteSubmit.click();


//		wait = new WebDriverWait(driver, 10);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("notes-title")));
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("notes-description")));
//		Assertions.assertEquals("Hello", homePage.getRecentNoteTitle());
//		Assertions.assertEquals("My name is abc", homePage.getRecentNoteDescription());



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

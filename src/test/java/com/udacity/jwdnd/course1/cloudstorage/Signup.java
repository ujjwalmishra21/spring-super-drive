package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Signup {
    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    public Signup(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    public void addSignUpCredentials(String firstname, String lastname, String username, String password){
        firstName.sendKeys(firstname);
        lastName.sendKeys(lastname);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);

    }
    public String getFirstname(){
        return firstName.getAttribute("value");
    }
    public String getLastname(){
        return lastName.getAttribute("value");
    }
    public String getUsername(){
        return usernameInput.getAttribute("value");
    }
    public String getPassword(){
        return passwordInput.getAttribute("value");
    }
    public void submitSignupForm(){
        submitButton.click();
    }
    public String successMessage(){
        return successMessage.getText();
    }
    public String errorMessage(){
        return errorMessage.getText();
    }
    public void loginLinkClick(){
        loginLink.click();
    }
}

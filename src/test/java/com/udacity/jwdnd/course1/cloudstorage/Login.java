package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(id = "logout-msg")
    private WebElement logoutMessage;

    public Login(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    public void addLoginCredentials(String username, String password){
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }
    public String getUsername(){
        return usernameInput.getAttribute("value");
    }
    public String getPassword(){
        return passwordInput.getAttribute("value");
    }
    public void  submitLoginForm(){
        submitButton.click();
    }
    public String getErrorMessage(){
        return errorMessage.getText();
    }
    public String getLogoutMessage(){ return logoutMessage.getText();}
}

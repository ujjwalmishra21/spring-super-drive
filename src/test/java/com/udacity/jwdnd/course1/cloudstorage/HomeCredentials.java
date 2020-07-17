package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomeCredentials {
    @FindBy(id = "btn-credential-add")
    private WebElement credentialAdd;

    @FindBy(id = "credential-url")
    private WebElement credentialURL;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(className = "all-credential-url")
    private List<WebElement>  credentialURLClass;

    @FindBy(className = "all-credential-username")
    private List<WebElement> credentialUsernameClass;

    @FindBy(className = "all-credential-password")
    private  List<WebElement> credentialPasswordClass;

    @FindBy(className = "all-credential-edit")
    private List<WebElement> credentialEditClass;

    @FindBy(className = "all-credential-delete")
    private List<WebElement> credentialDeleteClass;

    public HomeCredentials(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void addCredentialData(String url, String username, String password){
        this.credentialURL.clear();
        this.credentialUsername.clear();
        this.credentialPassword.clear();
        this.credentialURL.sendKeys(url);
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.sendKeys(password);
    }

    public void submitCredential(){
        this.credentialUsername.submit();
    }

    public String getModalURL(){
        return this.credentialURL.getAttribute("value");
    }
    public String getModalUsername(){
        return this.credentialUsername.getAttribute("value");
    }
    public String getModalPassword(){
         return this.credentialPassword.getAttribute("value");
    }
    public String getRecentCredentialURL(){
        return this.credentialURLClass.get(this.credentialURLClass.size()-1).getText();
    }

    public String getRecentCredentialUsername(){
        return this.credentialUsernameClass.get(this.credentialUsernameClass.size()-1).getText();
    }

    public String getRecentCredentialPassword(){
        return this.credentialPasswordClass.get(this.credentialPasswordClass.size()-1).getText();
    }

    public void credentialEditClick(){
        credentialEditClass.get(0).click();
    }

}

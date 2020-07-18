package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Home {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesNav;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsNav;

    @FindBy(id = "logout-btn")
    private WebElement logoutBtn;

    @FindBy(id = "home-success-msg")
    private WebElement successMessage;

    @FindBy(id = "home-deleted-msg")
    private WebElement deletedMessage;

    @FindBy(id = "home-error-msg")
    private WebElement errorMessage;

    public Home(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void navNoteSectionClick(){
        this.notesNav.click();
    }

    public void navCredentialSectionClick(){
        this.credentialsNav.click();
    }

    public void logoutClick(){
        this.logoutBtn.click();
    }

    public String getSuccessMessage(){
         return this.successMessage.getText();
    }

    public String getDeletedMessage(){
       return this.deletedMessage.getText();
    }

    public String getErrorMessage(){
        return this.errorMessage.getText();
    }


}

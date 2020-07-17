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

    public Home(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void navNoteSectionClick(){
        this.notesNav.click();
    }

    public void navCredentialSectionClick(){
        this.credentialsNav.click();
    }


}

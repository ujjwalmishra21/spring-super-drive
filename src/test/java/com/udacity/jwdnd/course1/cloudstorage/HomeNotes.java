package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomeNotes {
    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-submit")
    private WebElement noteSubmit;

    @FindBy(className = "notes-title")
    private List<WebElement> notesTitleClass;

    @FindBy(className = "notes-description")
    private List<WebElement> notesDescriptionClass;

    @FindBy(id = "note-add")
    private WebElement noteAdd;

    public HomeNotes(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void addNotesData(String noteTitle, String noteDescription){
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
    }

    public void submitNote(){
        this.noteSubmit.click();
    }

    public String getRecentNoteTitle(){
        String title = this.notesTitleClass.get(notesTitleClass.size()-1).getText();
        System.out.println("NOTES  DATA----" + title);
        return title;
    }

    public String getRecentNoteDescription(){
        String description = this.notesDescriptionClass.get(notesDescriptionClass.size()-1).getText();
        System.out.println("NOTES DESCRIPTION---" + description);
        return description;
    }

    public void noteAddClick(){
        this.noteAdd.click();
    }
}

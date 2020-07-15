package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller

public class HomeController {

    private final NoteService noteService;
    private final UserService userService;

    public HomeController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHome(Authentication authentication,Model model){
        String username = authentication.getName();
        User user = userService.getUser(username);
        if(user != null) {
            System.out.println("USER ID" + user.getUserId());
            model.addAttribute("notes", noteService.getNotes(user.getUserId()));
        }
        return "home";
    }

    @PostMapping("/file-upload")
    public String fileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        if(fileUpload.isEmpty()){

        }
        return "home";
    }

    @PostMapping("/add-note")
    public  String addNote(Authentication authentication, Note note, Model model){
        String addNoteError = null;

        User user = userService.getUser(authentication.getName());
        if(user != null){
            note.setUserId(user.getUserId());
            Integer rowAdded = noteService.addNote(note);
            if(rowAdded < 0){
                addNoteError = "Error adding note";
            }
        }else{
            addNoteError = "Unable to add note";
        }

        if(addNoteError !=null){
            model.addAttribute("homeSuccess", true);
        }else{
            model.addAttribute("homeError", addNoteError);
        }

        return "home";
    }
}

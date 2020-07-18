package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller

public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;
    private final FileService fileService;

    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String getHome(Authentication authentication,Model model){
        String username = authentication.getName();
        User user = userService.getUser(username);
        if(user != null) {
            model.addAttribute("files",fileService.getFiles(user.getUserId()));
            model.addAttribute("notes", noteService.getNotes(user.getUserId()));
            model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));

        }
        return "home";
    }

    @PostMapping("/file-upload")
    public String fileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        String fileUploadError = null;
        User user = userService.getUser(authentication.getName());
        if(user != null){
            if(!fileUpload.isEmpty()){
                if(!fileService.isFileNameAvailable(fileUpload.getOriginalFilename())){
                    fileUploadError = "Duplicate file names are not allowed";
                }else {
                    Integer rowAdded = fileService.addFile(fileUpload, user.getUserId());
                    if (rowAdded < 0) {
                        fileUploadError = "There seems to be some error";
                    }
                }
            }else{
                fileUploadError = "Input cannot be empty";
            }
        }else{
            fileUploadError = "No user found";
        }

        if(fileUploadError == null){
            model.addAttribute("homeSuccess", "File added successfully");

        }else{
            model.addAttribute("homeError", fileUploadError);
        }
        if(user != null) {
            model.addAttribute("files",fileService.getFiles(user.getUserId()));
            model.addAttribute("notes", noteService.getNotes(user.getUserId()));
            model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));
        }

        return "home";
    }


    @GetMapping("/delete-file")
    public String deleteFile(Authentication authentication,@RequestParam String id,Model model){
        String deleteFileError = null;
        Integer rowDeleted = fileService.deleteFile(Integer.parseInt(id));
        User user = userService.getUser(authentication.getName());
        if(user != null) {

            model.addAttribute("files",fileService.getFiles(user.getUserId()));
            model.addAttribute("notes", noteService.getNotes(user.getUserId()));
            model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));
        }
        if(rowDeleted > 0){
            model.addAttribute("homeDeletedSuccess", "File deleted");
        }else{
            model.addAttribute("homeError", "Unable to delete file");
        }
        return "home";
    }

    @GetMapping("/get-file/{fileId}/{fileName}")
    public ResponseEntity getFile(@PathVariable Integer fileId, @PathVariable String fileName) throws Exception{
        FileModel file = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }

    @PostMapping("/add-note")
    public  String addNote(Authentication authentication, Note note, Model model){
        String addNoteError = null;
        String addNoteMessage = null;
        User user = userService.getUser(authentication.getName());
        if(note.getNoteId() != null){
            noteService.updateNote(note);
            addNoteMessage = "Note updated successfully";
        }else if(user != null){
            note.setUserId(user.getUserId());
            Integer rowAdded = noteService.addNote(note);
            if(rowAdded < 0){
                addNoteError = "Error adding note";
            }else{
                addNoteMessage = "Note added successfully";
            }
        }else{
            addNoteError = "Unable to add note";
        }

        if(addNoteError == null){
            model.addAttribute("homeSuccess", addNoteMessage);
            if(user != null) {

                model.addAttribute("files",fileService.getFiles(user.getUserId()));
                model.addAttribute("notes", noteService.getNotes(user.getUserId()));
                model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));
            }
        }else{
            model.addAttribute("homeError", addNoteError);
        }
        return "home";
    }

    @GetMapping("/delete-note")
    public String deleteNote(Authentication authentication,@RequestParam String id,Model model){
        String deleteNoteError = null;
        Integer rowDeleted = noteService.deleteNote(Integer.parseInt(id));
        User user = userService.getUser(authentication.getName());
        if(user != null) {

            model.addAttribute("files",fileService.getFiles(user.getUserId()));
            model.addAttribute("notes", noteService.getNotes(user.getUserId()));
            model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));
        }
        if(rowDeleted > 0){
            model.addAttribute("homeDeletedSuccess", "Note deleted");
        }else{
            model.addAttribute("homeError", "Unable to delete note");
        }
        return "home";
    }
    @GetMapping(value = "/decode-password",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Map<String, String> decodePassword(@RequestParam Integer credentialId){
        Credential credential = credentialService.getCredential(credentialId);
        String decodedPassword = credentialService.decodePassword(credential);
        Map<String, String> response = new HashMap<>();
        response.put("originalPassword",credential.getPassword());
        response.put("decodedPassword", decodedPassword);

        return response;
    }

    @PostMapping("/add-credential")
    public String addCredential(Authentication authentication, Credential credential, Model model){
        String addCredentialError = null;
        String addCredentialMessage = null;

        User user = userService.getUser(authentication.getName());
        if(credential.getCredentialId() != null) {
            credentialService.updateCredential(credential);
            addCredentialMessage = "Credential updated successfully";
        }else if(user != null){
            credential.setUserId(user.getUserId());
            Integer rowAdded = credentialService.addCredential(credential);
            if(rowAdded < 0){
                addCredentialError = "Error adding note";
            }else{
                addCredentialMessage = "Credential added successfully";
            }
        }else{
            addCredentialError = "Unable to add note";
        }

        if(addCredentialError == null){
            model.addAttribute("homeSuccess", addCredentialMessage);
            if(user != null) {

                model.addAttribute("files",fileService.getFiles(user.getUserId()));
                model.addAttribute("notes", noteService.getNotes(user.getUserId()));
                model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));
            }
        }else{
            model.addAttribute("homeError", addCredentialError);
        }
        return "home";
    }

    @GetMapping("/delete-credential")
    public String deleteCredential(Authentication authentication,@RequestParam String id,Model model){
        String deleteCredentialError = null;
        Integer rowDeleted = credentialService.deleteCredential(Integer.parseInt(id));
        User user = userService.getUser(authentication.getName());
        if(user != null) {

            model.addAttribute("files",fileService.getFiles(user.getUserId()));
            model.addAttribute("notes", noteService.getNotes(user.getUserId()));
            model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));
        }
        if(rowDeleted > 0){
            model.addAttribute("homeDeletedSuccess", "Credential deleted");
        }else{
            model.addAttribute("homeError", "Unable to delete credential");
        }
        return "home";
    }

}

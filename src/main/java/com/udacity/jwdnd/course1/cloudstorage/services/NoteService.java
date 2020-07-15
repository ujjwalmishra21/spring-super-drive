package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappings.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoteService {
    private final NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public List<Note> getNotes(Integer userId){
        System.out.println("USER ID getNote() " + userId);
        return notesMapper.getNotes(userId);
    }

    public int addNote(Note note){
        return notesMapper.insertNotes(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));
    }
}

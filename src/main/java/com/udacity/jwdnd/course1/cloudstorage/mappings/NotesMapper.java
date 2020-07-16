package com.udacity.jwdnd.course1.cloudstorage.mappings;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * FROM NOTES WHERE USERID=#{userId}")
    List<Note> getNotes(Integer userId);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNotes(Note note);

    @Update("UPDATE NOTES SET NOTETITLE=#{noteTitle}, NOTEDESCRIPTION=#{noteDescription} WHERE NOTEID=#{noteId}")
    Integer updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE NOTEID=#{noteId}")
    Integer deleteNote(Integer noteId);

}

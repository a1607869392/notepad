package control;

import bean.Note;
import model.NoteDao;
import model.NoteService;

import java.sql.SQLException;
import java.util.ArrayList;

public class NoteControl {
    private NoteService noteService = new NoteServiceImpl();

    public ArrayList<Note> findAll() throws SQLException {
        ArrayList<Note> notes = noteService.findAll();
        for (Note note : notes) {
            System.out.println(note);
        }
        return notes;
    }

    public void insertNote(Note note) throws SQLException {
    }

    public void updateNote(Note note) throws SQLException {
    }

    public void deleteNote(int id) throws SQLException {
    }

}

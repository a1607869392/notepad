package control;

import bean.Note;
import model.NoteDao;
import model.NoteService;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 作者: 86199
 * 描述: 控制层，调用业务层返回数据
 * 日期: 2025/2/27
 */
public class NoteControl {
    private NoteService noteService = new NoteServiceImpl();

    public ArrayList<Note> findAll() throws SQLException {
        ArrayList<Note> notes = noteService.findAll();
        for (Note note : notes) {
            System.out.println(note);
        }
        return notes;
    }

    public Note findById(int id) throws SQLException {
        Note note = new Note();
        note = noteService.findById(id);
        return note;
    }

    public int insertNote(Note note) throws SQLException {
        return noteService.insertNote(note);
    }

    public int updateNote(Note note) throws SQLException {
        return noteService.updateNote(note);
    }

    public int deleteNote(int id) throws SQLException {
        return noteService.deleteNote(id);
    }

}

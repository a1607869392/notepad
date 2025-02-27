package control;

import bean.Note;
import model.NoteDao;
import model.NoteService;

import java.sql.SQLException;
import java.util.ArrayList;

public class NoteServiceImpl implements NoteService {

            private NoteDao noteDao = new NoteDaoImpl();
    @Override
    public ArrayList<Note> findAll() throws SQLException {
        return noteDao.findAll();
    }

    @Override
    public Note findById(int id) throws SQLException {
        return null;
    }

    @Override
    public Note findByTitle(String title) throws SQLException {
        return null;
    }

    @Override
    public int insertNote(Note note) throws SQLException {
        return 0;
    }

    @Override
    public int updateNote(Note note) throws SQLException {
        return 0;
    }

    @Override
    public int deleteNote(int id) throws SQLException {
        return 0;
    }
}

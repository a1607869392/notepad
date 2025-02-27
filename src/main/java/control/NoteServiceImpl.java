package control;

import bean.Note;
import model.NoteDao;
import model.NoteService;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 作者: 86199
 * 描述: 业务层实现类
 * 日期: 2025/2/26
 */
public class NoteServiceImpl implements NoteService {

    private NoteDao noteDao = new NoteDaoImpl();

    @Override
    public ArrayList<Note> findAll() throws SQLException {
        return noteDao.findAll();
    }

    @Override
    public Note findById(int id) throws SQLException {
        return noteDao.findById(id);
    }

    @Override
    public Note findByTitle(String title) throws SQLException {
        return null;
    }

    @Override
    public int insertNote(Note note) throws SQLException {
        return noteDao.insertNote(note);
    }

    @Override
    public int updateNote(Note note) throws SQLException {
        return noteDao.updateNote(note);
    }

    @Override
    public int deleteNote(int id) throws SQLException {
        return noteDao.deleteNote(id);
    }
}

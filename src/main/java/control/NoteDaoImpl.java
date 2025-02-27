package control;

import bean.Note;
import model.NoteDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NoteDaoImpl implements NoteDao {

    @Override
    public ArrayList<Note> findAll() throws SQLException {
        ArrayList<Note> notes=new ArrayList<>();
        ResultSet rs;
        DatabaseConnection db=null;
        Statement statement=null;
        try {
         db = new DatabaseConnection();
       statement= db.connect().createStatement();

        String sql = "select * from notes";
            rs=statement.executeQuery(sql);
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createTime = rs.getString("created_at");
                Note note = new Note(id,title,content,createTime);
                notes.add(note);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if (DatabaseConnection.connect()!=null){
                try {
                    DatabaseConnection.connect().close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return notes;
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

package control;

import bean.Note;
import model.NoteDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 作者: 86199
 * 描述: 数据持久层实现类
 * 日期: 2025/2/26
 */
public class NoteDaoImpl implements NoteDao {

    @Override
    public ArrayList<Note> findAll() throws SQLException {
        ArrayList<Note> notes = new ArrayList<>();
        ResultSet rs;
        DatabaseConnection db = null;
        Statement statement = null;
        try {
            db = new DatabaseConnection();
            statement = db.connect().createStatement();

            String sql = "select * from notes";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createTime = rs.getString("created_at");
                Note note = new Note(id, title, content, createTime);
                notes.add(note);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (DatabaseConnection.connect() != null) {
                try {
                    DatabaseConnection.connect().close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (statement != null) {
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
        Note note = new Note();
        ResultSet rs;
        DatabaseConnection db = null;
        Statement statement = null;
        try {
            db = new DatabaseConnection();
            statement = db.connect().createStatement();

            String sql = "select * from notes where id=" + id;
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Integer sid = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createTime = rs.getString("created_at");
                note = new Note(sid, title, content, createTime);
                return note;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (DatabaseConnection.connect() != null) {
                try {
                    DatabaseConnection.connect().close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return note;
    }

    @Override
    public Note findByTitle(String title) throws SQLException {
        return null;
    }

    @Override
    public int insertNote(Note note) throws SQLException {
        DatabaseConnection db = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            db = new DatabaseConnection();
            // 使用占位符代替直接拼接变量
            String sql = "INSERT INTO notes (title, content, created_at) VALUES (?, ?, ?)";

            preparedStatement = db.connect().prepareStatement(sql);

            // 设置占位符参数
            preparedStatement.setString(1, note.getTitle());      // 设置标题
            preparedStatement.setString(2, note.getContent());    // 设置内容
            preparedStatement.setString(3, note.getCrateTime());  // 设置创建时间

            // 执行插入操作
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // 捕获 SQL 异常并打印详细信息
            e.printStackTrace(); // 打印异常信息以帮助调试
            throw new RuntimeException(e);
        } finally {
            if (db != null && db.connect() != null) {
                try {
                    db.connect().close(); // 关闭数据库连接
                } catch (Exception e) {
                    e.printStackTrace(); // 打印异常信息
                    throw new RuntimeException(e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close(); // 关闭 PreparedStatement
                } catch (SQLException e) {
                    e.printStackTrace(); // 打印异常信息
                    throw new RuntimeException(e);
                }
            }
        }
        return result; // 返回影响的行数
    }


    @Override
    public int updateNote(Note note) throws SQLException {
        DatabaseConnection db = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            db = new DatabaseConnection();
            String sql = "UPDATE notes SET title=?, content=?, created_at=? WHERE id=?";

            preparedStatement = db.connect().prepareStatement(sql);

            // 设置 SQL 语句中的参数
            preparedStatement.setString(1, note.getTitle()); // 设置 title
            preparedStatement.setString(2, note.getContent()); // 设置 content
            preparedStatement.setString(3, note.getCrateTime()); // 设置 created_at
            preparedStatement.setInt(4, note.getId()); // 设置 id

            // 执行更新操作
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (db != null && db.connect() != null) {
                try {
                    db.connect().close(); // 关闭数据库连接
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close(); // 关闭 PreparedStatement
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result; // 返回影响的行数
    }


    @Override
    public int deleteNote(int id) throws SQLException {
        DatabaseConnection db = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            db = new DatabaseConnection();
            // 使用占位符代替直接拼接变量
            String sql = "DELETE FROM notes WHERE id=?";

            preparedStatement = db.connect().prepareStatement(sql);
            // 设置 id 参数
            preparedStatement.setInt(1, id);

            // 执行删除操作
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (db != null && db.connect() != null) {
                try {
                    db.connect().close(); // 关闭数据库连接
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close(); // 关闭 PreparedStatement
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result; // 返回影响的行数
    }

}

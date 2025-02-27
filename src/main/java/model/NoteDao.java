package model;

import bean.Note;

import java.sql.SQLException;
import java.util.ArrayList;
/**
 * 作者: 86199
 * 描述: 数据持久层
 * 日期: 2025/2/26
 */
public interface NoteDao {
    //查询
    public abstract ArrayList<Note> findAll() throws SQLException;
    public abstract Note findById(int id) throws SQLException;
    public abstract Note findByTitle(String title) throws SQLException;
    //插入
    public abstract int insertNote(Note note) throws SQLException;
    //更改
    public abstract int updateNote(Note note) throws SQLException;
    //删除
    public abstract int deleteNote(int id) throws SQLException;
}

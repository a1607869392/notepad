package ui;

import bean.Note;
import control.NoteControl;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 作者: 86199
 * 描述: 主界面
 * 日期: 2025/2/25
 */
public class MainNote implements Login.LoginCallback{
    static DefaultListModel<Note> listModel = new DefaultListModel<>();
    static JMenuBar menuBar = new JMenuBar();
    static JTextArea textArea = new JTextArea();
    static ArrayList<String> arrayList = new ArrayList<>();
    static int selectedIndex = -1;
    static CustomJList customJList;
    static NoteControl noteControl = new NoteControl();
    // 创建右键菜单
    static JPopupMenu popupMenu = new JPopupMenu();
   Login login;
    public MainNote() {
        // 创建登录界面并传入回调接口
        login= new Login(this);  // 这里将 MainNote 作为 Login 的回调接口传入
    }

    // 创建主界面
    private static void createMainWindow() {
        // 创建 JFrame
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setTitle("记事本");
        frame.setLocation(520, 245);
        // 设置自定义图标
        Image icon = Toolkit.getDefaultToolkit().getImage("G:\\code\\notepad\\src\\main\\resources\\textbook.png"); // 替换为图标文件的路径
        frame.setIconImage(icon);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 创建 SplitPane 左右分栏布局
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(150); // 设置左右分栏比例
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);

        // 左侧 JList 显示保存的记事本内容
        initList(listModel);

        customJList = new CustomJList(listModel);
        JScrollPane listScrollPane = new JScrollPane(customJList);
        splitPane.setLeftComponent(listScrollPane);

        // 右侧文本编辑区域
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textScrollPane = new JScrollPane(textArea);
        splitPane.setRightComponent(textScrollPane);

        // 创建 UndoManager 来管理撤回操作
        UndoManager undoManager = new UndoManager();
        // 监听文档变化并记录撤回操作
        textArea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));

        // 上方菜单栏
        initMenu(menuBar, undoManager);
        frame.setJMenuBar(menuBar);

        // 为 JList 添加选择事件监听
        customJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                textArea.setText("");
                selectedIndex = customJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String content = customJList.getSelectedValue().getContent();
                    System.out.println(content);
                    if (!content.equals("")) {
                        textArea.setText(customJList.getSelectedValue().getContent());
                    }
                }
            }
        });

        // 为 JList 添加鼠标监听器，右键点击时弹出菜单
        customJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e);
            }

            private void showPopupMenu(MouseEvent e) {
                // 判断是否为右键点击
                if (e.isPopupTrigger()) {
                    int index = customJList.locationToIndex(e.getPoint());
                    customJList.setSelectedIndex(index);  // 选中右键点击的项
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        // 显示界面
        frame.setVisible(true);
    }

    private static void initMenu(JMenuBar menuBar, UndoManager undoManager) {
        JMenu menu = new JMenu("文件");
        JMenuItem menuItem1 = new JMenuItem("撤回");
        JMenuItem menuItem2 = new JMenuItem("保存");
        JMenuItem menuItem3 = new JMenuItem("新建");
        JMenuItem menuItem4 = new JMenuItem("ai助手");
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        menu.add(menuItem4);

        // 添加菜单项
        JMenuItem deleteItem = new JMenuItem("删除");

        //设置快捷键
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        //设置点击事件
        menuItem1.addActionListener(e -> {
            undoManager.undo();
        });
        long currentTimestamp = System.currentTimeMillis();
        System.out.println("当前时间戳: " + currentTimestamp);

        menuItem2.addActionListener(e -> {
            if (textArea.getText().isEmpty()) {
                // 如果文本框为空，提示用户
                JOptionPane.showMessageDialog(null, "内容不能为空！");
            } else {
                try {
                    Note newnote = customJList.getSelectedValue();
                    customJList.getSelectedValue().setContent(textArea.getText());
                    newnote.setContent(textArea.getText());
                    noteControl.updateNote(newnote);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        menuItem3.addActionListener(e -> {
            addListItem(listModel);
        });
        menuItem4.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new ChatUI().setVisible(true));
        });
        // 点击删除时，移除选中的项
        deleteItem.addActionListener(e -> {
            int selectedIndex = customJList.getSelectedIndex();
            Note note = customJList.getSelectedValue();
            try {
                noteControl.deleteNote(note.getId());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        });
        popupMenu.add(deleteItem);

        menuBar.add(menu);
    }

    private static void addListItem(DefaultListModel<Note> listModel) {
        String noteText = "Note " + (listModel.getSize() + 1);
        int index = listModel.getSize() + 1;
        try {
            noteControl.insertNote(new Note(noteText, "", getTime()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listModel.addElement(new Note(index, noteText, "", getTime()));
        String noteList = "";
        arrayList.add(noteList);
        // 自动选中新增的项
        customJList.setSelectedIndex(listModel.getSize() - 1);

        // 使用 SwingUtilities.invokeLater 确保在事件调度线程中更新
        SwingUtilities.invokeLater(() -> {
            textArea.setText("");  // 清空文本框
        });
    }

    // 获取当前时间
    private static String getTime() {
        // 获取当前时间戳
        long currentTimestamp = System.currentTimeMillis();

        // 创建 SimpleDateFormat 对象，定义输出格式为：yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 将时间戳转换为 Date 对象
        Date date = new Date(currentTimestamp);

        // 将 Date 对象格式化为指定格式的字符串
        String formattedDate = sdf.format(date);

        // 返回格式化后的日期
        return formattedDate;
    }

    private static void initList(DefaultListModel<Note> listModel) {
        // 模拟已保存的记事本内容（可以根据实际数据来加载）
        ArrayList<Note> savedNotes = new ArrayList<>();
        try {
            savedNotes = noteControl.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (savedNotes.isEmpty()) {
            try {
                noteControl.insertNote(new Note(1, "Note1", "", getTime()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            savedNotes.add(new Note(1, "Note1", "", getTime()));
        }

        // 加载模拟数据到 JList
        for (Note note : savedNotes) {
            listModel.addElement(note);
        }
    }

    @Override
    public void onLoginSuccess() {
        createMainWindow();
        login.dispose();
    }

    @Override
    public void onLoginFailure() {
    }
}


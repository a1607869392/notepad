package ui;

import bean.Note;
import control.NoteControl;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Start {
    static DefaultListModel<Note> listModel = new DefaultListModel<>();
    static JMenuBar menuBar = new JMenuBar();
    static JTextArea textArea = new JTextArea();
    static ArrayList<String> arrayList = new ArrayList<>();
    static int selectedIndex = -1;
    static CustomJList customJList;

    public static void main(String[] args) {
        // 创建 JFrame
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setTitle("记事本");
        frame.setLocation(520, 245);
        // 设置自定义图标
        Image icon = Toolkit.getDefaultToolkit().getImage("G:\\code\\Array\\out\\production\\Source\\textb.png"); // 替换为图标文件的路径
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

        //上方菜单栏

        initMenu(menuBar, undoManager);
        frame.setJMenuBar(menuBar);

        // 为 JList 添加选择事件监听
        customJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // arrayList.set(customJList.getSelectedIndex(), textArea.getText());
                textArea.setText("");
                selectedIndex = customJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    if (arrayList.size() - 1 >= selectedIndex) {
                        textArea.setText(arrayList.get(selectedIndex));
                    } else {
                        textArea.setText("");
                    }
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
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        //设置快捷键
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
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
                if (selectedIndex != -1) {
                    arrayList.set(selectedIndex, textArea.getText());
                }
            }
        });
        menuItem3.addActionListener(e -> {
            arrayList.set(selectedIndex, textArea.getText());
            addListItem(listModel);

        });
        menuBar.add(menu);
    }

    private static void addListItem(DefaultListModel<Note> listModel) {
        String noteText = "Note " + (listModel.getSize() + 1) ;
        int index = listModel.getSize()+1;
        listModel.addElement(new Note(index,noteText,"",getTime()) );
        String noteList = "";
        arrayList.add(noteList);
        // 自动选中新增的项
        customJList.setSelectedIndex(listModel.getSize() - 1);

        // 使用 SwingUtilities.invokeLater 确保在事件调度线程中更新
        SwingUtilities.invokeLater(() -> {
            textArea.setText("");  // 清空文本框
        });
    }


    //获取当前时间
    private static String getTime() {
        // 获取当前时间戳
        long currentTimestamp = System.currentTimeMillis();

        // 创建 SimpleDateFormat 对象，定义输出格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

        // 将时间戳转换为 Date 对象
        Date date = new Date(currentTimestamp);

        // 将 Date 对象格式化为指定格式的字符串
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    private static void initList(DefaultListModel<Note> listModel) {
        // 模拟已保存的记事本内容（可以根据实际数据来加载）
        NoteControl noteControl = new NoteControl();
        ArrayList<Note> savedNotes = new ArrayList<>();
        try {
            savedNotes = noteControl.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (savedNotes.isEmpty()) {
            savedNotes.add(new Note(1,"Note1","",getTime()));
            arrayList.add("");
        }

        // 加载模拟数据到 JList
        for (Note note : savedNotes) {
            listModel.addElement(note);
        }

    }


}


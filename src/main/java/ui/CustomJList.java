package ui;
import bean.Note;

import javax.swing.*;
import java.awt.*;
/**
 * 作者: 86199
 * 描述: 自定义 JList 类
 * 日期: 2025/2/25
 */
public class CustomJList extends JList<Note> {

    // 构造方法：传入 DefaultListModel
    public CustomJList(DefaultListModel<Note> listModel) {
        super(listModel);  // 调用父类 JList 构造方法初始化数据模型
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setCellRenderer(new NoteListCellRenderer());  // 设置自定义的渲染器
    }

    // 自定义 ListCellRenderer
    static class NoteListCellRenderer implements ListCellRenderer<Note> {
        private JPanel panel;
        private JLabel mainTextLabel;
        private JLabel timeLabel;

        public NoteListCellRenderer() {
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // 创建一个新的 BoxLayout 实例

            // 主文本标签，设置为支持中文的字体
            mainTextLabel = new JLabel();
            mainTextLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));  // 设置为微软雅黑
            panel.add(mainTextLabel);

            // 时间标签，设置为支持中文的字体
            timeLabel = new JLabel();
            timeLabel.setFont(new Font("Microsoft YaHei", Font.ITALIC, 12)); // 设置为微软雅黑
            timeLabel.setForeground(Color.GRAY);
            panel.add(timeLabel);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Note> list, Note note, int index, boolean isSelected, boolean cellHasFocus) {
            // 设置标题和时间
            mainTextLabel.setText(note.getTitle());  // 设置标题
            timeLabel.setText(note.getCrateTime());  // 设置时间

            // 设置选中状态的背景和字体颜色
            if (isSelected) {
                panel.setBackground(list.getSelectionBackground());
                mainTextLabel.setForeground(Color.WHITE);
                timeLabel.setForeground(Color.LIGHT_GRAY);
            } else {
                panel.setBackground(list.getBackground());
                mainTextLabel.setForeground(list.getForeground());
                timeLabel.setForeground(Color.GRAY);
            }

            return panel;  // 返回设置了渲染器的面板
        }
    }
}

package ui;
import javax.swing.*;
import java.awt.*;
//jdbc:mysql://localhost:3306
// 自定义 JList 类
public class CustomJList extends JList<String> {

    // 构造方法：传入 DefaultListModel
    public CustomJList(DefaultListModel<String> listModel) {
        super(listModel);  // 调用父类 JList 构造方法初始化数据模型
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setCellRenderer(new NoteListCellRenderer());  // 设置自定义的渲染器
    }

    // 自定义 ListCellRenderer
    static class NoteListCellRenderer implements ListCellRenderer<String> {
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
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            // 解析内容和时间
            String[] parts = value.split(" - ");
            String noteContent = parts[0];
            String timestamp = (parts.length > 1) ? parts[1] : "";

            // 设置主文本和时间
            mainTextLabel.setText(noteContent);
            timeLabel.setText(timestamp);

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

package ui;

/**
 * 作者: 86199
 * 描述: ai界面
 * 日期: 2025/2/28
 */

import bean.Content;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.ApiClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatUI extends JFrame {
    private JTextArea chatArea; // 显示对话内容的区域
    private JTextField inputField; // 输入消息的文本框
    private JButton sendButton; // 发送按钮
    private Gson gson = new Gson();
    public ChatUI() {
        // 设置窗口标题
        setTitle("聊天界面");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中

        // 设置自定义图标
        Image icon = Toolkit.getDefaultToolkit().getImage("G:\\code\\notepad\\src\\main\\resources\\textbook.png"); // 替换为图标文件的路径
        this.setIconImage(icon);
        // 初始化组件
        initComponents();
    }

    private void initComponents() {
        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 聊天区域
        chatArea = new JTextArea();
        chatArea.setEditable(false); // 不可编辑
        chatArea.setLineWrap(true); // 自动换行
        JScrollPane scrollPane = new JScrollPane(chatArea); // 添加滚动条
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 输入面板
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("发送");

        // 发送按钮事件
        sendButton.addActionListener(e -> sendMessage());

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // 添加主面板到窗口
        add(mainPanel);
    }

    // 发送消息
    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            // 显示自己发送的消息（右侧）
            appendMessage("我: " + message, true);

            // 清空输入框
            inputField.setText("");

            try {
                ApiClient.search(message, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseBody = response.body().string();
                        // 输出响应体
                        System.out.println(responseBody);
                        // 使用读取的字符串内容解析 JSON
                        Content content = gson.fromJson(responseBody, Content.class);
                        // 调用方法处理解析后的内容
                        appendMessage("ai回答:"+content.getContent(), false);
                    }
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void appendMessage(String message, boolean isRightAligned) {
        // 设置对齐方式
        if (isRightAligned) {
            chatArea.setAlignmentX(JTextArea.RIGHT_ALIGNMENT);
        } else {
            chatArea.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
        }

        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength()); // 确保滚动条也在主线程更新
        });
    }


}
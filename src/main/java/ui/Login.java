package ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * 作者: 86199
 * 描述: 登录界面
 * 日期: 2025/2/28
 */

public class Login {
    public static JFrame frame = new JFrame("登录界面");
    public static JLabel label1 = new JLabel("用户名");
    public static JTextField username = new JTextField(10);
    public static JLabel label2 = new JLabel("密   码");
    public static JPasswordField password = new JPasswordField(10);
    public static JButton Signinbtn = new JButton("登录");
    private LoginCallback callback;  // 用于回调的接口

    // 静态变量表示登录状态
    public static boolean isLoggedIn = false;

    // 定义一个回调接口
    public interface LoginCallback {
        void onLoginSuccess();

        void onLoginFailure();
    }

    public Login(LoginCallback callback) {
        this.callback = callback;
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initFrame(callback);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void initFrame(LoginCallback callback) {
        JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel01.add(label1);
        panel01.add(username);

        JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel02.add(label2);
        panel02.add(password);

        JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel03.add(Signinbtn);

        Box vBox = Box.createVerticalBox();
        vBox.add(panel01);
        vBox.add(panel02);
        vBox.add(panel03);
        // 设置自定义图标
        Image icon = Toolkit.getDefaultToolkit().getImage("G:\\code\\notepad\\src\\main\\resources\\textbook.png"); // 替换为图标文件的路径
        frame.setIconImage(icon);
        frame.setContentPane(vBox);

        // 登录按钮事件
        Signinbtn.addActionListener(e -> {
            String inputUsername = username.getText().trim();
            String inputPassword = new String(password.getPassword()).trim();

            // 判断用户名和密码是否正确
            if (checkLogin(inputUsername, inputPassword) == 1) {
                isLoggedIn = true;  // 登录成功，设置登录状态
                JOptionPane.showMessageDialog(frame, "登录成功！");
                callback.onLoginSuccess();

                // 可以跳转到主界面或者其他操作
            } else if (checkLogin(inputUsername, inputPassword) == 2) {
                isLoggedIn = true;
                saveAccount(inputUsername, inputPassword);
                JOptionPane.showMessageDialog(frame, "注册成功！");
                callback.onLoginSuccess();
            } else {
                JOptionPane.showMessageDialog(frame, "用户名或密码错误！");
                callback.onLoginFailure();
            }
        });


    }

    //重置文件
    public void clearFile() {
        String filePath = "login_data.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 清空文件内容
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 判断登录是否成功
    public int checkLogin(String inputUsername, String inputPassword) {
        String filePath = "login_data.txt";
        // 检查文件是否存在，不存在则创建
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                // 文件不存在则创建
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isEmpty = true; // 用于标记文件是否为空
            while ((line = br.readLine()) != null) {
                isEmpty = false;
                System.out.println("读取文件行: " + line); // 打印文件行
                String[] credentials = line.split(":");
                if (credentials.length == 2) {
                    String storedUsername = credentials[0].trim();
                    String storedPassword = credentials[1].trim();
                    if (inputUsername.equals(storedUsername) && inputPassword.equals(storedPassword)) {
                        return 1;
                    }
                }
            }
            if (isEmpty) {
                return 2; // 文件为空，返回注册成功
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0; // 未找到匹配的用户名和密码

    }

    // 保存账户信息到本地文件
    public void saveAccount(String username, String password) {
        String filePath = "login_data.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(username + ":" + password);  // 将用户名和密码存入文件
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dispose() {
        frame.dispose();
    }
}

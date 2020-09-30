package hog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;



public class Simulation extends JFrame {
    private static JMenuBar jMenuBar;
    private static JButton confirmButton;
    private static JButton resetButton;
    private static JButton helpButton;
    private static JTextArea input;
    private static JScrollPane jScrollPane;
    private static ActionListener confirmAction;
    private static ActionListener resetAction;
    private static ActionListener helpAction;
    private static Robot robot;
    private static Random random;

    //点击开始后程序多久开始输出数字，单位ms
    private static final int DELAY_TIME = 5000;
    //最小的输出间隔时间，不能为0，否则有几率出现BUG，单位ms
    private static final int MIN_INTERVAL_TIME = 10;
    //最大的输出间隔时间，单位ms
    private static final int MAX_INTERVAL_TIME = 20;


    public static void init(JFrame jFrame){
        jMenuBar = new JMenuBar();
        confirmButton = new JButton("开始");
        resetButton = new JButton("重置");
        helpButton = new JButton("帮助");
        input = new JTextArea(7, 35);
        jScrollPane = new JScrollPane(input);
        random = new Random();

        confirmAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] chars = input.getText().toCharArray();
                try {
                    robot = new Robot();
                    Thread.sleep(DELAY_TIME);
                    for (char ch : chars) {
                        analogPress(ch);
                        int intervalTime = random.nextInt(MAX_INTERVAL_TIME) % (MAX_INTERVAL_TIME - MIN_INTERVAL_TIME + 1) + MIN_INTERVAL_TIME;
                        Thread.sleep(intervalTime);
                    }
                } catch (AWTException | InterruptedException awtException) {
                    awtException.printStackTrace();
                }


            }
        };
        resetAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        helpAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hintMessage = "提示信息";
                String content = "在文本框内输入文本之后，点击'开始'，您有" + DELAY_TIME / 1000 + "秒的准备时间，请将大写锁关闭，将输入法调成英文，当程序开始输出内容时，请不要随意点击鼠标";
                JOptionPane.showMessageDialog(null, content, hintMessage, JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    private static void analogPress(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(ch);
            robot.keyRelease(ch);
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } else if (ch >= 'a' && ch <= 'z') {
            robot.keyPress(ch -  32);
        } else if (ch >= '0' && ch <= '9') {
            robot.keyPress(ch);
        } else {
            switch (ch) {
                case ',' : robot.keyPress(KeyEvent.VK_COMMA);break;
                case '.' : robot.keyPress(KeyEvent.VK_PERIOD);break;
                case '[' : robot.keyPress(KeyEvent.VK_OPEN_BRACKET);break;
                case ']' : robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);break;
                case ';' : robot.keyPress(KeyEvent.VK_SEMICOLON);break;
                case '\'' : robot.keyPress(KeyEvent.VK_QUOTE);break;
                case '/' : robot.keyPress(KeyEvent.VK_SLASH);break;
                case '\\' : robot.keyPress(KeyEvent.VK_BACK_SLASH);break;
                case '-' : robot.keyPress(KeyEvent.VK_MINUS);break;
                case '*' : robot.keyPress(KeyEvent.VK_MULTIPLY);break;
                case '=' : robot.keyPress(KeyEvent.VK_EQUALS);break;
                case 10 : robot.keyPress(KeyEvent.VK_ENTER);break;
                case ' ' : robot.keyPress(KeyEvent.VK_SPACE);break;
                case 9 : robot.keyPress(KeyEvent.VK_TAB);break;
                case '!' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_1);
                    robot.keyRelease(KeyEvent.VK_1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '@' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_2);
                    robot.keyRelease(KeyEvent.VK_2);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '#' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_3);
                    robot.keyRelease(KeyEvent.VK_3);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '$' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_4);
                    robot.keyRelease(KeyEvent.VK_4);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '%' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_5);
                    robot.keyRelease(KeyEvent.VK_5);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '^' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_6);
                    robot.keyRelease(KeyEvent.VK_6);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '&' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_7);
                    robot.keyRelease(KeyEvent.VK_7);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '(' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_9);
                    robot.keyRelease(KeyEvent.VK_9);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case ')' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_0);
                    robot.keyRelease(KeyEvent.VK_0);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '_' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_MINUS);
                    robot.keyRelease(KeyEvent.VK_MINUS);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case ':' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_SEMICOLON);
                    robot.keyRelease(KeyEvent.VK_SEMICOLON);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '"' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_QUOTE);
                    robot.keyRelease(KeyEvent.VK_QUOTE);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '?' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_SLASH);
                    robot.keyRelease(KeyEvent.VK_SLASH);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '{' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
                    robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '}' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
                    robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '+' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_EQUALS);
                    robot.keyRelease(KeyEvent.VK_EQUALS);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '|' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_BACK_SLASH);
                    robot.keyRelease(KeyEvent.VK_BACK_SLASH);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '<' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_COMMA);
                    robot.keyRelease(KeyEvent.VK_COMMA);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '>' :
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_PERIOD);
                    robot.keyRelease(KeyEvent.VK_PERIOD);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
            }
        }
    }

    public static void binding(JFrame jFrame) {

        jMenuBar.add(confirmButton);
        jMenuBar.add(resetButton);
        jMenuBar.add(helpButton);
        input.setLineWrap(true);
        jFrame.setJMenuBar(jMenuBar);
        jFrame.add(jScrollPane, BorderLayout.CENTER);

        confirmButton.addActionListener(confirmAction);
        resetButton.addActionListener(resetAction);
        helpButton.addActionListener(helpAction);

        jFrame.setTitle(jFrame.getClass().getSimpleName());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocation(new Point(750, 350));
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Simulation forExam = new Simulation();
        init(forExam);
        binding(forExam);
    }
}

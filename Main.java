package 可视化的汉罗塔;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main();
    }

    Main() {
        this.setFont(new Font("华文正楷", Font.BOLD, 20));
        this.setSize(700, 500);
        this.setLocation(250, 250);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("汉罗塔动画演示");


        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.setContentPane(panel);
//        绘制：输入提示语
        JLabel label = new JLabel("please enter pan‘s num：");
        label.setSize(200, 50);
        label.setLocation(75, 10);
        panel.add(label);
//        绘制：数字输入框
        JTextField InPutBox = new JTextField();
        InPutBox.setSize(50, 20);
        InPutBox.setLocation(250, 25);
        panel.add(InPutBox);
        //        为了防止有人乱输入，我们加一个输入框监听
        InPutBox.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {
                } else {
                    e.consume();              //屏蔽非法输入
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


//        绘制：按钮
        JButton Work = new JButton("开始");
        JButton Stop = new JButton("停止");
        Work.setSize(100, 20);
        Work.setLocation(320, 25);
        Stop.setSize(100, 20);
        Stop.setLocation(450, 25);
        panel.add(Work);
        panel.add(Stop);
        final Thread[] thread = {null};
        Work.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                try {
                    if (InPutBox.getText().isEmpty()) {
                        System.out.print("null");
                        JOptionPane.showMessageDialog(null, "请输入圆盘数");
                        return;
                    }
                    MyThread mp= new MyThread(Integer.parseInt(InPutBox.getText()));
                    thread[0] = new Thread(mp);
                    thread[0].start();
                    panel.add(mp);
                } catch (Exception e2) {
                }
            }
        });

        Stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if (thread[0] != null) {
                        thread[0].stop();
                    }

                } catch (Exception e2) {

                }

            }
        });
//        问题打印
        JPanel huabu = new JPanel();
        huabu.setLayout(null);
        panel.add(huabu);
        this.setVisible(true);

    }

}

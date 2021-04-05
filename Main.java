package Day_08;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends JFrame {// 记事本
    private JPanel contentPane;
    public JTextArea textarea;
    private File file;// 打开和保存共同的成员变量
    private boolean changed;// 用作关闭时判断文件是否变化
    private JMenuBar menubar;// 菜单栏
    private JMenu m1, m2, m3, m4, m5;// 五个菜单
    private UndoManager undom;// 撤销管理器
    private JLabel status;// 状态栏
    private String form;// 字体
    private int font;// 字形
    private int size;// 字体大小
    private JPopupMenu popu;// 弹出式菜单
    private JCheckBox check1;// 区分大小写
    private JCheckBox check2;// 循环
    private JTextField textfield;// 查找输入框
    private JRadioButton up;// 向上
    private JRadioButton down;// 向下单选按钮
    private JCheckBoxMenuItem item3_1;// 自动换行
    private JMenuItem item2_10;// 转到

    public Main() {
        setTitle("无标题");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);// 什么都不做
        setBounds(100, 100, 500, 300);
        addWindowListener(new WindowAdapter() {

            public void windowOpened(WindowEvent e) {// 窗体打开时设置大小
                Toolkit toolkit = getToolkit();
                Dimension dimen = toolkit.getScreenSize();// 获得屏幕大小
                int width = (int) (dimen.getWidth() * 0.7);
                int height = (int) (dimen.getHeight() * 0.8);
                setSize(width, height);
            }

            public void windowClosing(WindowEvent e) {// 窗体关闭时
                do_this_windowClosing(e);// 调用写好的监听
            }

        });
        /*
         * 初始化字体
         */
        form = "宋体";
        font = Font.ITALIC;
        size = 14;

        menubar = new JMenuBar();// 菜单栏
        setJMenuBar(menubar);// 给窗体设置菜单栏
        /*
         * m1-m5菜单对象
         */
        m1 = new JMenu("文件(F)");// 菜单
        m1.setMnemonic('F');// 设置快捷键ALT+F
        menubar.add(m1);// 菜单加导菜单栏

        m2 = new JMenu("编辑(E)");// 菜单
        m2.setMnemonic('E');// 设置快捷键
        menubar.add(m2);// 菜单加导菜单栏

        m3 = new JMenu("格式(O)");// 菜单
        m3.setMnemonic('O');// 设置快捷键
        menubar.add(m3);// 菜单加导菜单栏

        m4 = new JMenu("查看(V)");// 菜单
        m4.setMnemonic('V');// 设置快捷键
        menubar.add(m4);// 菜单加导菜单栏

        m5 = new JMenu("帮助(H)");// 菜单
        m5.setMnemonic('H');// 设置快捷键
        menubar.add(m5);// 菜单加导菜单栏
        // 添加菜单项
        addJMenum1();
        addJMenum2();
        addJMenum3();
        addJMenum4();
        addJMenum5();

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        JScrollPane scrollpane = new JScrollPane();// 滚动面板
        textarea = new JTextArea();
        textarea.setLineWrap(true);// 设置换行策略
        textarea.setFont(new Font("宋体", Font.ITALIC, 14));// 设置字体样式
        textarea.setTabSize(4);// 设置扩展字符大小
        scrollpane.setViewportView(textarea);// 设置面板视图
        contentPane.add(scrollpane, BorderLayout.CENTER);
        textarea.addKeyListener(new KeyAdapter() {// 给文本添加键盘监听

            public void keyTyped(KeyEvent e) {// 发生击键事件触发
                changed = true;

            }

        });
        /*
         * 弹出式菜单
         */
        JMenuItem item2_1 = new JMenuItem("撤销(U)");
        item2_1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (undom.canUndo()) {// 如果可以撤销
                    undom.undo();// 撤销
                }
            }
        });
        item2_1.setMnemonic('U');

        JMenuItem item2_2 = new JMenuItem("剪切(T)");
        item2_2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textarea.cut();// 剪切
            }
        });
        item2_2.setMnemonic('T');

        JMenuItem item2_3 = new JMenuItem("复制(C)");
        item2_3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textarea.copy();// 复制
            }
        });
        item2_3.setMnemonic('C');

        JMenuItem item2_4 = new JMenuItem("粘贴(P)");
        item2_4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textarea.paste();// 粘贴
            }
        });
        item2_4.setMnemonic('P');

        JMenuItem item2_5 = new JMenuItem("删除(L)");
        item2_5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String content = textarea.getSelectedText();// 获得选中的文本
                textarea.replaceSelection("");// 替换选中的文本
            }
        });
        item2_5.setMnemonic('L');

        JMenuItem item2_11 = new JMenuItem("全选(A)");
        item2_11.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textarea.selectAll();// 全部选中
            }
        });
        item2_11.setMnemonic('A');

        popu = new JPopupMenu();// 弹出式菜单
        popu.add(item2_1);
        popu.add(item2_2);
        popu.add(item2_3);
        popu.add(item2_4);
        popu.add(item2_5);
        popu.add(item2_11);

        textarea.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (textarea.getText().equals("")) {
                    item2_11.setEnabled(false);
                } else {
                    item2_11.setEnabled(true);
                }
                if (textarea.getSelectedText() == null) {
                    item2_2.setEnabled(false);
                    item2_3.setEnabled(false);
                    item2_5.setEnabled(false);
                } else {
                    item2_2.setEnabled(true);
                    item2_3.setEnabled(true);
                    item2_5.setEnabled(true);
                }
                if (e.getButton() == 3) {
                    popu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        undom = new UndoManager();// 创建撤销管理器
        textarea.getDocument().addUndoableEditListener(undom);// 为文本注册监听器

        status = new JLabel();
        status.setVisible(true);
        status.setHorizontalAlignment(JLabel.RIGHT);// 设置为右对齐
        getContentPane().add(status, BorderLayout.SOUTH);

        check1 = new JCheckBox("区分大小写(C)");// 复选框
        check2 = new JCheckBox("循环(R)");// 复选框
    }

    protected void addJMenum1() {
        /*
         * item1-item8文件的菜单项
         */
        JMenuItem item1 = new JMenuItem("新建(N)");
        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                item1_1_ActionListener(e);
            }
        });
        item1.setMnemonic('N');
        item1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        m1.add(item1);
        JMenuItem item2 = new JMenuItem("新窗口(W)");
        item2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                item1_2_ActionListener(e);
            }
        });
        item2.setMnemonic('W');
        item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));
        m1.add(item2);

        JMenuItem item3 = new JMenuItem("打开(O)");
        item3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                item1_3_ActionListener(e);
            }
        });
        item3.setMnemonic('O');
        item3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        m1.add(item3);

        JMenuItem item4 = new JMenuItem("保存(S)");
        item4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                item4_ActionListener(e);
            }
        });
        item4.setMnemonic('S');
        item4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        m1.add(item4);

        JMenuItem item5 = new JMenuItem("另存为(A)");
        item5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                item1_5_ActionListener(e);
            }
        });
        item5.setMnemonic('A');
        item5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));
        m1.add(item5);
        m1.addSeparator();

        JMenuItem item6 = new JMenuItem("页面设置(U)");
        item6.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                item1_6_ActionListener(e);
            }
        });
        item6.setMnemonic('U');
        m1.add(item6);

        JMenuItem item7 = new JMenuItem("打印(P)");
        item7.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                item1_7_ActionListener(e);
            }
        });
        item7.setMnemonic('P');
        item7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
        m1.add(item7);
        m1.addSeparator();

        JMenuItem item8 = new JMenuItem("退出(X)");
        item8.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                item1_8_ActionListener(e);
            }
        });
        item8.setMnemonic('X');
        m1.add(item8);

    }

    protected void do_this_windowClosing(WindowEvent e) {
        String nowtext = textarea.getText();
        if (changed) { // 如果文本改动过则弹出对话框
            Object[] options = { "保存(S)", "不保存(N)", "取消" }; // 自定义按钮上的文字
            int m = JOptionPane.showOptionDialog(this, "你想将更改保存到 无标题 吗？", "记事本", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (m == JOptionPane.YES_OPTION) { // 点击保存
                Save();// 调用保存文本的方法
                dispose();// 销毁窗体
            } else if (m == JOptionPane.NO_OPTION) {
                dispose();// 销毁窗体
            }
        } else {
            dispose();// 销毁窗体
        }
    }

    /*
     * 各种功能的监听方法
     */
    protected void item1_1_ActionListener(ActionEvent e) {// 新建
        this.file = null;// 设置为null
        changed = false;
        String text = textarea.getText();// 获取文本内容
        if (text.isEmpty()) {// 如果文本为空
            setTitle("无标题-记事本");
            textarea.setText("");// 清空文本域
        } else {
            Object[] options = { "保存(S)", "不保存(N)", "取消" }; // 自定义按钮上的文字
            int m = JOptionPane.showOptionDialog(this, "你想将更改保存到 无标题 吗？", "记事本", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (m == JOptionPane.YES_OPTION) {// 点击保存
                Save();// 调用保存的方法
                setTitle("无标题-记事本");
                textarea.setText("");// 清空文本域
            } else if (m == JOptionPane.NO_OPTION) {// 点击不保存
                setTitle("无标题-记事本");
                textarea.setText("");// 清空文本域
            }
        }
    }

    protected void item1_2_ActionListener(ActionEvent e) {// 新窗口
        Main m = new Main();
        m.setVisible(true);
    }

    protected void item1_3_ActionListener(ActionEvent e) {// 打开
        String text = textarea.getText();// 获取文本内容
        changed = false;
        if (text.isEmpty()) {// 如果文本为空,那么直接打开文件选择器
            Open();

        } else {
            Object[] options = { "保存(S)", "不保存(N)", "取消" }; // 自定义按钮上的文字
            int m = JOptionPane.showOptionDialog(this, "你想将更改保存到 无标题 吗？", "记事本", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (m == JOptionPane.YES_OPTION || m == JOptionPane.NO_OPTION) {// 判断是否打开文件
                if (m == JOptionPane.YES_OPTION) {// 如果要保存，否则直接跳过
                    Save();// 调用保存文本的方法
                }
                Open();// 调用打开文件的方法
            }
        }
    }

    /*
     * 保存，打开
     */
    private void Save() {// 保存的方法
        String text = textarea.getText();// 获取文本域的内容
        if (text.isEmpty()) {// 如果为空
            /*
             * 只要是对话框，尽量不要学null，指定父体 这样在完成对话框之前是不允许操作主窗体的
             */
            JOptionPane.showMessageDialog(this, "没有需要保存的文本");
            return;
        } else {
            JFileChooser filechooser = new JFileChooser();// 创建文件选择框
            int option = filechooser.showSaveDialog(this);// 打开文件选择框
            if (option == filechooser.APPROVE_OPTION) {// 判断用户单击是否为打开按钮
                File file = filechooser.getSelectedFile();// 获取用户选择的文件
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(file));// 创建文件的缓存输出流
                    bw.write(text);// 把文本保存导文件
                    bw.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    try {
                        bw.close();// 关闭流
                    } catch (IOException e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    private void Open() {// 打开文件的方法
        JFileChooser filechooser = new JFileChooser();// 创建一个文件选择器
        filechooser.setFileFilter(new FileNameExtensionFilter("文本文档(*.txt)", "txt"));// 设置文件过滤器
        int i = filechooser.showOpenDialog(this);// 显示对话框
        if (i == filechooser.APPROVE_OPTION) {// 如果点击了打开按钮
            this.file = filechooser.getSelectedFile();// 获取选中的文件
            this.setTitle(this.file.getName());// 设置标题
            BufferedReader br = null;// 声明缓存输入流
            try {
                br = new BufferedReader(new FileReader(this.file));// 创建文件的输入流
                String str = null;
                textarea.setText("");// 清空文本
                while ((str = br.readLine()) != null) {
                    textarea.append(str + "\n");// 追加写入到文本中
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();// 关闭流
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    protected void item4_ActionListener(ActionEvent e) {// 保存按钮监听
        String text = textarea.getText();// 获取文本域的内容
        changed = false;
        if (this.file != null) {// 判断文件是否存在，如果已经存在直接保存
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(file));// 创建文件的缓存输出流
                bw.write(text);// 把文本保存导文件
                bw.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    bw.close();// 关闭流
                } catch (IOException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }
            }
        } else {// 文件不存在，打开文件选择器进行保存
            Save();
        }
    }

    protected void item1_5_ActionListener(ActionEvent e) {// 另存为
        changed = false;
        FileDialog fd = new FileDialog(this, "另存为", FileDialog.SAVE);// 创建文件保存对话框
        fd.setFile(this.getTitle() + ".txt");
        fd.setVisible(true);// 显示对话框
        String text = textarea.getText();// 获取文本内容
        String parent = fd.getDirectory();// 获得父路径
        String child = fd.getFile();// 获得文件名
        if (parent != null && child != null) {// 判断是否要保存，或者取消
            File f = new File(parent, child);// 创建一个文件对象
            PrintStream ps = null;// 打印流
            try {
                ps = new PrintStream(f);
                ps.write(text.getBytes());
                ps.flush();
            } catch (FileNotFoundException e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        }
    }

    protected void item1_6_ActionListener(ActionEvent e) {
        Point();
    }

    protected void item1_7_ActionListener(ActionEvent e) {
        Point();
    }

    protected void item1_8_ActionListener(ActionEvent e) {// 退出监听
        dispose();// 销毁窗体
    }

    /*
     * Point()
     */
    private void Point() {// 未开发功能提示框
        JOptionPane.showMessageDialog(this, "对不起，此功能尚未实现！", "提示", JOptionPane.WARNING_MESSAGE);
    }

    protected void addJMenum2() {
        /*
         * item2_1-item2_13编辑的菜单项
         */
        JMenuItem item2_1 = new JMenuItem("撤销(U)");
        item2_1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (undom.canUndo()) {// 如果可以撤销
                    undom.undo();// 撤销
                }
            }
        });
        item2_1.setMnemonic('U');
        item2_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
        m2.add(item2_1);
        m2.addSeparator();// 添加分隔线

        JMenuItem item2_2 = new JMenuItem("剪切(T)");
        item2_2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textarea.cut();// 剪切
            }
        });
        item2_2.setMnemonic('T');
        item2_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
        m2.add(item2_2);

        JMenuItem item2_3 = new JMenuItem("复制(C)");
        item2_3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textarea.copy();// 复制
            }
        });
        item2_3.setMnemonic('C');
        item2_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        m2.add(item2_3);

        JMenuItem item2_4 = new JMenuItem("粘贴(P)");
        item2_4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textarea.paste();// 粘贴
            }
        });
        item2_4.setMnemonic('P');
        item2_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        m2.add(item2_4);

        JMenuItem item2_5 = new JMenuItem("删除(L)");
        item2_5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String content = textarea.getSelectedText();// 获得选中的文本
                textarea.replaceSelection("");// 替换选中的文本
            }
        });
        item2_5.setMnemonic('L');
        m2.add(item2_5);
        m2.addSeparator();

        JMenuItem item2_6 = new JMenuItem("查找(F)");
        item2_6.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Find();
            }
        });
        item2_6.setMnemonic('F');
        item2_6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));
        m2.add(item2_6);

        JMenuItem item2_7 = new JMenuItem("查找下一个(N)");
        item2_7.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String areastr = textarea.getText();// 获取文本区文本
                String fieldstr = textarea.getSelectedText();// 获取选中的文本
                if (fieldstr != null) {
                    String toupparea = areastr.toUpperCase();// 转为大写，用做区分大小写判断方便查找
                    String touppfield = fieldstr.toUpperCase();
                    String A;// 用做查找的文本域内容
                    String B;// 用作查找的文本框内容

                    if (check1.isSelected()) {// 区分大小写
                        A = areastr;
                        B = fieldstr;
                    } else {// 全部换为大写
                        A = toupparea;
                        B = touppfield;
                    }
                    int n = textarea.getCaretPosition();// 获取光标的位置
                    int m = 0;
                    // 开始查找
                    m = A.indexOf(B, n);
                    if (m != -1) {
                        textarea.setCaretPosition(m + fieldstr.length());
                        textarea.select(m, m + fieldstr.length());
                    } else {
                        if (check2.isSelected()) {// 如果循环
                            m = A.indexOf(B);// 从头开始找
                            if (m != -1) {
                                textarea.setCaretPosition(m + fieldstr.length());
                                textarea.select(m, m + fieldstr.length());
                            } else {
                                JOptionPane.showMessageDialog(null, "找不到 “" + fieldstr + "“", "查找",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "找不到 “" + fieldstr + "“", "查找",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
        item2_7.setMnemonic('N');
        item2_7.setAccelerator(KeyStroke.getKeyStroke("F3"));
        m2.add(item2_7);

        JMenuItem item2_8 = new JMenuItem("查找上一个(V)");
        item2_8.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String areastr = textarea.getText();// 获取文本区文本
                String fieldstr = textarea.getSelectedText();// 获取文本框文本
                if (fieldstr != null) {
                    String toupparea = areastr.toUpperCase();// 转为大写，用做区分大小写判断方便查找
                    String touppfield = fieldstr.toUpperCase();
                    String A;// 用做查找的文本域内容
                    String B;// 用作查找的文本框内容
                    if (check1.isSelected()) {// 区分大小写
                        A = areastr;
                        B = fieldstr;
                    } else {// 全部换为大写
                        A = toupparea;
                        B = touppfield;
                    }
                    int n = textarea.getCaretPosition();// 获取光标的位置
                    int m = 0;
                    // 开始向上查找
                    if (textarea.getSelectedText() == null) {
                        m = A.lastIndexOf(B, n - 1);
                    } else {
                        m = A.lastIndexOf(B, n - fieldstr.length() - 1);
                    }
                    if (m != -1) {
                        textarea.setCaretPosition(m);
                        textarea.select(m, m + fieldstr.length());
                    } else {
                        if (check1.isSelected()) {// 如果循环
                            m = A.lastIndexOf(B);// 从后面开始找
                            if (m != -1) {
                                textarea.setCaretPosition(m);
                                textarea.select(m, m + fieldstr.length());
                            } else {
                                JOptionPane.showMessageDialog(null, "找不到 “" + fieldstr + "“", "查找",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "找不到 “" + fieldstr + "“", "查找",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
        item2_8.setMnemonic('V');
        item2_8.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_F3, KeyEvent.SHIFT_MASK));
        m2.add(item2_8);

        JMenuItem item2_9 = new JMenuItem("替换(R)");
        item2_9.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Tihuan(e);
            }
        });
        item2_9.setMnemonic('R');
        item2_9.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
        m2.add(item2_9);

        this.item2_10 = new JMenuItem("转到(G)");
        this.item2_10.setEnabled(false);
        this.item2_10.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Zhuandao(e);
            }
        });
        item2_10.setMnemonic('G');
        item2_10.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_MASK));
        m2.add(item2_10);
        m2.addSeparator();

        JMenuItem item2_11 = new JMenuItem("全选(A)");
        item2_11.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                textarea.selectAll();// 全部选中
            }
        });
        item2_11.setMnemonic('A');
        item2_11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
        m2.add(item2_11);

        JMenuItem item2_12 = new JMenuItem("时间/日期(D)");
        item2_12.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Date date = new Date();// 获得当前日期
                /*
                 * 日期格式化SimpleDateFormat h小时，m分钟 y年份 M月份 d天数
                 */
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm yyyy-MM-dd");
//				System.out.println(sdf.format(date));
                textarea.append(sdf.format(date));// 追加到文本
            }
        });
        item2_12.setMnemonic('D');
        item2_12.setAccelerator(KeyStroke.getKeyStroke("F5"));
        m2.add(item2_12);

        m2.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (textarea.getText().equals("")) {
                    item2_11.setEnabled(false);
                } else {
                    item2_11.setEnabled(true);
                }
                if (textarea.getSelectedText() == null) {
                    item2_2.setEnabled(false);
                    item2_3.setEnabled(false);
                    item2_5.setEnabled(false);
                } else {
                    item2_2.setEnabled(true);
                    item2_3.setEnabled(true);
                    item2_5.setEnabled(true);
                }
            }
        });

    }

    private void Tihuan(ActionEvent e) {
        int end;// 结尾的位置
        int start;// 开始的位置
        JDialog d = new JDialog(this, "替换", false);
        Container c = d.getContentPane();
        d.setSize(350, 160);
        d.setLocation(200, 200);
        d.setResizable(false);
        JPanel jpanel = new JPanel();
        final JLabel label1 = new JLabel("查找内容(N)");
        final JTextField fiel = new JTextField(10);// 查找框
        final JButton but1 = new JButton("查找 下一 个(F)");// 对按钮添加事件
        final JLabel label2 = new JLabel("替   换  为(P)");
        final JTextField fiel2 = new JTextField(10);// 替换框
        final JButton but2 = new JButton("替     换    (   R   )");
        final JButton but3 = new JButton("取消");
        final JCheckBox box = new JCheckBox("区分大小写(C)");

        but1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String str1 = textarea.getText();// 获取文本内容
                String str2 = fiel.getText();// 获取查找文本的内容
                String str3 = str1.toUpperCase();// 转为大写
                String str4 = str2.toUpperCase();
                String A, B;
                if (box.isSelected()) {
                    A = str1;
                    B = str2;
                } else {
                    A = str3;
                    B = str4;
                }
                int n = textarea.getCaretPosition();// 获取光标所处位置
                int m = A.indexOf(B, n);// 得到找到文本的位置。或者没有为-1
                if (m != -1) {
                    textarea.setCaretPosition(m + str2.length());
                    textarea.select(m, m + str2.length());
                } else {
                    m = A.indexOf(B);// 从头开始找
                    if (m != -1) {
                        textarea.setCaretPosition(m + str2.length());
                        textarea.select(m, m + str2.length());
                    } else {
                        JOptionPane.showMessageDialog(null, "找不到 “" + str2 + "”");
                    }
                }
            }
        });
        but2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String str1 = fiel.getText();
                String str2 = fiel2.getText();
                if (textarea.getSelectedText() != null) {// 如果有选中的
                    textarea.replaceRange(str2, textarea.getSelectionStart(), textarea.getSelectionEnd());
                } else {
                    JOptionPane.showMessageDialog(null, "选择内容不能为空！");
                }
            }
        });
        but3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        jpanel.add(label1);
        jpanel.add(fiel);
        jpanel.add(but1);
        jpanel.add(label2);
        jpanel.add(fiel2);
        jpanel.add(but2);
        jpanel.add(box);
        jpanel.add(but3);
        c.add(jpanel);
        d.setVisible(true);
    }

    private void Zhuandao(ActionEvent e) {
        JDialog dialog = new JDialog(this, "转到指定行");
        JButton define = new JButton("转到");
        JButton off = new JButton("取消");
        dialog.setLayout(null);
        Container c = dialog.getContentPane();
        JLabel l = new JLabel("行号(L)：");
        JTextField field = new JTextField();
        l.setBounds(10, 12, 400, 30);
        field.setBounds(10, 42, 350, 28);
        define.setBounds(180, 84, 80, 28);
        define.setContentAreaFilled(false);
        off.setBounds(270, 84, 80, 28);
        off.setContentAreaFilled(false);

        dialog.setBounds(200, 200, 400, 160);
        dialog.setResizable(false);
        c.add(l);
        c.add(field);
        c.add(define);
        c.add(off);
        dialog.setVisible(true);
        define.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int r = textarea.getLineCount();// 确定行数
                String str[] = textarea.getText().split("\n");
                int count = 0;
                try {
                    count = Integer.parseInt(field.getText().trim());
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "请输入数字！");
                }
                if (r >= count) {
                    int sum = 0;
                    for (int i = 0; i < count - 1; i++) {
                        sum += str[i].length() + 1;
                    }
                    textarea.setCaretPosition(sum);
                } else {
                    JOptionPane.showMessageDialog(null, "行数超过了总行数！");
                }
            }
        });
        off.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }

    private void Find() {// 查找
        final JDialog dislog = new JDialog(this, "查找", false);// 创建一个窗体
        Container con = dislog.getContentPane();// 将窗体转化为容器
        con.setLayout(null);// 取消布局
        final JLabel j1 = new JLabel("查找内容(N)：");
        textfield = new JTextField(18);// 文本框
        final JButton b1 = new JButton("查找下一个(F)");
        final JButton b2 = new JButton("取消");
        b1.setBounds(330, 10, 115, 25);
        b1.setContentAreaFilled(false);
        b2.setBounds(330, 40, 115, 25);
        b2.setContentAreaFilled(false);

        /*
         * 加入到按钮组中的按钮只能选中其一，其他的咋会关闭
         */
        up = new JRadioButton("向上(U)");
        down = new JRadioButton("向下(D)");
        final ButtonGroup group = new ButtonGroup();// 按钮组
        group.add(up);
        group.add(down);
        down.setSelected(true);// 默认选中向下
        check1.setBounds(0, 100, 140, 30);
        check1.setFont(new Font("黑体", Font.ITALIC, 14));// 设置字体
        check2.setBounds(0, 130, 140, 30);
        check2.setFont(new Font("黑体", Font.ITALIC, 14));
        j1.setFont(new Font("黑体", Font.ITALIC, 14));

        /*
         * 设置快捷键
         */
        up.setMnemonic('U');
        down.setMnemonic('D');
        b1.setMnemonic('F');
        check1.setMnemonic('C');
        check2.setMnemonic('R');

        JPanel p1 = new JPanel();// j1,textfield
        JPanel p4 = new JPanel();// 放up down
        // 设置面板p1
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));// 流体布局，左对齐
        p1.setLocation(0, 10);
        p1.setSize(330, 60);
        p1.add(j1);
        p1.add(textfield);

        /*
         * 设置d4组件的边框; BorderFactory.createTitledBorder(String title)创建一个新标题边框，
         * 使用默认边框（浮雕化）、默认文本位置（位于顶线上）、默认调整 (leading) 以及由当前外观确定的默认字体和文本颜色，并指定了标题文本。
         */
        p4.setBorder(BorderFactory.createTitledBorder("方向"));
        p4.setBounds(150, 80, 180, 70);
        up.setFont(new Font("黑体", Font.ITALIC, 14));
        down.setFont(new Font("黑体", Font.ITALIC, 14));
        p4.add(up);
        p4.add(down);

        con.add(p1);
        con.add(b1);
        con.add(b2);
        con.add(check1);
        con.add(check2);
        con.add(p4);
        dislog.setBounds(200, 200, 460, 220);
        dislog.setResizable(false);// 设置窗体大小不可改变
        dislog.setVisible(true);// 显示窗体
        b1.addActionListener(new ActionListener() {// 查找下一个按钮

            public void actionPerformed(ActionEvent e) {

                String areastr = textarea.getText();// 获取文本区文本
                String fieldstr = textfield.getText();// 获取文本框文本
                String toupparea = areastr.toUpperCase();// 转为大写，用做区分大小写判断方便查找
                String touppfield = fieldstr.toUpperCase();
                String A;// 用做查找的文本域内容
                String B;// 用作查找的文本框内容
                if (check1.isSelected()) {// 区分大小写
                    A = areastr;
                    B = fieldstr;
                } else {// 全部换为大写
                    A = toupparea;
                    B = touppfield;
                }
                int n = textarea.getCaretPosition();// 获取光标的位置
                int m = 0;
                if (up.isSelected()) {// 向上查找
                    if (textarea.getSelectedText() == null) {
                        m = A.lastIndexOf(B, n - 1);
                    } else {
                        m = A.lastIndexOf(B, n - textfield.getText().length() - 1);
                    }
                    if (m != -1) {
                        textarea.setCaretPosition(m);
                        textarea.select(m, m + textfield.getText().length());
                    } else {
                        if (check2.isSelected()) {// 如果循环
                            m = A.lastIndexOf(B);// 从后面开始找
                            if (m != -1) {
                                textarea.setCaretPosition(m);
                                textarea.select(m, m + textfield.getText().length());
                            } else {
                                JOptionPane.showMessageDialog(null, "找不到 “" + textfield.getText() + "“", "查找",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "找不到 “" + textfield.getText() + "“", "查找",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                } else {// 向下查找
                    m = A.indexOf(B, n);
                    if (m != -1) {
                        textarea.setCaretPosition(m + textfield.getText().length());
                        textarea.select(m, m + textfield.getText().length());
                    } else {
                        if (check2.isSelected()) {// 如果循环
                            m = A.indexOf(B);// 从头开始找
                            if (m != -1) {
                                textarea.setCaretPosition(m + textfield.getText().length());
                                textarea.select(m, m + textfield.getText().length());
                            } else {
                                JOptionPane.showMessageDialog(null, "找不到 “" + textfield.getText() + "“", "查找",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "找不到 “" + textfield.getText() + "“", "查找",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        b2.addActionListener(new ActionListener() {// 取消

            public void actionPerformed(ActionEvent e) {
                dislog.dispose();// 销毁窗体
            }
        });

    }

    protected void addJMenum3() {// 格式
        this.item3_1 = new JCheckBoxMenuItem("自动换行(W)", true);// 复选框菜单项组件,设为选中
        this.item3_1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (item3_1.isSelected()) {// 如果选中，则可以自动换行
                    item2_10.setEnabled(false);
                    textarea.setLineWrap(true);
                } else {
                    item2_10.setEnabled(true);
                    textarea.setLineWrap(false);
                }
            }
        });
        item3_1.setMnemonic('W');
        m3.add(item3_1);

        JMenuItem item3_2 = new JMenuItem("字体(F)");
        item3_2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                item3_2Listener(e);
            }
        });
        item3_2.setMnemonic('F');
        m3.add(item3_2);

    }

    /*
     * 字体 方法事件
     */
    private void item3_2Listener(ActionEvent e) {
        JDialog dialog = new JDialog(this, "字体");
        Container c = dialog.getContentPane();// 获得窗体的容器
        dialog.setLayout(null);// 取消布局
        dialog.setBounds(300, 100, 460, 470);
        dialog.setResizable(false);// 设置窗体大小不可改变
        JLabel j1 = new JLabel("字体(F)：");
        j1.setBounds(10, 10, 100, 20);
        j1.setFont(new Font("宋体", Font.ITALIC, 14));
        JTextField fiedl1 = new JTextField(10);
        fiedl1.setBounds(10, 30, 180, 25);
        DefaultListModel model = new DefaultListModel();// 列表模型
        JList list1 = new JList(model);// 列表框
        /*
         * getLocalGraphicsEnvironment()返回本地 GraphicsEnvironment
         * getAvailableFontFamilyNames()返回一个包含此 GraphicsEnvironment 中所有字体系列名称的数组
         */
        String[] fontName = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String x : fontName) {
            model.addElement(x);
        }
        fiedl1.setText(form);
        list1.setSelectedIndex(101);// 默认选中 宋体

        JScrollPane scr1 = new JScrollPane();// 滚动面板
        scr1.setViewportView(list1);
        scr1.setBounds(10, 54, 180, 180);

        JLabel j2 = new JLabel("字形(Y)：");
        j2.setBounds(205, 10, 100, 20);
        j2.setFont(new Font("宋体", Font.ITALIC, 14));
        JTextField fiedl2 = new JTextField(10);
        fiedl2.setBounds(205, 30, 150, 25);
        DefaultListModel model2 = new DefaultListModel();// 列表模型
        model2.addElement("常规");
        model2.addElement("倾斜");
        model2.addElement("粗体");
        model2.addElement("粗偏斜体");
        JList list2 = new JList(model2);// 列表框
        JScrollPane scr2 = new JScrollPane();// 滚动面板
        scr2.setViewportView(list2);
        scr2.setBounds(205, 54, 150, 180);
        fiedl2.setText("常规");
        list2.setSelectedIndex(0);

        JLabel j3 = new JLabel("大小(S)：");
        j3.setBounds(367, 10, 100, 20);
        j3.setFont(new Font("宋体", Font.ITALIC, 14));
        JTextField fiedl3 = new JTextField(10);
        fiedl3.setBounds(367, 30, 70, 25);
        DefaultListModel model3 = new DefaultListModel();// 列表模型

        JList list3 = new JList(model3);// 列表框
        JScrollPane scr3 = new JScrollPane();// 滚动面板
        scr3.setViewportView(list3);
        scr3.setBounds(367, 54, 70, 180);
        // 将字体大小加入到列表框
        for (int i = 8; i <= 12; i++) {
            model3.addElement(String.valueOf(i));// 转为字符串
        }
        for (int i = 14; i <= 28; i += 2) {
            model3.addElement(String.valueOf(i));
        }
        model3.addElement("36");
        model3.addElement("48");
        model3.addElement("72");
        String fontsize[] = { "初号", "小初", "一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五", "六号", "小六", "七号",
                "八号" };
        int n[] = { 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 11, 9, 8, 7, 6, 5 };// 与中文字号相对应的大小
        for (String x : fontsize) {
            model3.addElement(x);
        }
        // 添加列表选择监听事件
        fiedl3.setText("14");
        list3.setSelectedIndex(5);

        // 示例
        JLabel shili = new JLabel("AaBbYyZy");
        shili.setFont(new Font(form, font, size));
        JPanel pane = new JPanel();
        pane.setBounds(200, 250, 200, 80);
        pane.setBorder(BorderFactory.createTitledBorder("示例"));// 设置标题边框
        pane.add(shili, BorderLayout.CENTER);

        /*
         * 确定，取消按钮
         */
        JButton b1 = new JButton("确定");
        b1.setContentAreaFilled(false);
        b1.setBounds(270, 400, 80, 30);
        b1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                form = fiedl1.getText().trim();// 获得字体
                String str = fiedl2.getText().trim();
                // 获得字形
                switch (str) {
                    case "常规":
                        font = Font.PLAIN;
                        break;
                    case "倾斜":
                        font = Font.ITALIC;
                        break;
                    case "粗体":
                        font = Font.BOLD;
                        break;
                    case "粗偏斜体":
                        font = Font.BOLD + Font.ITALIC;
                        break;
                }
                // 获得字体大小
                int len = list3.getLeadSelectionIndex();
                String select3 = (String) list3.getModel().getElementAt(len);
                if (0 <= len && len <= 15) {
                    size = Integer.parseInt(select3);
                } else {
                    size = n[len - 16];
                }
                // 更新示例标签
                shili.setFont(new Font(form, font, size));
                textarea.setFont(new Font(form, font, size));// 设置文本字体
                dialog.dispose();
            }
        });

        JButton b2 = new JButton("取消");
        b2.setContentAreaFilled(false);
        b2.setBounds(360, 400, 80, 30);
        b2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.dispose();// 销毁窗体
            }
        });

        list1.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int select = list1.getLeadSelectionIndex();// 获得选中的下标
                String selectname = (String) model.getElementAt(select);// 获得选中下标对应的名称
                fiedl1.setText(selectname);// 在文本框显示选中的字体
                // 获得字形
                String str = fiedl2.getText().trim();
                int fontstyle = 0;// 字形
                switch (str) {
                    case "常规":
                        fontstyle = Font.PLAIN;
                        break;
                    case "倾斜":
                        fontstyle = Font.ITALIC;
                        break;
                    case "粗体":
                        fontstyle = Font.BOLD;
                        break;
                    case "粗偏斜体":
                        fontstyle = Font.BOLD + Font.ITALIC;
                        break;
                }
                // 获得字体大小
                int len = list3.getLeadSelectionIndex();
                String select3 = (String) list3.getModel().getElementAt(len);
                int fontsize = 0;
                if (0 <= len && len <= 15) {
                    fontsize = Integer.parseInt(select3);
                } else {
                    fontsize = n[len - 16];
                }
                // 更新示例标签
                shili.setFont(new Font(fiedl1.getText(), fontstyle, fontsize));
            }
        });

        list2.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int select = list2.getLeadSelectionIndex();// 获得选中的下标
                String selectname = (String) model2.getElementAt(select);// 获得选中下标对应的名称
                fiedl2.setText(selectname);// 在文本框显示选中的字体

                // 获得字形
                String str = fiedl2.getText().trim();
                int fontstyle = 0;// 字形
                switch (str) {
                    case "常规":
                        fontstyle = Font.PLAIN;
                        break;
                    case "倾斜":
                        fontstyle = Font.ITALIC;
                        break;
                    case "粗体":
                        fontstyle = Font.BOLD;
                        break;
                    case "粗偏斜体":
                        fontstyle = Font.BOLD + Font.ITALIC;
                        break;
                }
                // 获得字体大小
                int len = list3.getLeadSelectionIndex();
                String select3 = (String) list3.getModel().getElementAt(len);
                int fontsize = 0;
                if (0 <= len && len <= 15) {
                    fontsize = Integer.parseInt(select3);
                } else {
                    fontsize = n[len - 16];
                }
                // 更新示例标签
                shili.setFont(new Font(fiedl1.getText(), fontstyle, fontsize));
            }
        });

        list3.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int select = list3.getLeadSelectionIndex();// 获得选中的下标
                String selectname = (String) model3.getElementAt(select);// 获得选中下标对应的名称
                fiedl3.setText(selectname);// 在文本框显示选中的字体

                // 获得字形
                String str = fiedl2.getText().trim();
                int fontstyle = 0;// 字形
                switch (str) {
                    case "常规":
                        fontstyle = Font.PLAIN;
                        break;
                    case "倾斜":
                        fontstyle = Font.ITALIC;
                        break;
                    case "粗体":
                        fontstyle = Font.BOLD;
                        break;
                    case "粗偏斜体":
                        fontstyle = Font.BOLD + Font.ITALIC;
                        break;
                }
                // 获得字体大小
                int len = list3.getLeadSelectionIndex();
                String select3 = (String) list3.getModel().getElementAt(len);
                int fontsize = 0;
                if (0 <= len && len <= 15) {
                    fontsize = Integer.parseInt(select3);
                } else {
                    fontsize = n[len - 16];
                }
                // 更新示例标签
                shili.setFont(new Font(fiedl1.getText(), fontstyle, fontsize));
            }
        });

        c.add(j1);
        c.add(fiedl1);
        c.add(scr1);
        c.add(j2);
        c.add(fiedl2);
        c.add(scr2);
        c.add(j3);
        c.add(fiedl3);
        c.add(scr3);
        c.add(pane);
        c.add(b1);
        c.add(b2);
        dialog.setVisible(true);// 显示窗体
    }

    protected void addJMenum4() {// 查看
        JMenu menu = new JMenu("缩放(Z)");
        menu.setMnemonic('Z');

        JMenuItem pmen1 = new JMenuItem("放大(l)");
        pmen1.setMnemonic('l');
        pmen1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_MASK));// 设置Ctrl + 加号
        pmen1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String name = textarea.getFont().getFontName();// 返回字体外观
                int style = textarea.getFont().getStyle();// 获得字体的样式
                int size = textarea.getFont().getSize();// 获得字体的大小
                textarea.setFont(new Font(name, style, size + 1));// 设置字体大小+1
            }
        });

        JMenuItem pmen2 = new JMenuItem("缩小(O)");
        pmen2.setMnemonic('O');
        pmen2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_MASK));// 设置Ctrl + 减号
        pmen2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String name = textarea.getFont().getFontName();// 返回字体外观
                int style = textarea.getFont().getStyle();// 获得字体的样式
                int size = textarea.getFont().getSize();// 获得字体的大小
                textarea.setFont(new Font(name, style, size - 1));// 设置字体大小-1
            }
        });

        JMenuItem pmen3 = new JMenuItem("恢复默认缩放");
        pmen3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_MASK));// 设置Ctrl + 0
        pmen3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String name = textarea.getFont().getFontName();// 返回字体外观
                int style = textarea.getFont().getStyle();// 获得字体的样式
                int size = textarea.getFont().getSize();// 获得字体的大小
                textarea.setFont(new Font(name, style, 14));// 默认四号(14)
            }
        });

        menu.add(pmen1);
        menu.add(pmen2);
        menu.add(pmen3);

        JCheckBoxMenuItem check = new JCheckBoxMenuItem("状态栏(S)");
        check.setMnemonic('S');
        check.setSelected(true);// 设为选中
        check.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if (check.isSelected()) {
                    status.setVisible(true);
                } else {
                    status.setVisible(false);
                }
            }
        });
        m4.add(menu);
        m4.add(check);
    }

    protected void addJMenum5() {// 帮助
        JMenuItem item5_1 = new JMenuItem("查看帮助(H)");
        item5_1.setMnemonic('H');
        item5_1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // 打开指定网站
                Desktop desk = Desktop.getDesktop();
                try {
                    URI uri = new URI(
                            "https://cn.bing.com/search?q=%E8%8E" + "%B7%E5%8F%96%E6%9C%89%E5%85%B3+windows+10+%E"
                                    + "4%B8%AD%E7%9A%84%E8%AE%B0%E4%BA%8B%E6%9C%AC%E7%"
                                    + "9A%84%E5%B8%AE%E5%8A%A9&filters=guid:%224466414-zh-h"
                                    + "ans-dia%22%20lang:%22zh-hans%22&form=T00032&ocid=HelpPane-BingIA");
                    desk.browse(uri);
                } catch (MalformedURLException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }

            }
        });

        JMenuItem item5_2 = new JMenuItem("发送反馈(F)");
        item5_2.setMnemonic('F');
        item5_2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Point();
            }
        });

        JMenuItem item5_3 = new JMenuItem("关于记事本(A)");
        item5_3.setMnemonic('A');
        item5_3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                about();
            }

        });

        m5.add(item5_1);
        m5.add(item5_2);
        m5.addSeparator();
        m5.add(item5_3);
    }

    private void about() {
        JOptionPane.showMessageDialog(this,
                "*********************************************\n" + " 编写者：小黎 \n"
                        + " 编写时间：2020-07-09                           \n" + "    QQ：3033827669                \n"
                        + " 有些功能未能实现，不足之处希望大家能提出意见，谢谢！  \n" + "*********************************************\n",
                "记事本", JOptionPane.INFORMATION_MESSAGE);

//		JOptionPane.showMessageDialog(this,
//                "*********************************************\n"+
//                " 编写者：小黎 \n"+
//                " 编写时间：2020-07-09                           \n"+
//                "    QQ：3033827669                \n"+
//                " 一些地方借鉴他人，不足之处希望大家能提出意见，谢谢！  \n"+
//                "*********************************************\n",
//                "记事本",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main m = new Main();
                m.setVisible(true);
            }
        });
    }
}

package 可视化的汉罗塔;

import javax.swing.*;
import java.awt.*;

class MyThread extends JPanel implements Runnable {
    final static int Width = 670;
    final static int height = 350;
    static Rect[] rect;
    static ZhuZi zhuzi = null;
    int num;                    //计算移动总次数

    MyThread(int n) {
        zhuzi = new ZhuZi(n);
        rect = new Rect[zhuzi.getN()];
        this.setSize(Width, height);
        this.setLocation(10, 50);
        this.setLayout(null);
        this.setBackground(Color.white);
        ZuoBiaoZhuanHuan(zhuzi);
        this.repaint();
        this.num = 0;
        if (zhuzi.getN() >= 15) {
            this.num = (int) Math.pow(2, zhuzi.getN()) - 1;
        }
    }

    private static void ZuoBiaoZhuanHuan(ZhuZi zhuzi)//讲柱子转换为坐标
    {
        int a[] = zhuzi.getA();
        int b[] = zhuzi.getB();
        int c[] = zhuzi.getC();
        int k = 0;
        for (int i = 0; i < zhuzi.getN(); i++) {
            if (a[i] != 0) {
                Rect jx = new Rect(Width / 4 - Width / 8 / zhuzi.getN() * a[i], height - 40 - 20 * (zhuzi.getN() - i - 1), Width / 8 / zhuzi.getN() * a[i] * 2, 20);
                rect[k++] = jx;
            }
            if (b[i] != 0) {
                Rect jx = new Rect(Width / 4 * 2 - Width / 8 / zhuzi.getN() * b[i], height - 40 - 20 * (zhuzi.getN() - i - 1), Width / 8 / zhuzi.getN() * b[i] * 2, 20);
                rect[k++] = jx;
            }
            if (c[i] != 0) {
                Rect jx = new Rect(Width / 4 * 3 - Width / 8 / zhuzi.getN() * c[i], height - 40 - 20 * (zhuzi.getN() - i - 1), Width / 8 / zhuzi.getN() * c[i] * 2, 20);
                rect[k++] = jx;
            }
        }

    }

    private void hanoi(int n, int from, int buffer, int to) {
        if (n == 1)
            this.Move(from, to);
        else {
            hanoi(n - 1, from, to, buffer);
            hanoi(1, from, buffer, to);
            hanoi(n - 1, buffer, from, to);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.CYAN);
        g.fillRect(0, Width - 20, Width, 20);
        g.fillRect(Width / 4 - 5, 0, 10, Width - 20);
        g.fillRect(Width / 2 - 5, 0, 10, Width - 20);
        g.fillRect(Width / 4 * 3 - 5, 0, 10, Width - 20);//画柱子
        g.setColor(Color.green);

        for (int i = 0; i < zhuzi.getN(); i++) {
            g.fillRect(rect[i].x, rect[i].y, rect[i].width, rect[i].height);
            g.setColor(Color.black);
            g.drawRect(rect[i].x, rect[i].y, rect[i].width, rect[i].height);
            g.setColor(Color.pink);
        }
    }

    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (num == 0) {
            this.hanoi(zhuzi.getN(), 0, 1, 2);
            JOptionPane.showMessageDialog(null, "完成移动.共移动" + this.num + "次");
        } else {
              JOptionPane.showMessageDialog(null,"盘数过多，所需运行时间过长，不便于展示，根据公式：可知移动次数为："+this.num);
        }
    }


    public void Move(int loc1, int loc2) {
        {
            int x;//被移动的色块
            this.num++;
            {
                if (loc1 == 0) {
                    x = zhuzi.getA()[zhuzi.top_A + 1];
                    zhuzi.getA()[zhuzi.top_A + 1] = 0;
                    zhuzi.top_A++;
                    ZuoBiaoZhuanHuan(zhuzi);
                    Rect jx = new Rect(Width / 4 - Width / 8 / zhuzi.getN() * x, 0, Width / 8 / zhuzi.getN() * x * 2, 20);
                    rect[zhuzi.getN() - 1] = jx;
                    this.repaint();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (loc2 == 1) {
                        jx = new Rect(Width / 4 * 2 - Width / 8 / zhuzi.getN() * x, 0, Width / 8 / zhuzi.getN() * x * 2, 20);
                        rect[zhuzi.getN() - 1] = jx;
                        this.repaint();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        zhuzi.getB()[zhuzi.top_B] = x;
                        zhuzi.top_B--;
                    } else {
                        jx = new Rect(Width / 4 * 3 - Width / 8 / zhuzi.getN() * x, 0, Width / 8 / zhuzi.getN() * x * 2, 20);
                        rect[zhuzi.getN() - 1] = jx;
                        this.repaint();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        zhuzi.getC()[zhuzi.top_C] = x;
                        zhuzi.top_C--;
                    }
                    ZuoBiaoZhuanHuan(zhuzi);
                    this.repaint();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else if (loc1 == 1) {
                    x = zhuzi.getB()[zhuzi.top_B + 1];
                    zhuzi.getB()[zhuzi.top_B + 1] = 0;
                    zhuzi.top_B++;
                    ZuoBiaoZhuanHuan(zhuzi);
                    Rect jx = new Rect(Width / 4 * 2 - Width / 8 / zhuzi.getN() * x, 0, Width / 8 / zhuzi.getN() * x * 2, 20);
                    rect[zhuzi.getN() - 1] = jx;
                    this.repaint();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (loc2 == 0) {
                        jx = new Rect(Width / 4 - Width / 8 / zhuzi.getN() * x, 0, Width / 8 / zhuzi.getN() * x * 2, 20);
                        rect[zhuzi.getN() - 1] = jx;
                        this.repaint();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        zhuzi.getA()[zhuzi.top_A] = x;
                        zhuzi.top_A--;
                    } else {
                        jx = new Rect(Width / 4 * 3 - Width / 8 / zhuzi.getN() * x, 0, Width / 8 / zhuzi.getN() * x * 2, 20);
                        rect[zhuzi.getN() - 1] = jx;
                        this.repaint();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        zhuzi.getC()[zhuzi.top_C] = x;
                        zhuzi.top_C--;
                    }
                    ZuoBiaoZhuanHuan(zhuzi);
                    this.repaint();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    x = zhuzi.getC()[zhuzi.top_C + 1];
                    zhuzi.getC()[zhuzi.top_C + 1] = 0;
                    zhuzi.top_C++;
                    ZuoBiaoZhuanHuan(zhuzi);
                    Rect jx = new Rect(Width / 4 * 3 - Width / 8 / zhuzi.getN() * x, 0, Width / 8 / zhuzi.getN() * x * 2, 20);
                    rect[zhuzi.getN() - 1] = jx;
                    this.repaint();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (loc2 == 1) {
                        jx = new Rect(Width / 4 * 2 - Width / 8 / zhuzi.getN() * x, 0, Width / 8 / zhuzi.getN() * x * 2, 20);
                        rect[zhuzi.getN() - 1] = jx;
                        this.repaint();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        zhuzi.getB()[zhuzi.top_B] = x;
                        zhuzi.top_B--;
                    } else {
                        jx = new Rect(Width / 4 - Width / 8 / zhuzi.getN() * x, 0, Width / 8 / zhuzi.getN() * x * 2, 20);
                        rect[zhuzi.getN() - 1] = jx;
                        this.repaint();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        zhuzi.getA()[zhuzi.top_A] = x;
                        zhuzi.top_A--;
                    }
                    ZuoBiaoZhuanHuan(zhuzi);
                    this.repaint();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }

    }

}

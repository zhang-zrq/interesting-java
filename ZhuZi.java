package 可视化的汉罗塔;

public class ZhuZi {
    private int A[];
    int top_A;
    private int B[];
    int top_B;
    private int C[];
    int top_C;
    private int n;

    ZhuZi(int n) {
        this.n = n;
        A = new int[n];
        B = new int[n];
        C = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = i + 1;
            top_A = -1;
            top_B = n - 1;
            top_C = n - 1;
        }
    }

    public int[] getA() {
        return A;
    }

    public int[] getB() {
        return B;
    }

    public int[] getC() {
        return C;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}

package dp;

/**
 * 斐波那契数列
 */
public class Fib {

    public static void main(String[] args) {
        System.out.println(fib(6));
    }

    private static int fib(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        int pre = 1;
        int curr = 1;
        int next = 0;
        for (int i = 3; i <= n; i++) {
            next = pre + curr;
            pre = curr;
            curr = next;
        }
        return next;
    }

}

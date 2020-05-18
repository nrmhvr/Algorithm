import java.util.Scanner;

public class PoweringNumber {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.print("a : ");
		int a = scan.nextInt();
		System.out.print("n : ");
		int n = scan.nextInt();

		System.out.println("result : " + poweringNumber(a, n));
	}

	public static int poweringNumber(int a, int n) {
		if (n == 0) {
			return 0;
		} else if (n == 1) {
			return a;
		} else if (n % 2 != 0) { // odd
			return a * poweringNumber(a, (n - 1) / 2) * poweringNumber(a, (n - 1) / 2);
		} else if (n % 2 == 0) { // even
			return poweringNumber(a, n / 2) * poweringNumber(a, n / 2);
		}
		return -1;
	}
}

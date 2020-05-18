import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Fibonacci {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("방법");
		System.out.println("1 : Recursion");
		System.out.println("2 : Array");
		System.out.println("3 : Recursive squaring\n");

		// input
		System.out.print("방법  = ");
		int what = scan.nextInt();
		System.out.print("n = ");
		int input = scan.nextInt();
		BigInteger n = BigInteger.valueOf(input);
		System.out.println();

		// 0 ~ n 까지 fibonacci 돌리기
		for (int i = 0; i <= n.intValue(); i++) {
			System.out.print("f<" + i + "> = ");
			long startTime = System.nanoTime(); // 시간 측정 시작
			if (what == 1) {
				System.out.print(Recursion(BigInteger.valueOf(i)));
			} else if (what == 2) {
				System.out.print(Array(BigInteger.valueOf(i)));
			} else if (what == 3) {
				System.out.print(RecursiveSquaring(BigInteger.valueOf(i)));
			}
			long endTime = System.nanoTime(); // 시간 측정 끝
			// 걸린 시간 출력
			System.out.print(String.format("%15s", "time : ")
					+ String.format("%.12f", new BigDecimal((endTime - startTime) / 1000000000.0)) + " sec");
			System.out.println();
		}
	}

	public static BigInteger Recursion(BigInteger n) {
		if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) { // 0 일때 0, 1 일때 1 반환
			return n;
		}
		// 전 + 전전
		return Recursion(n.subtract(BigInteger.ONE)).add(Recursion(n.subtract(BigInteger.valueOf(2))));
	}

	public static BigInteger Array(BigInteger n) {
		if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) { // 0 일때 0, 1 일때 1 반환
			return n;
		}
		// 배열 생성
		BigInteger[] array = new BigInteger[n.intValue() + 1];
		array[0] = BigInteger.ZERO;
		array[1] = BigInteger.ONE;
		for (int i = 2; i <= n.intValue(); i++) {
			// [index-1] + [index-2] 를 [index] 에 저장, 반복
			array[i] = array[i - 1].add(array[i - 2]);
		}
		// 마지막 index 반환
		return array[n.intValue()];
	}

	public static BigInteger RecursiveSquaring(BigInteger n) {
		if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) { // 0 일때 0, 1 일때 1 반환
			return n;
		}

		// 1 1
		// 1 0
		BigInteger[][] a = new BigInteger[][] { { BigInteger.ONE, BigInteger.ONE },
				{ BigInteger.ONE, BigInteger.ZERO } };

		return pow(a, n)[0][1]; // 계산하고 [0][1]의 값( = f(n) ) 반환
	}

	public static BigInteger[][] pow(BigInteger[][] a, BigInteger n) {
		if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) { // 0,1 일때 그대로 반환
			return a;
		}

		if (n.intValue() % 2 != 0) { // odd
			// (n-1)/2
			BigInteger t = (n.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
			// a^((n-1)/2) * a^((n-1)/2) * a
			return mul(pow(mul(a, a), t), a);
		} else { // even
			// a^(n/2) * a^(n/2)
			return pow(mul(a, a), n.divide(BigInteger.valueOf(2)));
		}
	}

	public static BigInteger[][] mul(BigInteger[][] a, BigInteger[][] b) {
		// 결과 저장용
		BigInteger[][] c = new BigInteger[][] { { BigInteger.ZERO, BigInteger.ZERO },
				{ BigInteger.ZERO, BigInteger.ZERO } };

		// 행렬 곱
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				for (int k = 0; k < a.length; k++) {
					c[i][j] = c[i][j].add(a[i][k].multiply(b[k][j]));
				}
			}
		}
		return c;
	}

}

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Knapsack {
	static int[] value = null; // value
	static int[] weight = null; // weight
	static int[][] M = null; // OPT
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		// read
		try (FileInputStream fstream = new FileInputStream("data10_knapsack.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			// , 단위로 자르기
			String[] stokens = s.split(",|\n");
			value = new int[stokens.length / 3];
			weight = new int[stokens.length / 3];
			// String 배열을 int 배열로 바꿈
			for (int i = 0, j = 0; i < stokens.length / 3; i++, j += 3) {
				value[i] = Integer.parseInt(stokens[j + 1].trim());
				weight[i] = Integer.parseInt(stokens[j + 2].trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.print("배낭의 사이즈를 입력하세요(0~50) : ");
		int size = scan.nextInt();
		if (size < 0 || size > 50) {
			System.out.println("사이즈가 범위에 속하지 않습니다.");
		} else {
			knapsack(size, value, weight);
			printOPT(size);
		}
	}

	private static void knapsack(int n, int[] value, int[] weight) {
		M = new int[value.length + 1][n + 1];

		// i = 0 이면 0
		for (int w = 0; w < n; w++) {
			M[0][w] = 0;
		}

		// OPT 배열 채우기
		// i, w 를 반복 한번 더 하는 대신 value, weight 는 -1
		for (int i = 1; i < value.length + 1; i++) {
			for (int w = 1; w < n + 1; w++) {
				if (weight[i - 1] > w) {
					M[i][w] = M[i - 1][w];
				} else {
					M[i][w] = Math.max(M[i - 1][w], value[i - 1] + M[i - 1][w - weight[i - 1]]);
				}
			}
		}
	}

	private static void printOPT(int n) {
		// M 출력
		for (int i = 0; i < M.length; i++) {
			for (int j = 0; j < n + 1; j++) {
				System.out.print(String.format("%5d", M[i][j]));
			}
			System.out.println();
		}

		int i = M.length - 1;
		int j = n;
		ArrayList<Integer> item = new ArrayList<Integer>();

		System.out.println("max : " + M[i][j]);

		// value 의 총합이 최대가 되는 item 찾기
		for (int p = i - 1; p >= 0; p--) {
			if (M[i][j] == M[i - 1][j]) {
				i--;
			} else {
				item.add(p + 1);
				i--;
				j -= weight[p];
			}
		}

		// item 출력
		System.out.print("item : ");
		for (int q = item.size() - 1; q >= 0; q--) {
			System.out.print(item.get(q) + " ");
		}
	}
}

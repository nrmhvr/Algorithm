import java.io.FileInputStream;
import java.io.IOException;

public class MatrixChainMultiplication {
	static int[][] M = null; // OPT

	public static void main(String[] args) {
		int[] matrix = null;

		// read
		try (FileInputStream fstream = new FileInputStream("data11_matrix_chain.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			// , 단위로 자르기
			String[] stokens = s.split(",|\n");
			matrix = new int[stokens.length / 2 + 1];
			// String 배열을 int 배열로 바꿈
			matrix[0] = Integer.parseInt(stokens[0].trim());
			for (int i = 1, j = 1; j < matrix.length; i += 2, j++) {
				matrix[j] = Integer.parseInt(stokens[i].trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		matrixChainOrder(matrix);

	}

	private static void matrixChainOrder(int[] p) {
		int n = p.length;
		M = new int[n][n];

		// i = j 면 0
		for (int i = 1; i < n; i++) {
			M[i][i] = 0;
		}

		for (int l = 2; l < n; l++) {
			for (int i = 1; i < n - l + 1; i++) {
				int j = i + l - 1;
				M[i][j] = Integer.MAX_VALUE;
				for (int k = i; k <= j - 1; k++) {
					int q = M[i][k] + M[k + 1][j] + p[i - 1] * p[k] * p[j];
					if (q < M[i][j]) {
						M[i][j] = q;
					}
				}
			}
		}

		// 출력
		for (int i = 1; i < M.length; i++) {
			for (int j = 1; j < M[i].length; j++) {
				System.out.print(String.format("%8d", M[i][j]));
			}
			System.out.println();
		}
	}
}

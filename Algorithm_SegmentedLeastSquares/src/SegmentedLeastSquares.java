import java.io.FileInputStream;
import java.io.IOException;

public class SegmentedLeastSquares {
	static String[] stokens = null; // txt파일 읽어서 , 단위로 저장
	static double[][] points = null; // 점 쌍
	static int points_num; // 점 개수
	static int c; // cost
	static double[][] A = null; // a
	static double[][] B = null; // b
	static double[][] e = null; // SSE
	static double[] M = null; // OPT
	static int[] divided = null; // 나눠지는 구간
	static int[] endIndexes = null; // 나눠지는 구간의 end index

	public static void main(String[] args) {

		// read
		try (FileInputStream fstream = new FileInputStream("data08.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			// , 단위로 자르기
			stokens = s.split(",");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Cost of the optimal solution : " + String.format("%.6f", segmentedLeastSquares()));
		System.out.println();
		optimalSolution();
	}

	public static double segmentedLeastSquares() {
		init();

		for (int j = 0; j < points_num; j++) {
			for (int i = 0; i < j; i++) {
				computeSSE(i, j);
			}
		}
		for (int j = 0; j < points_num; j++) {
			computeOPT(j);
		}
		return M[M.length - 1];
	}

	private static void init() {
		points_num = Integer.parseInt(stokens[0]);
		c = Integer.parseInt(stokens[stokens.length - 1]);
		A = new double[points_num][points_num];
		B = new double[points_num][points_num];
		e = new double[points_num][points_num];
		M = new double[points_num];
		divided = new int[points_num];
		endIndexes = new int[points_num];
		M[0] = 0;

		points = new double[points_num][2];
		for (int i = 1, j = 0; i < stokens.length - 2; i += 2, j++) {
			double x = Double.parseDouble(stokens[i]);
			double y = Double.parseDouble(stokens[i + 1]);
			points[j][0] = x;
			points[j][1] = y;
		}
	}

	private static void computeSSE(int i, int j) {
		double xsum = 0, ysum = 0, xysum = 0, xxsum = 0, sse = 0;
		double n = j - i + 1; // a, b 구할때 쓸 n

		// a, b 구할때 쓸 sum 계산하기
		for (int k = i; k <= j; k++) {
			xsum += points[k][0];
			ysum += points[k][1];
			xysum += points[k][0] * points[k][1];
			xxsum += points[k][0] * points[k][0];
		}

		// a, b 구하기
		double a = ((n * xysum) - (xsum * ysum)) / ((n * xxsum) - (xsum * xsum));
		double b = (ysum - (a * xsum)) / n;

		// SSE 구하기
		for (int k = i; k <= j; k++) {
			sse += Math.pow(a * points[k][0] + b - points[k][1], 2);
		}

		// 저장
		A[i][j] = a;
		B[i][j] = b;
		e[i][j] = sse;
	}

	private static void computeOPT(int j) {
		double opt = 0;
		int index = 0;
		double min = Double.POSITIVE_INFINITY;

		// OPT 구하기
		for (int i = 0; i <= j; i++) {
			if (i - 1 >= 0) {
				opt = M[i - 1];
			}
			double temp = e[i][j] + c + opt;

			// 최소값 찾기
			if (temp < min) {
				min = temp;
				index = i;
			}
		}

		// 저장
		M[j] = min;
		divided[j] = index;
	}

	public static void optimalSolution() {
		System.out.println("An optimal solution : ");

		// 나눠지는 구간 찾기
		for (int i = points_num - 1; i > 0; i--) {
			int j = divided[i];

			if (i == j) {
				endIndexes[--j] = i;
			} else {
				endIndexes[j] = i;
			}
			i = j;
		}

		// 나눠지는 구간 출력
		for (int start = 0; start < points_num; start++) {
			int end = endIndexes[start];
			if (end != 0) {
				System.out.println("[Segment " + (start + 1) + " - " + (end + 1) + "] : y = "
						+ String.format("%.6f", A[start][end]) + " * x + " + String.format("%.6f", B[start][end])
						+ " // square error : " + String.format("%.6f", e[start][end]));
			}
		}
	}
}
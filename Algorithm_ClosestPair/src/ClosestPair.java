import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class ClosestPair {

	public static void main(String[] args) {
		double[] dtokens = null;

		// read
		try (FileInputStream fstream = new FileInputStream("data04_closest.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			System.out.println("Intput Data : ");
			System.out.println(s);
			// 자르기
			String[] stokens = s.split(",|\n");
			dtokens = new double[stokens.length];
			// String 배열을 double 배열로 바꿈
			for (int i = 0; i < stokens.length; i++) {
				dtokens[i] = Double.parseDouble(stokens[i].trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sort
		// x 좌표 오름차순으로 정렬
		for (int i = 0; i < dtokens.length; i += 2) {
			for (int j = 0; j < dtokens.length - i - 2; j += 2) {
				if (dtokens[j] > dtokens[j + 2]) {
					// swap
					double tempx = dtokens[j], tempy = dtokens[j + 1];
					dtokens[j] = dtokens[j + 2];
					dtokens[j + 2] = tempx;
					dtokens[j + 1] = dtokens[j + 3];
					dtokens[j + 3] = tempy;
				}
			}
		}
		// find, print
		System.out.println("Output Data : " + String.format("%.3f", closestPair(dtokens, 0, dtokens.length - 1)));
	}

	public static double closestPair(double[] point, int i, int j) {
		int num = (j - i + 1) / 2; // point 개수
		if (num <= 3) { // 3개 이하면 Brute Force 사용
			return bruteForce(point, i, j, num);

		}
		int L = (j - i) / 2; // n/2 지점 index
		double m1 = closestPair(point, i, L);
		double m2 = closestPair(point, L, j);
		double m = Math.min(m1, m2); // m1 m2 중 더 작은 값

		for (int p = 0; p < point.length; p += 2) {
			// 범위 초과한 점 표시해놓기
			if (point[p] > m + m) {
				point[p] = Double.NaN;
				point[p + 1] = Double.NaN;
			}
			// y 좌표 오름차순으로 정렬
			for (int q = 0; q < point.length - p - 2; q += 2) {
				if (point[q + 1] > point[q + 3]) {
					// swap
					double tempx = point[q], tempy = point[q + 1];
					point[q] = point[q + 2];
					point[q + 2] = tempx;
					point[q + 1] = point[q + 3];
					point[q + 3] = tempy;
				}
			}
		}

		// 배열에서 범위 이외의 것 삭제
		double[] t = new double[point.length];
		int q = -1;
		for (int p = 0; p < point.length; p++) {
			if (!Double.isNaN(point[p])) {
				t[++q] = point[p];
			}
		}
		point = Arrays.copyOf(t, q + 1); // window 에 포함되는 점 배열

		// window 내부의 최단거리 구하기
		for (int a = 0; a < point.length; a += 2) {
			for (int b = 0; b < point.length - a - 2; b += 2) {
				if ((point[b + 3] - point[b + 1]) < m) { // 두 점의 y좌표 거리가 m 보다 작으면 m 갱신
					m = Math.sqrt(Math.pow((point[b + 2] - point[b]), 2) + Math.pow((point[b + 3] - point[b + 1]), 2));
				}
			}
		}
		return m;
	}

	public static double bruteForce(double[] point, int i, int j, int L) {
		double[] distance; // 점 사이 거리 저장용
		// 점 2개
		if (L == 2) {
			distance = new double[1];
			distance[0] = Math
					.sqrt(Math.pow((point[i + 2] - point[i]), 2) + Math.pow((point[i + 3] - point[i + 1]), 2));

			return distance[0];
		}

		// 점 3개
		distance = new double[3];
		distance[0] = Math.sqrt(Math.pow((point[i + 2] - point[i]), 2) + Math.pow((point[i + 3] - point[i + 1]), 2));
		distance[1] = Math
				.sqrt(Math.pow((point[i + 4] - point[i + 2]), 2) + Math.pow((point[i + 5] - point[i + 3]), 2));
		distance[2] = Math.sqrt(Math.pow((point[i + 4] - point[i]), 2) + Math.pow((point[i + 5] - point[i + 1]), 2));

		// 제일 작은 값 반환
		if (distance[0] < distance[1] && distance[0] < distance[2]) {
			return distance[0];
		} else {
			if (distance[1] < distance[2]) {
				return distance[1];
			}
			return distance[2];
		}
	}

}

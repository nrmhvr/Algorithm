import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class QuickSort {
	static Random rand = new Random();

	public static void main(String[] args) {
		int[] intData = null;
		int[] intDataForRandom = null;

		// read
		try (FileInputStream fstream = new FileInputStream("data06.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			// , 단위로 자르기
			String[] stokens = s.split(",|\n");
			intData = new int[stokens.length];
			intDataForRandom = new int[stokens.length];
			// String 배열을 int 배열로 바꿈
			for (int i = 0; i < stokens.length; i++) {
				intData[i] = Integer.parseInt(stokens[i]);
				intDataForRandom[i] = Integer.parseInt(stokens[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// write
		try (FileWriter fw = new FileWriter("data6_quick.txt");) {
			quickSort(intData, 0, intData.length - 1);
			fw.write(forWrite(intData));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// write
		try (FileWriter fw = new FileWriter("data6_quickRandom.txt");) {
			quickSort_withRandom(intDataForRandom, 0, intDataForRandom.length - 1);
			fw.write(forWrite(intDataForRandom));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int partition(int[] A, int p, int r) {
		int x = A[r]; // pivot 임시 지정
		int i = p - 1;
		// 정렬
		for (int j = p; j < r; j++) {
			if (A[j] <= x) {
				i = i + 1;
				swap(A, i, j);
			}
		}
		i = i + 1;
		swap(A, i, r);
		return i; // pivot 위치
	}

	public static int randomizedPartition(int[] A, int p, int r) {
		int i = rand.nextInt(r - p) + p; // 랜덤 원소
		swap(A, i, r); // 랜덤원소가 pivot 이 되도록 swap 해줌
		return partition(A, p, r);
	}

	public static void quickSort(int[] A, int p, int r) {
		if (p < r) {
			int q = partition(A, p, r);
			quickSort(A, p, q - 1); // 왼
			quickSort(A, q + 1, r); // 오
		}
	}

	public static void quickSort_withRandom(int[] A, int p, int r) {
		if (p < r) {
			int q = randomizedPartition(A, p, r);
			quickSort_withRandom(A, p, q - 1); // 왼
			quickSort_withRandom(A, q + 1, r); // 오
		}
	}

	private static void swap(int[] A, int a, int b) {
		int swap = A[a];
		A[a] = A[b];
		A[b] = swap;
	}

	private static String forWrite(int[] tokens) {
		// , 넣어서 하나의 String 으로 합침
		String sorted = "";
		for (int i = 0; i < tokens.length; i++) {
			sorted += tokens[i];
			if (i < tokens.length - 1) {
				sorted += ",";
			}
		}
		return sorted;
	}
}

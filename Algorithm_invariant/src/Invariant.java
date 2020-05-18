import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Invariant {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		int[] aData = null;
		int[] bData = null;

		// read
		try (FileInputStream fstream = new FileInputStream("data07_a.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			// , 단위로 자르기
			String[] stokens = s.split(", ");
			aData = new int[stokens.length];
			// String 배열을 int 배열로 바꿈
			for (int i = 0; i < stokens.length; i++) {
				aData[i] = Integer.parseInt(stokens[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// read
		try (FileInputStream fstream = new FileInputStream("data07_b.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			// , 단위로 자르기
			String[] stokens = s.split(", ");
			bData = new int[stokens.length];
			// String 배열을 int 배열로 바꿈
			for (int i = 0; i < stokens.length; i++) {
				bData[i] = Integer.parseInt(stokens[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.print("input x = ");
		int x = scan.nextInt();

//		// BinarySearch 로 x 찾기
		int w = findX_BinarySearch(aData, x, 0, aData.length);
		if (w < 0) {
			System.out.println("BinarySearch : x is not exist in data07_a.");
		} else {
			System.out.println("BinarySearch : " + x + " is in " + w + "th index in data07_a.");
		}
		w = findX_BinarySearch(bData, x, 0, bData.length);
		if (w < 0) {
			System.out.println("BinarySearch : x is not exist in data07_b.");
		} else {
			System.out.println("BinarySearch : " + x + " is in " + w + "th index in data07_b.");
		}

		System.out.println();
		System.out.println("find Nth at data07_a + data07_b : " + findN(aData, bData));
	}

	public static int findX_BinarySearch(int[] data, int x, int p, int q) {
		while (p < q) {
			int middle = (p + q) / 2;
			if (data[middle] > x) {
				q = middle; // p ~ middle
			} else if (data[middle] < x) {
				p = middle + 1; // middle+1 ~ q
			} else {
				return middle; // 찾음
			}
		}
		// x 없을 때
		return -1;
	}

	public static int findN(int[] A, int[] B) {
		int a = 0; // A 처음
		int b = A.length - 1; // A 마지막
		int p = 0; // B 처음
		int q = B.length - 1; // B 마지막
		int i = (b - a) / 2; // A 의 중간 index
		int j = (q - p) / 2; // B 의 중간 index

		while (b - a > 1 || q - p > 1) { // 배열이 3개 이상이면 반복
			if (A[i] < B[j]) { // A[i] < B[j] 이면 A 는 중간 ~ 뒤, B 는 앞 ~ 중간로 범위조정
				a = i;
				q = p + (b - i);
			} else { // A[i] > B[j] 이면 A 는 앞 ~ 중간, B 는 중간 ~ 뒤로 범위조정
				b = a + (q - j);
				p = j;
			}
			// 범위 사이의 중간 index 계산
			i = a + ((b - a) / 2);
			j = p + ((q - p) / 2);
		}

		// 배열의 원소가 2개면 Loop 나옴, 총 4개 원소 중 중간값을 반환
		if (A[a] > B[p] && A[a] < B[q]) {
			return A[a];
		}
		if (A[a] < B[p] && A[b] < B[p]) {
			return A[b];
		}
		if (A[a] < B[p] && A[b] > B[p]) {
			return B[p];
		}
		return B[q];
	}
}

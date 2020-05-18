import java.io.FileInputStream;
import java.io.IOException;

public class Inversion {
	static int inversion_count = 0; // inversion 횟수 세는 용

	public static void main(String[] args) {
		int[] itokens = null;

		// read
		try (FileInputStream fstream = new FileInputStream("data04_inversion.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			System.out.println("Input Data : " + s);
			// , 단위로 자르기
			String[] stokens = s.split(",");
			itokens = new int[stokens.length];
			// String 배열을 int 배열로 바꿈
			for (int i = 0; i < stokens.length; i++) {
				itokens[i] = Integer.parseInt(stokens[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// print
		mergeSort(itokens, 0, itokens.length - 1);
		System.out.println("Output Data : " + inversion_count);
	}

	// divide
	public static void mergeSort(int[] tokens, int p, int r) {
		// index num : 첫번째 p, 중간 q, 마지막 r
		int q = (p + r) / 2;

		if (p == r) { // 배열 크기가 1 일때
			return;
		}
		// 배열크기 2 이상이면 둘로 나눔
		// index num : 0 ~ 2/n 까지 배열
		mergeSort(tokens, p, q);
		// index num : 2/n+1 ~ n 까지 배열
		mergeSort(tokens, q + 1, r);
		merge(tokens, p, q, r);
	}

	// conquer & combine
	public static void merge(int[] tokens, int p, int q, int r) {
		int i = p; // 앞배열의 iterator
		int j = q + 1; // 뒷배열의 iterator
		int k = p; // 임시 배열의 iterator
		int temp[] = new int[tokens.length]; // 임시저장배열

		while (i <= q && j <= r) {
			// 두 배열의 원소 중 더 작은 값을 임시 배열에 저장
			// 저장에 사용된 배열의 iterator 는 1 증가
			if (tokens[i] < tokens[j]) {
				temp[k++] = tokens[i++];
			} else {
				inversion_count += q + 1 - i; // inversion 계산
				temp[k++] = tokens[j++];
			}
		}

		while (i <= q) {
			temp[k++] = tokens[i++];
		}
		while (j <= r) {
			temp[k++] = tokens[j++];
		}

		// 임시저장배열 -> 원래 배열로 옮기기
		for (int index = p; index < k; index++) {
			tokens[index] = temp[index];
		}
	}
}

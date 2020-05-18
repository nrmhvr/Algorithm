import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class InsertionSort {

	public static void main(String[] args) {
		int[] itokens = null;

		// read
		try (FileInputStream fstream = new FileInputStream("data02.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
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

		// write
		try (FileWriter fw = new FileWriter("hw01_01_201701976_insertion.txt");) {
			fw.write(insertionSort(itokens, itokens.length));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String insertionSort(int[] tokens, int num) {
		int key, j;
		// insertion sort
		for (int i = 2; i < num; i++) {
			key = tokens[i]; // 기준값 key
			// key 의 앞에 값, 비교할 값
			j = i - 1;

			while (j > 0 && tokens[j] > key) {
				// 앞에 값이 더 크면 오른쪽으로 밀기
				tokens[j + 1] = tokens[j];
				j = j - 1;
			}
			// 값 저장
			tokens[j + 1] = key;
		}
		// 문자열로 반환
		return forWrite(tokens);
	}

	public static String forWrite(int[] tokens) {
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

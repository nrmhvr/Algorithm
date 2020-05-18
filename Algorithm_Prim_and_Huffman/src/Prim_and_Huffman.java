import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

class Node {
	private int key;
	private int value;

	// Node 생성자
	public Node(int key, int value) {
		this.key = key;
		this.value = value;
	}

	// getter
	public int getKey() {
		return this.key;
	}

	public int getValue() {
		return this.value;
	}

	// setter
	public void setKey(int key) {
		this.key = key;
	}

	public void setValue(int value) {
		this.value = value;
	}
}

public class Prim_and_Huffman {
	static String[] txt = null; // txt파일 읽기용
	static int num = 0; // 정점 개수
	static ArrayList<Node> Q = null; // 우선순위 큐
	static ArrayList<String> name = null;
	static int[][] w = null; // weight
	static int[] key = null;

	public static void main(String[] args) {
		// read
		try (FileInputStream fstream = new FileInputStream("data13_prim.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			txt = s.split("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		init();
		prim();
	}

	private static void init() {
		String[] existG = txt[0].split(",|\n");
		num = existG.length;
		Q = new ArrayList<>();
		name = new ArrayList<String>();
		w = new int[num][num];
		key = new int[num];

		for (int i = 0; i < num; i++) {
			String a = existG[i].replace("\\n", "");
			name.add(a);
			System.out.println(i + "=" + a);
		}

		// w 초기화
		for (int i = 1; i < txt.length; i++) {
			String[] one = txt[i].split(",");
			int start = name.indexOf(one[0].trim());
			int end = name.indexOf(one[1].trim());
//			int weight = Integer.MAX_VALUE;
			int weight = Integer.parseInt(one[2].trim());
//			if (one.length == 3) {
//				weight = Integer.parseInt(one[2].trim());
//			}
			System.out.println(end);
			w[start][end] = weight;
			System.out.println("w[" + start + "][" + end +"]=" + weight);
		}

		// path 없으면 무한대
		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i].length; j++) {
				
				if (w[i][j] == 0) {
					w[i][j] = Integer.MAX_VALUE;
				}
			}
		}

		// Q 초기화
		Q.add(new Node(1, 0));
		for (int i = 1; i < num; i++) {
			Q.add(new Node(i + 1, Integer.MAX_VALUE));
		}
	}

	private static void prim() {
//		System.out.println("dijkstra's algorithm으로 계산한 결과는 다음과 같습니다.");
//		for (int i = 0; i < num; i++) {
//			System.out.println("________________________________________________");
//			Node Q_root = extractMin(Q);
//			S.add(Q_root);
//			buildMinHeap(Q);
//			System.out.println("S[" + i + "] : d[" + toASCII(Q_root.getKey()) + "] = " + Q_root.getValue());
//			System.out.println("------------------------------------------------");
//			for (int j = 0; j < Q.size(); j++) {
//				System.out.print("Q[" + j + "] : d[" + toASCII(Q.get(j).getKey()) + "] = " + Q.get(j).getValue());
//				int d = Q_root.getValue() + w[Q_root.getKey() - 1][Q.get(j).getKey() - 1];
//				if (d < Q.get(j).getValue() && w[Q_root.getKey() - 1][Q.get(j).getKey() - 1] != Integer.MAX_VALUE) {
//					Q.get(j).setValue(d);
//					System.out.print("-> d[" + toASCII(Q.get(j).getKey()) + "] = " + d);
//				}
//				System.out.println();
//			}
//			buildMinHeap(Q);
//		}
//
//		for (int i = 0; i < w.length; i++) {
//			for (int j = 0; j < w[i].length; j++) {
//				System.out.print(w[i][j] + "\t");
//			}
//			System.out.println();
//		}
//
//		for (int i = 0; i < name.size(); i++) {
//			System.out.println(i + "= " + name.get(i));
//		}
//
//		for (int i = 0; i < Q.size(); i++) {
//			System.out.println(Q.get(i).getKey() + ", " + Q.get(i).getValue());
//		}
	}

	// 부모가 자식보다 작은 value 값을 갖도록 해주는 재귀함수
	public static void minHeapify(ArrayList<Node> heap, int i) {
		int smallest; // 제일 작은 값 index
		int left = leftChild(i); // 왼쪽 자식
		int right = rightChild(i); // 오른쪽 자식
		if (left < heap.size() && heap.get(left).getValue() < heap.get(i).getValue()) { // 왼쪽 자식이 더 작으면
			smallest = left;
		} else {
			smallest = i;
		}
		if (right < heap.size() && heap.get(right).getValue() < heap.get(i).getValue()) { // 오른쪽 자식이 더 작으면
			smallest = right;
		}
		if (smallest != i) {
			// 현재 Node 가 제일 작은 Node가 되도록 위치 바꾸기
			Node temp = heap.get(i);
			heap.set(i, heap.get(smallest));
			heap.set(smallest, temp);
			// 밑 Node에서 더 작은 값 있으면 재귀로 바꿔줌
			minHeapify(heap, smallest);
		}
	}

	// leaf 부터 root 까지 검사하며 Min Heap 으로 만들어줌
	public static void buildMinHeap(ArrayList<Node> heap) {
		for (int i = parent(heap.size()); i >= 0; i--) {
			minHeapify(heap, i);
		}
	}

	// 최소값 삭제
	public static Node extractMin(ArrayList<Node> heap) {
		// root 삭제하고 그 자리에 leaf 넣기
		Node min = heap.get(0);
		Node leaf = heap.get(heap.size() - 1);
		heap.set(0, leaf);
		heap.remove(heap.size() - 1);
		return min;
	}

	// index 번호 구하는 함수들
	public static int parent(int i) {
		return (int) Math.floor(i / 2);
	}

	public static int leftChild(int i) {
		return 2 * i + 1;
	}

	public static int rightChild(int i) {
		return 2 * i + 2;
	}
}
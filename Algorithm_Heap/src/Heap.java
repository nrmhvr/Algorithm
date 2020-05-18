import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Heap {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		Node[] heap = null;

		// read
		try (FileInputStream fstream = new FileInputStream("data05.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			// 자르기
			String[] stokens = s.split(",|\n");
			heap = new Node[stokens.length / 2];
			// Node 생성, heap 배열에 넣기
			for (int i = 0, j = 0; j < heap.length; i += 2, j++) {
				Node node = new Node(Integer.parseInt(stokens[i]), stokens[i + 1].trim());
				heap[j] = node;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int what = 0;
		buildMaxHeap(heap);

		while (what != 6) {
			System.out.println("**** 현재 우선 순위 큐에 저장되어 있는 작업 대기 목록은 다음과 같습니다 ****");
			System.out.println();
			printHeap(heap);
			System.out.println();
			System.out.println("----------------------------------------------------------");
			System.out.println("1. 작업 추가		2. 최대값		3. 최대 우선순위 작업 처리");
			System.out.println("4. 원소 키값 증가		5. 작업 제거	6. 종료");
			System.out.println("----------------------------------------------------------");
			System.out.print("방법  = ");
			what = scan.nextInt();
			System.out.println();

			if (what == 1) { // 삽입
				System.out.print("key  = ");
				int key = scan.nextInt();
				scan.nextLine();
				System.out.print("value  = ");
				String value = scan.nextLine();
				Node node = new Node(key, value);

				int length = heap.length;
				heap = insert(heap, node);
				if (length == heap.length) {
					System.out.println("이미 존재하는 key 입니다!");
				}
			} else if (what == 2) { // 최대값 삭제
				Node max = max(heap);
				heap = extractMax(heap);
				System.out.println(max.getKey() + ", " + max.getValue() + "-> 삭제되었습니다!");
			} else if (what == 3) { // 우선순위 작업 처리(Max Heap 만들기)
				buildMaxHeap(heap);
			} else if (what == 4) { // key 값 바꾸기
				System.out.print("key  = ");
				int key = scan.nextInt();
				System.out.print("new key  = ");
				int newkey = scan.nextInt();

				if (newkey > key) {
					Node node = new Node(key, null);
					if (!increaseKey(heap, node, newkey)) {
						System.out.println("key가 존재하지 않거나 new key 가 이미 존재합니다!");
					}
				} else {
					System.out.println("new key는 원래 key인 " + key + " 보다 커야 합니다!");
				}
			} else if (what == 5) { // 삭제
				System.out.print("key  = ");
				int key = scan.nextInt();
				Node node = new Node(key, null);

				int length = heap.length;
				heap = delete(heap, node);
				if (length == heap.length) {
					System.out.println("해당 key는 존재하지 않습니다!");
				}
				buildMaxHeap(heap); // Max Heap 유지
			}
			System.out.println();
		}
	}

	// 부모가 자식보다 큰 key 값을 갖도록 해주는 재귀함수
	public static void maxHeapify(Node[] heap, int i) {
		int largest; // 제일 큰 값 index
		int left = leftChild(i); // 왼쪽 자식
		int right = rightChild(i); // 오른쪽 자식
		if (left < heap.length && heap[left].getKey() > heap[i].getKey()) { // 왼쪽 자식이 더 크면
			largest = left;
		} else {
			largest = i;
		}
		if (right < heap.length && heap[right].getKey() > heap[i].getKey()) { // 오른쪽 자식이 더 크면
			largest = right;
		}
		if (largest != i) {
			// 현재 Node 가 제일 큰 Node가 되도록 위치 바꾸기
			Node temp = heap[i];
			heap[i] = heap[largest];
			heap[largest] = temp;
			// 밑 Node에서 더 큰 값 있으면 재귀로 바꿔줌
			maxHeapify(heap, largest);
		}
	}

	// leaf 부터 root 까지 검사하며 Max Heap 으로 만들어줌
	public static void buildMaxHeap(Node[] heap) {
		for (int i = parent(heap.length); i >= 0; i--) {
			maxHeapify(heap, i);
		}
	}

	// 삽입
	public static Node[] insert(Node[] heap, Node newNode) {
		for (int i = 0; i < heap.length; i++) {
			if (heap[i].getKey() == newNode.getKey()) {// 이미 있으면
				return heap;
			}
		}
		heap = Arrays.copyOf(heap, heap.length + 1); // 한칸 추가
		heap[heap.length - 1] = newNode; // 마지막에 newNode 삽입
		return heap;
	}

	// 최대값 삭제
	public static Node[] extractMax(Node[] heap) {
		return delete(heap, max(heap));
	}

	// 최대값 Node 반환
	public static Node max(Node[] heap) {
		return heap[0];
	}

	// node 의 key 를 newKey 로 증가시킴
	public static boolean increaseKey(Node[] heap, Node node, int newKey) {
		for (int i = 0; i < heap.length; i++) {
			if (heap[i].getKey() == newKey) { // new key 가 이미 있으면
				return false;
			}
			if (heap[i].getKey() == node.getKey()) { // 있으면
				heap[i].setKey(newKey); // key 값 set
				return true;
			}
		}
		return false;
	}

	// 삭제
	public static Node[] delete(Node[] heap, Node node) {
		for (int i = 0; i < heap.length; i++) {
			if (heap[i].getKey() == node.getKey()) { // 있으면
				heap[i] = heap[heap.length - 1]; // 마지막 Node 를 그 자리에 저장
				heap = Arrays.copyOf(heap, heap.length - 1); // 마지막 칸 삭제
				return heap;
			}
		}
		return heap;
	}

	// 출력
	public static void printHeap(Node[] heap) {
		for (int i = 0; i < heap.length; i++) {
			System.out.println(heap[i].getKey() + ", " + heap[i].getValue());
		}
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

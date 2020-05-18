import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

class Graph {
	int vertex;
	int edge;
	int weight;

	Graph(int vertex, int edge, int weight) {
		this.vertex = vertex;
		this.edge = edge;
		this.weight = weight;
	}
}

public class BellmanFord {
	static String[] txt = null; // txt파일 읽기용
	static int[][] M = null; // OPT
	static ArrayList<Graph> G = null; // Graph
	static int[] D = null; // D

	public static void main(String[] args) {

		// read
		try (FileInputStream fstream = new FileInputStream("data11_bellman_ford_1.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			txt = s.split("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("<< data11_bellman_ford_1 >>");
		init();
		result(bellmanFord(G));

		// read
		try (FileInputStream fstream = new FileInputStream("data11_bellman_ford_2.txt")) {
			byte[] rb = new byte[fstream.available()];
			while (fstream.read(rb) != -1) {
			}
			fstream.close();
			String s = new String(rb);
			txt = s.split("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("<< data11_bellman_ford_2 >>");
		init();
		result(bellmanFord(G));
	}

	private static void init() {
		String[] existG = txt[0].split(",");
		int num = existG.length;
		G = new ArrayList<>();
		D = new int[num];

		for (int i = 1; i < txt.length; i++) {
			String[] one = txt[i].split(",");
			int start = Integer.parseInt(one[0].trim());
			int end = Integer.parseInt(one[1].trim());
			int weight = Integer.parseInt(one[2].trim());
			G.add(new Graph(start, end, weight));
		}

		// 시작 정점 뺀 나머지 무한대로 초기화
		D[0] = 0;
		for (int i = 1; i < D.length; i++) {
			D[i] = Integer.MAX_VALUE;
		}
	}

	private static boolean bellmanFord(ArrayList<Graph> g) {
		for (int cnt = 0; cnt < D.length; cnt++) {
			System.out.println();
			System.out.println("------------iteration " + cnt + " --------------");

			for (int i = 0; i < g.size(); i++) {
				// relax
				if (D[g.get(i).vertex] != Integer.MAX_VALUE) {
					int d = D[g.get(i).vertex] + g.get(i).weight;
					if (d < D[g.get(i).edge]) {
						if (D[g.get(i).edge] == Integer.MAX_VALUE) {
							System.out.println("Update distance of " + g.get(i).edge + " from inf to " + d);
							D[g.get(i).edge] = d;
						} else {
							System.out.println(
									"Update distance of " + g.get(i).edge + " from " + D[g.get(i).edge] + " to " + d);
							D[g.get(i).edge] = d;
						}
					}
				}
			}

			// 출력
			System.out.print("iteration " + cnt + " distance : [");
			for (int i = 0; i < D.length; i++) {
				if (i == D.length - 1) {
					System.out.print(D[i] + "]");
				} else {
					System.out.print(D[i] + ", ");
				}
			}
			System.out.println();
		}

		// 음수 사이클 존재 확인
		for (int i = 0; i < g.size(); i++) {
			if (D[g.get(i).edge] > D[g.get(i).vertex] + g.get(i).weight) {
				return false;
			}
		}
		return true;
	}

	private static void result(boolean bool) {
		System.out.println();
		if (bool == true) {
			System.out.print("Final distance [");
			for (int p = 0; p < D.length; p++) {
				if (p == D.length - 1) {
					System.out.print(D[p] + "]");
				} else {
					System.out.print(D[p] + ", ");
				}
			}
			System.out.println();
		} else {
			System.out.println("The graph has negative cycle");
		}
	}
}

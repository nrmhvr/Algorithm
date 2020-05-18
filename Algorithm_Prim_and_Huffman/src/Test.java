import java.util.Arrays;

public class Test {

   public static class Node {
      private char key; // name of Node
      private int value;
      private Object near;

      Node(char key, int value) {
         this.key = key;
         this.value = value;
         this.near = null;
      }

      public char getKey() {
         return this.key;
      }

      public int getValue() {
         return this.value;
      }

      public char getNear() {
         return (char) this.near;
      }

      public void setValue(int v) {
         this.value = v;
      }

      public void setNear(char n) {
         this.near = n;
      }
   }

   public static class HeapPriorityQueue {
      private static final int CAPACITY = 200;
      private Node[] a;
      private int size;

      public HeapPriorityQueue() {
         this(CAPACITY);
      }

      public HeapPriorityQueue(int capacity) {
         a = new Node[capacity];
         size = 0;
      }

      public void push(Object object) {
         if (!(object instanceof Node))
            throw new IllegalArgumentException();
         Node x = (Node) object;
         if (size == a.length)
            resize(); // 힙이 가득 찼을 경우 resize()
         int i = size++;
         while (i > 0) {
            int j = i;
            i = (i - 1) / 2; // 부모
            if (a[i].getValue() <= x.getValue()) { // 삽입할 노드의 value가 부모의 value보다 크다면
               a[j] = x;
               return;
            } // 삽입할 노드의 value가 부모의 value보다 작다면 부모(i)를 j로 복사한다
            a[j] = a[i];
         } // 반복을 마친후 i가 가리키는 곳에 x를 삽입한다
         a[i] = x;
      }

      public Node pop() {
         Node min = root();
         a[0] = a[--size];
         heapify(0, size);
         return min;
      }

      public Node root() {
         if (size == 0)
            throw new java.util.NoSuchElementException();
         return a[0];
      }

      public int size() {
         return size;
      }

      private void heapify(int i, int n) {
         Node ai = a[i];
         while (i < n / 2) { // i가 leaf노드가 아닌 동안
            int l = 2 * i + 1; // i의 left child
            int minIndex = l;
            int r = l + 1; // i의 right child
            if (r < n && a[r].getValue() < a[l].getValue())
               // i에게 right child가 있고, 그 value가 left child의 value보다 작은 경우
               minIndex = r;
            if (a[minIndex].getValue() > ai.getValue())
               // 왼쪽, 오른쪽 자식과 ai중에서 ai의 value가 가장 작다면 아무것도 하지 않고 끝낸다
               break;
            a[i] = a[minIndex]; // value가 작은 자식 노드를 부모 자리에 복사한다
            i = minIndex;
         }
         /* break를 통해 반복을 빠져나오거나 반복을 마치고 리프에 도달한 경우 현재 i가 가리키는 자리에 ai를 넣는다 */
         a[i] = ai;
      }

      public void buind_min_heap() {
         for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(i, size);
         }
      }

      private void resize() { // 힙 공간을 2배한다 (size 변수는 건들지 않는다)
         Node[] aa = new Node[2 * a.length];
         System.arraycopy(a, 0, aa, 0, a.length);
         a = aa;
      }

      public void setNodeValue(char c, int value, char near) {
         for (int i = 0; i < this.size; i++) {
            if (a[i].getKey() == c) {
               a[i].setValue(value);
               a[i].setNear(near);
               return;
            }
         }
      }

   }

   static char[] V = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i' }; // 노드
   static boolean[] included; // 최소 신장 트리를 이루는 노드를 마킹하기 위한 배열
   static char start; // 시작 노드
   static int n; // 노드 개수
   static int[][] w; // 가중치
   static int[] key; // distance
   static int MAX = Integer.MAX_VALUE;

   static HeapPriorityQueue Q;
   static char[] S;

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      init();
      prim();

   }

   public static void set(int v1, int v2, int weight) {
      // 간선의 방향성이 없기 때문에 w[v1][v2], w[v2][v1]둘 다 같은 값으로 설정한다
      w[v1][v2] = weight;
      w[v2][v1] = weight;
   }

   public static void init() {
      start = 'a'; // 시작 노드 설정
      n = V.length;
      // 가중치 matrix 초기화
      w = new int[n][n];
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            if (i == j)
               w[i][j] = 0;
            else
               w[i][j] = MAX;
         }
      }
      // path의 가중치 저장
      set(0, 1, 4);
      set(0, 7, 8);
      set(1, 2, 8);
      set(2, 3, 7);
      set(2, 5, 4);
      set(2, 8, 2);
      set(3, 4, 9);
      set(3, 5, 14);
      set(4, 5, 10);
      set(5, 6, 2);
      set(6, 7, 1);
      set(6, 8, 6);
      set(7, 8, 7);

      // distance 초기화
      key = new int[n];
      for (int i = 0; i < n; i++) {
         if (V[i] == start) { // key[start]를 0으로 지정
            key[i] = 0;
         } else {
            key[i] = MAX; // key[v]를 max value로 초기화
         }
      }
      // Q에 모든 노드 삽입
      Q = new HeapPriorityQueue();
      for (int i = 0; i < n; i++) {
         Node x = new Node(V[i], key[i]);
         Q.push(x);
      }
      Q.buind_min_heap(); // Q 정렬
      included = new boolean[n];
      Arrays.fill(included, false);
      S = new char[n];
   }

   public static void prim() {
      boolean isStart = true;
      int cost = 0;

      while (Q.size() > 0) { // Q가 공집합이 될 때까지 반복
         Node u = Q.pop(); // Q의 root 추출
         cost += u.value;
         int ui = getIndexOfChar(V, u.getKey()); // 추출한 노드의 index
         included[ui] = true; // Queue에서 추출한 노드에 대해 최소 신장 트리에 포함되었다고 마크
         if (isStart) {
            System.out.printf("w< ,%c> = %d\n", V[ui], key[ui]);
            isStart = false;
         } else {
            System.out.printf("w<%c,%c> = %d\n", u.getNear(), V[ui], key[ui]);
         }
         for (int i = 0; i < n; i++) {
            if (w[ui][i] < MAX && w[ui][i] < key[i] && !included[i]) {
               key[i] = w[ui][i];
               Q.setNodeValue(V[i], key[i], V[ui]);
            }
         }
         Q.buind_min_heap(); // Q 정렬
      }
      System.out.printf("\nw<MST> = %d\n", cost);

   }

   public static int getIndexOfChar(char[] v, char x) { // 배열 v에서 x가 위치한 index를 반환한다
      for (int i = 0; i < v.length; i++) {
         if (x == v[i]) {
            return i;
         }
      }
      return v.length;
   }

}
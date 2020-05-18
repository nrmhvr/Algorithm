
public class Node {
	private int key;
	private String value;

	// Node 생성자
	public Node(int key, String value) {
		this.key = key;
		this.value = value;
	}

	// getter
	public int getKey() {
		return this.key;
	}

	public String getValue() {
		return this.value;
	}

	// setter
	public void setKey(int key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

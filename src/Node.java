import java.util.ArrayList;
import java.util.List;

public class Node {
	private int data;
	private List<Node> next;

	public Node(int data) {
		this.data = data;
		next = new ArrayList<>();
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
	
	public void markAsNextNode(Node nextNode) {
		if (!next.contains(nextNode))
			next.add(nextNode);
	}

	public List<Node> getForwardNodes() {
		return next;
	}
	
	public int level() {
		return next.size()-1;
	}
//	public void setForwardNodes(List<Node> forwardNodes) {
//		this.forwardNodes = forwardNodes;
//	}
}
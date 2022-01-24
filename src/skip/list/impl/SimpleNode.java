package skip.list.impl;
import java.util.ArrayList;
import java.util.List;

public class SimpleNode {
	private Integer data;
	public List<SimpleNode> next;
	

	public SimpleNode(Integer data) {
		this.data = data;
		this.next = new ArrayList<SimpleNode>();
		
	}

	public Integer getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
	
	public void markAsNextNode(SimpleNode nextNode) {
		if (!next.contains(nextNode))
			next.add(nextNode);
		else {
			System.out.println("This node already has a next node!");
		}
	}

	public List<SimpleNode> getNextList() {
		return next;
	}
	
	public int level() {
		return next.size()-1;
	}
//	public void setForwardNodes(List<Node> forwardNodes) {
//		this.forwardNodes = forwardNodes;
//	}
}

package skip.list.impl;
import java.util.ArrayList;
import java.util.List;

public class SkipListNode {
	private Integer data;
	public List<SkipListNode> next;
	

	public SkipListNode(Integer data) {
		this.data = data;
		this.next = new ArrayList<SkipListNode>();
		
	}

	public Integer getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
	
	public void markAsNextNode(SkipListNode nextNode) {
		if (!next.contains(nextNode))
			next.add(nextNode);
		else {
			System.out.println("This node already has a next node!");
		}
	}

	public List<SkipListNode> getNextList() {
		return next;
	}
	
	public int level() {
		return next.size()-1;
	}
//	public void setForwardNodes(List<Node> forwardNodes) {
//		this.forwardNodes = forwardNodes;
//	}
}

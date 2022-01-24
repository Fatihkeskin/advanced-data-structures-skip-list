package skip.list.impl;

public class CacheFriendlyNode {
	private int data;
	private CacheFriendlyNode next;					// Note this is not a list in cache friendly implementation

	public CacheFriendlyNode(int data) {
		this.data = data;		
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public CacheFriendlyNode getNext() {
		return next;
	}

	public void setNext(CacheFriendlyNode next) {
		this.next = next;
	}
	
//	public void markAsNextNode(SimpleNode nextNode) {
//		if (!next.contains(nextNode))
//			next.add(nextNode);
//		else {
//			System.out.println("This node already has a next node!");
//		}
//	}
//
//	public List<SimpleNode> getNextList() {
//		return next;
//	}
//	
//	public int level() {
//		return next.size()-1;
//	}
}

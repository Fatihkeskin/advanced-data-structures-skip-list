package skip.list.impl;

public class LinkedListNode {
	private int data;
	private LinkedListNode next;					// Note this is not a list in cache friendly implementation
	public boolean isDeleted = false;
	
	public LinkedListNode(int data) {
		this.data = data;		
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public LinkedListNode getNext() {
		return next;
	}

	public void setNext(LinkedListNode next) {
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

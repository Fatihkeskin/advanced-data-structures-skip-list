package skip.list.impl;
import java.util.Random;

public class ConstructSkipList {

	private Node sentinel;				//also known as head
	private int highestLevel;
    private final Random random = new Random();
	
    /**
     * insert node into skip list
     * @param data
     */
	public void addNodeToList(int data) {		
		Node newNode = new Node(data);
		int newNodeLevel = 0;
		newNodeLevel = flipCoin(newNode);
		
		Node searchPointer = sentinel;

		for (int i = highestLevel; i >= 0; i--) {
			// walk down the level until it find a range
			while (null != searchPointer.next.get(i)) {
				// when at bottom level, i is always 0, needs to find the right node to stop
				if (searchPointer.next.get(i).getData() > data ) {
					break;
				}
				searchPointer = searchPointer.next.get(i);
			}

			if (i <= newNodeLevel) {
				newNode.getNextList().set(i, searchPointer.next.get(i));
				searchPointer.getNextList().set(i, newNode);
			}
		}
		
		Node head = sentinel;
		
//		sentinel.markAsNextNode(newNode);
		
		
		
		
	}

	public int flipCoin(Node newNode) {
		int tempLevel = 0;
		
		while (random.nextBoolean()) {
			if(++tempLevel > highestLevel)
				highestLevel = tempLevel;
		}
		return tempLevel;
	}
}

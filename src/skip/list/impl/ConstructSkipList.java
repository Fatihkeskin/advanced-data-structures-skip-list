package skip.list.impl;
import java.util.Random;

public class ConstructSkipList {

	private Node sentinel;				//also known as head
	private int highestLevel;
    private final Random random = new Random();
	
    
    public ConstructSkipList() {
    	sentinel = new Node(null);
    }
    /**
     * insert node into skip list
     * @param data
     */
	public void addNodeToList(int data) {		
		Node newNode = new Node(data);
		int newNodeLevel = 0;
		newNodeLevel = flipCoin();
		
		Node searchPointer = sentinel;
		int searchLevel = highestLevel;
		while (searchLevel>= 0) {
			Node i = searchPointer.next.get(searchLevel);
			if (i == null) {
				searchLevel--;
			} else {
				if (i.getData() > data)
					insertNodeToSkipList(i, i);//TODO
			}
		}
		
		insertNodeToSkipList(newNode, searchPointer);
		
	}

	public int flipCoin() {
		int tempLevel = 0;
		
		while (random.nextBoolean()) {
			if(++tempLevel > highestLevel) {
				sentinel.next.add(null);
				highestLevel = tempLevel;
			}
		}
		return tempLevel;
	}
	
	private void insertNodeToSkipList(Node insertedNode, Node previousNode) {
		previousNode.next.add(insertedNode);
		insertedNode.next.add(previousNode.getNextList().get(highestLevel));
	}
}

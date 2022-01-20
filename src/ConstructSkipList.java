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
		
		Node head = sentinel;
		
//		sentinel.markAsNextNode(newNode);
		
		
		
		
	}

	private int flipCoin(Node newNode) {
		int tempLevel = 0;
		
		while (random.nextBoolean()) {
			if(++tempLevel > highestLevel)
				highestLevel = tempLevel;
		}
		return tempLevel;
	}
}

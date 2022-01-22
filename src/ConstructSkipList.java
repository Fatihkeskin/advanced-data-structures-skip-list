import java.util.Random;

public class ConstructSkipList {

	
	private int levelOfSkipList;
	private Node sentinel;	//also known as head
    private final Random random = new Random();
	
    
    public ConstructSkipList(int levelOfSkipList) {
    	this.levelOfSkipList = levelOfSkipList;
    	this.sentinel = new Node(0);
    	for(int i=0; i<levelOfSkipList; i++) {
			this.sentinel.next.add(null);
		}
    }
    
    /**
     * insert node into skip list
     * @param data
     */
	public void addNodeToList(int data) {	
		
		int levelOfNewNode = flipCoin();
		System.out.print("Level of new node is calculated as: ");
		System.out.println(levelOfNewNode);
		Node newNode = new Node(data);
		for(int i=0; i<levelOfNewNode; i++) {
			newNode.next.add(null);
		}
		Node searchPointer = sentinel;

		placeNode(newNode, levelOfNewNode, searchPointer, data);
		
		
		
	}

	
	private void placeNode(Node newNode, int levelOfNewNode, Node searchPointer, int data) {
		
		int counter = 0;
		
		while(counter < levelOfSkipList) {
			System.out.print("Traversing in sl.. counter: ");
			System.out.println(levelOfSkipList-counter-1);
			if(searchPointer.next.get(levelOfSkipList-counter-1)!= null) {
				System.out.print("If next is not null");
				if(searchPointer.next.get(levelOfSkipList-counter-1).getData() < data) {
					System.out.println(" and next is lower than our data, make pointer next");
					searchPointer = searchPointer.next.get(levelOfSkipList-counter-1);
					counter--;
				}else {
					System.out.println(" and next is greater than our data");
				}
			}
			else if(levelOfSkipList-counter-1 < levelOfNewNode){
				System.out.println("if we are on the last level that we can come");
				
				System.out.println("the node's " + (levelOfSkipList-counter-1) +"th pointer to new point");
				Node newNodeNext = searchPointer.next.get(levelOfSkipList-counter-1);
				newNode.next.set(levelOfSkipList-counter-1, newNodeNext);
				searchPointer.next.set(levelOfSkipList-counter-1, newNode);
				
				System.out.println("New node's pointer: " + searchPointer.getData());
					
					
				//System.out.println("New node's next: " + newNode.next);
				
			}
			counter++;
		}
	}
	
	
	/*private void deleteNode(int data) {
		
		Node searchPointer = sentinel;
		
		int counter = 0;
		
		while(counter < levelOfSkipList) {
			System.out.print("Traversing in sl.. counter: ");
			System.out.println(counter);
			if(searchPointer.next.get(counter)!= null) {
				System.out.println("If next is not null");
				if(searchPointer.next.get(counter).getData() < data) {
					System.out.print(" and next is lower than our data, make pointer next");
					searchPointer = searchPointer.next.get(counter);
				}
			}
			else if(counter == levelOfSkipList-1){
				System.out.println("if we are on the last level that we can come");
				for(int i=0; i<levelOfNewNode; i++) {
					System.out.println("the node's " + i +"th pointer to new point");
					Node newNodeNext = searchPointer.next.get(i);
					//System.out.print("New node's size: ");
					//System.out.println(newNode.next.size());
					
					newNode.next.set(i, newNodeNext);
					searchPointer.next.set(i, newNode);
				}
			}
			counter++;
		}
		
	}*/
	
	
	
	
	public int flipCoin() {
		int tempLevel = 1;
		
		while (random.nextBoolean()) {
			if(++tempLevel > levelOfSkipList)
				levelOfSkipList = tempLevel;
		}
		return tempLevel;
	}
}

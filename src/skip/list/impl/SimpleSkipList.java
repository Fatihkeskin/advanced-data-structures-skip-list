package skip.list.impl;

import java.util.Random;

public class SimpleSkipList {

	private int maxLevelOfSkipList;						// maximum level is defined at the constructor	
	private SkipListNode sentinel; 								// also known as head
	private final Random random = new Random();			// we will use for finding new node's level
	private int size = 0;

	public SimpleSkipList(int levelOfSkipList) {
		// Define max level of the list
		this.maxLevelOfSkipList = levelOfSkipList;
		// Initialize the head node
		this.sentinel = new SkipListNode(0);
		// Initialize max levels from the sentinel
		for (int i = 0; i < levelOfSkipList; i++) {
			this.sentinel.next.add(null);
		}
	}

	/**
	 * insert node into skip list
	 * 
	 * @param data
	 */
	public void addNodeToList(int data) {
		// Determine new node level
		int levelOfNewNode = flipCoin();
//		System.out.println(data + " Level of new node is calculated as: " + levelOfNewNode);
		// Create new node
		SkipListNode newNode = new SkipListNode(data);
		// Initialize next pointers of new node as null
		for (int i = 0; i < levelOfNewNode; i++) {
			newNode.next.add(null);
		}
		// Search pointer is now head
		SkipListNode searchPointer = sentinel;
		// Insert the new node into skip list
		placeNode(newNode, levelOfNewNode, searchPointer, data);
	}

	private void placeNode(SkipListNode newNode, int levelOfNewNode, SkipListNode searchPointer, int data) {
		int counter = 0;
		while (counter < maxLevelOfSkipList) {
			if (searchPointer.next.get(maxLevelOfSkipList - counter - 1) != null) {
				if (searchPointer.next.get(maxLevelOfSkipList - counter - 1).getData() < data) {
					searchPointer = searchPointer.next.get(maxLevelOfSkipList - counter - 1);
					counter--;
				} else {
					if (levelOfNewNode > maxLevelOfSkipList - counter - 1)
						updatePointers(searchPointer, newNode, counter);
				}
			} else if (maxLevelOfSkipList - counter - 1 < levelOfNewNode) {
				updatePointers(searchPointer, newNode, counter);
			}
			counter++;
		}
		size++;
	}

	public void deleteNode(int data) {
		SkipListNode searchPointer = sentinel;
		int counter = 0;
		while (counter < maxLevelOfSkipList) {
			int currentLevel = maxLevelOfSkipList - counter - 1;
			if (searchPointer.next.get(currentLevel) != null && searchPointer.next.get(currentLevel).getData() == data) {
				searchPointer.next.set(currentLevel, searchPointer.next.get(currentLevel).next.get(currentLevel));
			} else if (searchPointer.next.get(currentLevel) != null && searchPointer.next.get(currentLevel).getData() < data) {
				searchPointer = searchPointer.next.get(currentLevel);
				counter--;
			}
			counter++;
		}
		size--;
	}
	
	public String search(int data) {
		SkipListNode searchPointer = sentinel;
		int counter = 0;
		while (counter < maxLevelOfSkipList) {
			int currentLevel = maxLevelOfSkipList - counter - 1;
			if (searchPointer.next.get(currentLevel) != null) {
				if ( searchPointer.next.get(currentLevel).getData() == data) {
					return "Node found";
				} else if (searchPointer.next.get(currentLevel) != null && searchPointer.next.get(currentLevel).getData() < data) {
					searchPointer = searchPointer.next.get(currentLevel);
					counter--;
				}
			}	
			counter++;
		}
		return "Node does not exist";
	}

	private void updatePointers(SkipListNode previousNode, SkipListNode newNode, int counter) {
		int level = maxLevelOfSkipList - counter - 1;
		SkipListNode newNodeNext = previousNode.next.get(level);
		newNode.next.set(level, newNodeNext);
		previousNode.next.set(level, newNode);
	}

	public int flipCoin() {
		int tempLevel = 1;

		while (random.nextBoolean()) {
			tempLevel++;
		}
		return tempLevel;
	}

	public void printListNoIterate() {
		int level = maxLevelOfSkipList-1;
		do {
			SkipListNode searchPointer = sentinel;
			System.out.print("[" + level + "]" + " -> ");
			while (searchPointer.next.get(level) != null) {
				searchPointer = searchPointer.next.get(level);
				System.out.print(searchPointer.getData() + " -> ");
			}
			System.out.println("null");
			level--;
		} while(level > -1);
	}
	
	public void printSkipListWithSize() {
		int level = maxLevelOfSkipList-1;
		for (; level > -1; level--) {
			SkipListNode searchPointer = sentinel;
			int sizeOfList = size;
			System.out.print("[" + level + "]" + " -> ");
			for (; sizeOfList > 0; sizeOfList-- ) {
				if(searchPointer.next.get(level) != null) {
//					sizeOfList = insertArrows(searchPointer, searchPointer.getData(), sizeOfList);
					searchPointer = searchPointer.next.get(level);
					System.out.print(searchPointer.getData());
				} else {
					System.out.print(" -> ");
				}
			}
			System.out.print("null");
			System.out.println();
		}
	}

//	private int insertArrows(SimpleNode currentNode, int data, int sizeOfList) {
//		SimpleNode searchPointer = sentinel;
//		while (!currentNode.next.get(0).getData().equals(data)) {
//			currentNode = currentNode.next.get(0);
//			System.out.print(" -> ");
//			sizeOfList--;
//		}
//		return sizeOfList;
//	}
}

package skip.list.impl;

import java.util.Random;

public class ConstructSkipList {

	private int maxLevelOfSkipList;
	private Node sentinel; // also known as head
	private final Random random = new Random();

	public ConstructSkipList(int levelOfSkipList) {
		this.maxLevelOfSkipList = levelOfSkipList;
		this.sentinel = new Node(0);
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
		System.out.println(data + " Level of new node is calculated as: " + levelOfNewNode);
		// Create new node
		Node newNode = new Node(data);
		// Initialize next pointers of new node as null
		for (int i = 0; i < levelOfNewNode; i++) {
			newNode.next.add(null);
		}
		// Search pointer is now head
		Node searchPointer = sentinel;
		// Insert the new node into skip list
		placeNode(newNode, levelOfNewNode, searchPointer, data);
	}

	private void placeNode(Node newNode, int levelOfNewNode, Node searchPointer, int data) {

		int counter = 0;
		while (counter < maxLevelOfSkipList) {
//			System.out.print("Traversing in sl.. counter: ");
//			System.out.println(maxLevelOfSkipList-counter-1);
			if (searchPointer.next.get(maxLevelOfSkipList - counter - 1) != null) {
//				System.out.print("If next is not null");
				if (searchPointer.next.get(maxLevelOfSkipList - counter - 1).getData() < data) {
//					System.out.println(" and next is lower than our data, make pointer next");
					searchPointer = searchPointer.next.get(maxLevelOfSkipList - counter - 1);
					counter--;
				} else {
					if (levelOfNewNode > maxLevelOfSkipList - counter - 1)
						updatePointers(searchPointer, newNode, counter);
				}
			} else if (maxLevelOfSkipList - counter - 1 < levelOfNewNode) {
//				System.out.println("if we are on the last level that we can come");

				System.out.println("the node's " + (maxLevelOfSkipList - counter - 1) + "th pointer to new point");
				updatePointers(searchPointer, newNode, counter);

				System.out.println("New node's pointer: " + searchPointer.getData());

			}
			counter++;
		}
	}

	public void deleteNode(int data) {
		Node searchPointer = sentinel;
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
	}
	
	public boolean search(int data) {
		Integer position = null;
		Node searchPointer = sentinel;
		int counter = 0;
		int iteration = 0;
		while (counter < maxLevelOfSkipList) {
			int currentLevel = maxLevelOfSkipList - counter - 1;
			if (searchPointer.next.get(currentLevel) != null) {
				if ( searchPointer.next.get(currentLevel).getData() == data) {
//					position = iteration + 1;
					return true;
				} else if (searchPointer.next.get(currentLevel) != null && searchPointer.next.get(currentLevel).getData() < data) {
					searchPointer = searchPointer.next.get(currentLevel);
//					iteration++;
					counter--;
				}
			}	
			counter++;
		}
		return false;
	}

	private void updatePointers(Node previousNode, Node newNode, int counter) {
		int level = maxLevelOfSkipList - counter - 1;
		Node newNodeNext = previousNode.next.get(level);
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

	public void printSkipList() {
		System.out.println(sentinel.getData() + " " + sentinel.next.get(0).getData() + " "
				+ sentinel.next.get(0).next.get(0).getData() + " "
				+ sentinel.next.get(0).next.get(0).next.get(0).getData() + " "
				+ sentinel.next.get(0).next.get(0).next.get(0).next.get(0).getData() + " "
				+ sentinel.next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).getData() + " "
				+ sentinel.next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).getData() + " "
//				+ sentinel.next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).getData()
//				+ " "
//				+ sentinel.next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).next
//						.get(0).getData()
//				+ " "
//				+ sentinel.next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).next.get(0).next
//						.get(0).getData()
				);
	}
}

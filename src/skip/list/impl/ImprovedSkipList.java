package skip.list.impl;

import java.util.Iterator;

public class ImprovedSkipList {

	private static final int LEVEL_COUNT = 10;			// optimal level for 10M data
	private static final int SKIP_PER_LANE = 5;			// every level has 5 time more than upper level
	private static final int MAX_LEVEL_ELEMENT_NUMBER = 10;		// max level has 10 nodes

	private LinkedListNode head;
	private LinkedListNode tail;

	private int elementCount = 0;

	private int[] itemCount = new int[LEVEL_COUNT];						// holds element count for every lane
	private int[] expressLaneItems = new int[LEVEL_COUNT];				
	private int[] startingIndexOfExpressLanes = new int[LEVEL_COUNT];	// starting index for express lanes
	private int[] nodeIndexOnExpressLane;								// holds all indexes for nodes
	private IndexingNode[] pointersToLinkedListNodes;					// holds pointers to original data

	public ImprovedSkipList() {
		this.head = new LinkedListNode(0);
		this.tail = head;

		int maxLevelElementSize = MAX_LEVEL_ELEMENT_NUMBER;

		itemCount[LEVEL_COUNT - 1] = maxLevelElementSize;
		startingIndexOfExpressLanes[LEVEL_COUNT - 1] = 0;

		for (int cur_lev = LEVEL_COUNT - 2; cur_lev >= 0; cur_lev--) {
			itemCount[cur_lev] = itemCount[cur_lev + 1] * SKIP_PER_LANE;
			maxLevelElementSize += itemCount[cur_lev];		
			startingIndexOfExpressLanes[cur_lev] = startingIndexOfExpressLanes[cur_lev + 1] + itemCount[cur_lev + 1];
		}
		
		nodeIndexOnExpressLane = new int[maxLevelElementSize];
		pointersToLinkedListNodes = new IndexingNode[itemCount[0]];

		for (int i = 0; i < maxLevelElementSize; i++) {
			nodeIndexOnExpressLane[i] = Integer.MAX_VALUE;
		}
		
		for (int i = 0; i < itemCount[0]; i++) {
			pointersToLinkedListNodes[i] = null;
		}
	}

	// add new node to skip list
	public void addNodeToList(int key) {
		LinkedListNode new_node = new LinkedListNode(key);
		boolean insertToExpress = false;

		// new node to the end
		tail.setNext(new_node);
		tail = new_node;

		// express lane adjustments
		for (int level = 0; level < LEVEL_COUNT; level++) {
			if (elementCount % (int) Math.pow(SKIP_PER_LANE, (level + 1)) == 0) {
				addNodeToExpressLane(level, new_node);
			} else {
				break;
			}
			insertToExpress = true;
		}

		if (!insertToExpress) {
			createPointerForTheNode(new_node);
		}

		elementCount++;

		// create new express lanes
		if (elementCount % (MAX_LEVEL_ELEMENT_NUMBER * ((int) Math.pow(SKIP_PER_LANE, LEVEL_COUNT))) == 0) {
			createNewExpressLanes();
		}
	}
	
	// mainly used for prints
	private int insertArrows(LinkedListNode currentNode, int data, int sizeOfList) {
		LinkedListNode searchPointer = head;
		while (!currentNode.getNext().equals(data)) {
			currentNode = currentNode.getNext();
			System.out.print(" -> ");
			sizeOfList--;
		}
		return sizeOfList;
	}

	// add new node to other levels
	private int addNodeToExpressLane(int level, LinkedListNode newNode) {
		int currentIndex = startingIndexOfExpressLanes[level] + expressLaneItems[level];
		int levelLimit = currentIndex + itemCount[level];

		if (currentIndex > levelLimit) {
			currentIndex = levelLimit;
		}

		while (newNode.getData() > nodeIndexOnExpressLane[currentIndex] && currentIndex < levelLimit) {
			currentIndex++;
		}

		if (nodeIndexOnExpressLane[currentIndex] == Integer.MAX_VALUE) {
			nodeIndexOnExpressLane[currentIndex] = newNode.getData();
			if (level == 0) {
				pointersToLinkedListNodes[currentIndex - startingIndexOfExpressLanes[0]] = createNewPointerForNode(newNode);
			}

			expressLaneItems[level]++;
		} else {
			return Integer.MAX_VALUE;
		}

		return currentIndex;
	}

	// create the pointer of the node to pointers array
	private void createPointerForTheNode(LinkedListNode node) {
		IndexingNode pointerIndex = pointersToLinkedListNodes[expressLaneItems[0] - 1];

		for (int i = 1; i < SKIP_PER_LANE; i++) {
			if (pointerIndex.data[i] == Integer.MAX_VALUE) {
				pointerIndex.pointers[i] = node;
				pointerIndex.data[i] = node.getData();
				return;
			}
		}
	}
	
	// updates the pointers
	private void updatePointers(SkipListNode previousNode, SkipListNode newNode, int counter) {
		int level = MAX_LEVEL_ELEMENT_NUMBER - counter - 1;
		SkipListNode newNodeNext = previousNode.next.get(level);
		newNode.next.set(level, newNodeNext);
		previousNode.next.set(level, newNode);
	}

	// create new express lanes
	private void createNewExpressLanes() {
		int[] levelItems = new int[LEVEL_COUNT];
		int[] levelStarts = new int[LEVEL_COUNT];

		int newSize = itemCount[LEVEL_COUNT - 1] + MAX_LEVEL_ELEMENT_NUMBER;
		levelStarts[LEVEL_COUNT - 1] = 0;
		levelItems[LEVEL_COUNT - 1] = newSize;

		for (int level = LEVEL_COUNT - 2; level >= 0; level--) {
			levelItems[level] = levelItems[level + 1] * SKIP_PER_LANE;
			levelStarts[level] = levelStarts[level + 1] + levelItems[level + 1];
			newSize += levelItems[level];
		}
		IndexingNode[] newPointers = new IndexingNode[levelItems[0]];

		int[] newExpressLanes = new int[LEVEL_COUNT];

		for (int i = expressLaneItems[LEVEL_COUNT - 1]; i < newSize; i++) {
			newExpressLanes[i] = Integer.MAX_VALUE;
		}

		for (int level = LEVEL_COUNT - 1; level >= 0; level--) {
			for (int i = 0; i < itemCount[level]; i++) {
				newExpressLanes[levelStarts[level] - i] = nodeIndexOnExpressLane[startingIndexOfExpressLanes[level] - i];
			}
		}

	    newPointers[0] = pointersToLinkedListNodes[0];

	    startingIndexOfExpressLanes = levelStarts;
		nodeIndexOnExpressLane = newExpressLanes;
		itemCount = levelItems;
		pointersToLinkedListNodes = newPointers;
	}

	
	public IndexingNode createNewPointerForNode(LinkedListNode node) {
		IndexingNode pointerElement = new IndexingNode();
		pointerElement.data[0] = node.getData();

		for (int i = 1; i < SKIP_PER_LANE; i++) {
			pointerElement.data[i] = Integer.MAX_VALUE;
		}
		pointerElement.pointers[0] = node;

		return pointerElement;
	}

	public String checkOrder() {
		for (int i = LEVEL_COUNT - 1; i >= 0; i--) {
			int previous = Integer.MIN_VALUE;
			int range = i  ==  0 ? nodeIndexOnExpressLane.length : startingIndexOfExpressLanes[i - 1];
			System.out.println("CHECK FROM " + startingIndexOfExpressLanes[i] + " TO " + range);
			for (int j = startingIndexOfExpressLanes[i]; j < range; j++) {
				if (nodeIndexOnExpressLane[j] != Integer.MAX_VALUE) {
//					if (fastLanes[j] < previous) {
//						 System.out.println("WRONG ORDER " + fastLanes[j]);
//						 previous = fastLanes[j]; 
//					} else {
						System.out.println("LEVEL " + i + " INDEX " + j + " : " + nodeIndexOnExpressLane[j]);
						previous = nodeIndexOnExpressLane[j];
//					}
				}
			}
		}
		
		return "CORRECT ORDER";
	}
}
package skip.list.impl;

import java.util.Random;

public class CacheFriendlySkipList {

	/******************
	 * Cache **********
	 *****************/
	// L1 Cache Size: 512 KB -------> 512000 bits
	// L2 Cache Size: 2 MB
	// L3 Cache Size: 16 MB
	// In Java int size: 32 bits
	private static final int CACHE_PARTITIONED_ARRAY_SIZE = 16000;

	private static final int MAX_LEVEL = 10;
	private static final int SKIP = 5; // refactor
	private static final int TOP_LANE_BLOCK = 16; // refactor

	private CacheFriendlyNode sentinelFirst; // also known as head
	private CacheFriendlyNode sentinelLast; // also known as tail

	private int currentLevelOfSkipList; // maximum level is defined at the constructor
	private final Random random = new Random(); // we will use for finding new node's level
	private int num_elements = 0;

	private int[] items_per_level = new int[MAX_LEVEL];
	private int[] flane_items = new int[MAX_LEVEL];
	private int[] starts_of_flanes = new int[MAX_LEVEL];
	private int[] flanes;
	private ProxyNode[] flane_pointers;


	public CacheFriendlySkipList() {
		currentLevelOfSkipList = 0;
		// Initialize the head node
		this.sentinelFirst = new CacheFriendlyNode(0);
		this.sentinelLast = sentinelFirst;

		buildfastLanes();
	}

	private void buildfastLanes() {
		int flane_size = TOP_LANE_BLOCK;

		items_per_level[MAX_LEVEL - 1] = flane_size;
		starts_of_flanes[MAX_LEVEL - 1] = 0;

		// calculate level sizes level by level
		for (int levell = MAX_LEVEL - 2; levell >= 0; levell--) {
			items_per_level[levell] = items_per_level[levell + 1] * SKIP;
			starts_of_flanes[levell] = starts_of_flanes[levell + 1] + items_per_level[levell + 1];
			flane_size += items_per_level[levell];
		}

		flanes = new int[flane_size];
		flane_pointers = new ProxyNode[items_per_level[0]];
		// initialize arrays with placeholder values
		for (int i = 0; i < flane_size; i++) {
			flanes[i] = Integer.MAX_VALUE;
		}
		for (int i = 0; i < items_per_level[0]; i++) {
			flane_pointers[i] = null;
		}
	}

	// Inserts a new element into the given skip list (bulk insert)
	public void insertElement(int key) {
		CacheFriendlyNode new_node = new CacheFriendlyNode(key);
		boolean nodeInserted = true;
		boolean flaneInserted = false;

		// add new node at the end of the data list
		sentinelLast.setNext(new_node); // ????
		sentinelLast = new_node;

		// add key to fast lanes
		for (int level = 0; level < MAX_LEVEL; level++) {
			if (num_elements % (int) Math.pow(SKIP, (level + 1)) == 0 && nodeInserted) {
				insertItemIntoFastLane(level, new_node);
			} else {
				break;
			}
			flaneInserted = true;
		}

		if (!flaneInserted) {
			findAndInsertIntoProxyNode(new_node);
		}

		num_elements++;

		// resize fast lanes if more space is needed
		if (num_elements % (TOP_LANE_BLOCK * ((int) Math.pow(SKIP, MAX_LEVEL))) == 0) {
			resizeFastLanes();
		}
	}

	// Inserts a given key into a fast lane at the given level
	public int insertItemIntoFastLane(int level, CacheFriendlyNode newNode) {
		int curPos = starts_of_flanes[level] + flane_items[level];
		int levelLimit = curPos + items_per_level[level];

		if (curPos > levelLimit) {
			curPos = levelLimit;
		}

		while (newNode.getData() > flanes[curPos] && curPos < levelLimit) {
			curPos++;
		}

		if (flanes[curPos] == Integer.MAX_VALUE) {
			flanes[curPos] = newNode.getData();
			if (level == 0) {
				flane_pointers[curPos - starts_of_flanes[0]] = newProxyNode(newNode);
			}

			flane_items[level]++;
		} else {
			return Integer.MAX_VALUE;
		}

		return curPos;
	}

//	private void updatePointers(SimpleNode previousNode, SimpleNode newNode, int counter) {
//		int level = maxLevelOfSkipList - counter - 1;
//		SimpleNode newNodeNext = previousNode.next.get(level);
//		newNode.next.set(level, newNodeNext);
//		previousNode.next.set(level, newNode);
//	}
//
//	private void checkIfNewLevelNeeded() {
//		int remainder = size / 2;
//		if (currentLevelOfSkipList < remainder) {
//			int[] newLevel = new int[CACHE_PARTITIONED_ARRAY_SIZE];
//			entriesArray.add(newLevel);
//		}
//	}

	// Adds a new element to the corresponding proxy lane in the given skip list
	public void findAndInsertIntoProxyNode(CacheFriendlyNode node) {
		ProxyNode proxy = flane_pointers[flane_items[0] - 1];

		for (int i = 1; i < SKIP; i++) {
			if (proxy.keys[i] == Integer.MAX_VALUE) {
				proxy.keys[i] = node.getData();
				proxy.pointers[i] = node;
				return;
			}
		}
	}

	// Increase size of existing fast lanes of a given skip list
	void resizeFastLanes() {
		int new_size = items_per_level[MAX_LEVEL - 1] + TOP_LANE_BLOCK;
		int[] level_items = new int[MAX_LEVEL];
		int[] level_starts = new int[MAX_LEVEL];

		level_items[MAX_LEVEL - 1] = new_size;
		level_starts[MAX_LEVEL - 1] = 0;

		for (int level = MAX_LEVEL - 2; level >= 0; level--) {
			level_items[level] = level_items[level + 1] * SKIP;
			level_starts[level] = level_starts[level + 1] + level_items[level + 1];
			new_size += level_items[level];
		}

		int[] new_flanes = new int[MAX_LEVEL];
		ProxyNode[] new_fpointers = new ProxyNode[level_items[0]];

		for (int i = flane_items[MAX_LEVEL - 1]; i < new_size; i++) {
			new_flanes[i] = Integer.MAX_VALUE;
		}

		// copy from old flane to new flane
		for (int level = MAX_LEVEL - 1; level >= 0; level--) {
			new_flanes[level_starts[level]] = flanes[starts_of_flanes[level]]; //TODO for
//	    memcpy(&new_flanes[level_starts[level]],  &flanes[starts_of_flanes[level]], sizeof(uint32_t) * items_per_level[level]);
		}

	    new_fpointers[0] = flane_pointers[0];

		flanes = new_flanes;
		flane_pointers = new_fpointers;
		items_per_level = level_items;
		starts_of_flanes = level_starts;
	}

	// Creates a new proxy node in the given skip list
	public ProxyNode newProxyNode(CacheFriendlyNode node) {
		ProxyNode proxy = new ProxyNode();
		proxy.keys[0] = node.getData();
		proxy.updated = false;

		for (int i = 1; i < SKIP; i++) {
			proxy.keys[i] = Integer.MAX_VALUE;
		}
		proxy.pointers[0] = node;

		return proxy;
	}

}

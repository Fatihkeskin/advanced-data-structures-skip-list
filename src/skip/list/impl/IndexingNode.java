package skip.list.impl;

public class IndexingNode {

	public int[] data =  new int[5];
	public LinkedListNode[] pointers  =  new LinkedListNode[5];				// Note this is not a list in cache friendly implementation
}	
package skip.list.impl;

public class ProxyNode {

	public int[] keys =  new int[5];
	public CacheFriendlyNode[] pointers  =  new CacheFriendlyNode[5];				// Note this is not a list in cache friendly implementation
	public boolean updated;
}	
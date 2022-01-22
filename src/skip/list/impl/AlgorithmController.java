package skip.list.impl;

public class AlgorithmController {

	public static void main(String[] args) {
		
		ConstructSkipList skipTest = new ConstructSkipList(10);
		
		skipTest.addNodeToList(20);
		skipTest.addNodeToList(40);
		skipTest.addNodeToList(60);
		skipTest.addNodeToList(25);
		//skipTest.addNodeToList(25);		// eþitse var de
		skipTest.addNodeToList(55);
		skipTest.addNodeToList(80);
		skipTest.addNodeToList(75);
		skipTest.addNodeToList(90);
		skipTest.addNodeToList(85);
		
//		skipTest.deleteNode(25);
		skipTest.deleteNode(85);
//		skipTest.deleteNode(55);
		
		System.out.println(skipTest.search(85));
		
		skipTest.printSkipList();
	}

}

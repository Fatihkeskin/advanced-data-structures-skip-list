package skip.list.impl;

import java.util.Random;
import java.util.stream.IntStream;

public class AlgorithmController {

	public static void main(String[] args) {
		
		SimpleSkipList skipTest = new SimpleSkipList(10);
		Random random = new Random();
		
		skipTest.addNodeToList(20);
		skipTest.addNodeToList(40);
		skipTest.addNodeToList(60);
		skipTest.addNodeToList(25);
		//skipTest.addNodeToList(25);		// eþitse var de
		skipTest.addNodeToList(55);
		skipTest.addNodeToList(80);
		skipTest.addNodeToList(80);
		skipTest.addNodeToList(75);
		skipTest.addNodeToList(90);
		skipTest.addNodeToList(85);
		
//		skipTest.deleteNode(25);
//		skipTest.deleteNode(85);
//		skipTest.deleteNode(55);
		
//		System.out.println(skipTest.search(85));
		
		
		
//		IntStream limitedIntStream = random.ints(10);
//		limitedIntStream.forEach(skipTest::addNodeToList);
		
		double start = System.nanoTime();
//		skipTest.addNodeToList(5);
		System.out.println((System.nanoTime() - start) / 1000000000);
		
		double start3 = System.nanoTime();
		System.out.println(skipTest.search(85));
		System.out.println((System.nanoTime() - start3) / 1000000000);
		skipTest.printSkipListWithSize();
	}

}

package skip.list.impl;

import java.util.Random;
import java.util.stream.IntStream;

public class AlgorithmController {

	public static void main(String[] args) {
		
		SimpleSkipList simpleSkipList = new SimpleSkipList(10);
		Random random = new Random();
		
		simpleSkipList.addNodeToList(20);
		simpleSkipList.addNodeToList(40);
		simpleSkipList.addNodeToList(60);
		simpleSkipList.addNodeToList(25);
		//skipTest.addNodeToList(25);		// eþitse var de
		simpleSkipList.addNodeToList(55);
		simpleSkipList.addNodeToList(80);
		simpleSkipList.addNodeToList(80);
		simpleSkipList.addNodeToList(75);
		simpleSkipList.addNodeToList(90);
		simpleSkipList.addNodeToList(85);
		
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
		System.out.println(simpleSkipList.search(85));
		System.out.println((System.nanoTime() - start3) / 1000000000);
//		simpleSkipList.printSkipListWithSize();
		
		
		int listSize = 100;
		CacheFriendlySkipList cachefriendlySkipList = new CacheFriendlySkipList();
		int[] keys = new int[listSize];
		for(int i = 0; i < listSize; i++) {			
			keys[i] = i + 1;
		}
		
		double startTime = System.currentTimeMillis();
		for (int i = 0; i < listSize; i++) {
			cachefriendlySkipList.insertElement(keys[i]);
		}
		System.out.println("Insertion: " + (int) (listSize / (System.currentTimeMillis() - startTime))+ "ops/sec \n" );

		//burada kaldýk
		// Execute a large amount of single-key lookups using random search keys
//		shuffle_array(keys, 16000000);
		int repeat=100000000/16000000;
		if (repeat < 1) repeat = 1;
			start = System.currentTimeMillis();
//			for (long r = 0; r < repeat; r++) {
//				for (int i = 0; i < 16000000; i++) {
//					assert(searchElement( keys[i]) == keys[i]);
//				}
//			}
//			System.out.println("Lookup: " + (int) (16000000*repeat / (System.currentTimeMillis() - start)) +     "%d ops/s.\n");
		}

}

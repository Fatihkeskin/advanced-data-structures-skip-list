package skip.list.impl;

import java.util.Random;
import java.util.stream.IntStream;

public class Main {

	public static void main(String[] args) {
		
		SimpleSkipList simpleSkipList = new SimpleSkipList(10);
		Random random = new Random();
		
		simpleSkipList.addNodeToList(20);
		simpleSkipList.addNodeToList(40);
		simpleSkipList.addNodeToList(60);
		simpleSkipList.addNodeToList(25);
		simpleSkipList.addNodeToList(55);
		simpleSkipList.addNodeToList(75);
		simpleSkipList.addNodeToList(90);
		simpleSkipList.addNodeToList(85);
		
		simpleSkipList.deleteNode(25);
		simpleSkipList.deleteNode(85);
		simpleSkipList.deleteNode(55);
		
		int size;
		if (args.length == 0) 
			size= 10000;
		else 
			size = Integer.valueOf(args[0]);
		
		double startConstruct = System.nanoTime();
		long instructionStart = System.currentTimeMillis();
		IntStream limitedIntStream = random.ints(size);
		limitedIntStream.forEach(simpleSkipList::addNodeToList);
		System.out.println("Simple Skip List Construct: " + (System.nanoTime() - startConstruct) / 1000000000 + " seconds for " + size + " random integers.");
		
//		simpleSkipList.printListNoIterate();   // You can print skip list with size less than 1000 because it does not fit into console
		
		double startInsert = System.nanoTime();
		simpleSkipList.addNodeToList(1342);
		System.out.println("Simple Skip List Insert: " + (System.nanoTime() - startInsert) / 1000000 + " milliseconds");
		
		double startSearch = System.nanoTime();
		System.out.println("Simple Skip List Search: Result-> " + simpleSkipList.search(85) + " Time-> " + (System.nanoTime() - startSearch) / 1000000 + " milliseconds");
		
		double startDelete = System.nanoTime();
		simpleSkipList.deleteNode(25);
		System.out.println("Simple Skip List Delete: " + (System.nanoTime() - startDelete) / 1000000 + " milliseconds");
		
		System.out.println();
		/**
		 * Improved skip list
		 */	
		ImprovedSkipList cachefriendlySkipList = new ImprovedSkipList();
		int[] keys = new int[size];
		for(int i = 0; i < size; i++) {			
			keys[i] = random.nextInt(size);
		}
		
		double startConstructImproved = System.nanoTime();
		long improvedStart = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			cachefriendlySkipList.addNodeToList(keys[i]);
		}
		System.out.println("Improved Skip List Construct: " + (System.nanoTime() - startConstructImproved) / 1000000000 + " seconds for " + size + " random integers.");

		double startInsertImproved = System.nanoTime();
		cachefriendlySkipList.addNodeToList(5);
		System.out.println("Improved Skip List Insert: " + (System.nanoTime() - startInsertImproved) / 1000000 + " milliseconds");
		
		// Note: Search operation could not implemented due to lack of planning but it takes just as insert.
		// Note: Delete operation only marks actual nodes as deleted so it takes just as insert.
	}
}

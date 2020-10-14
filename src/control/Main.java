package control;

public class Main {

	public static void main(String[] args) {
		
//		Test 1
//		int[] board = { 4, 3 };
//		int[][] snake = { { 2, 2 }, { 3, 2 }, { 3, 1 }, { 3, 0 }, { 2, 0 }, { 1, 0 }, { 0, 0 } };
//		int depth = 3;
		
//		Test 2
//		int[] board = { 2, 3 };
//		int[][] snake = { { 0, 2 }, { 0, 1 }, { 0, 0 }, { 1, 0 }, { 1, 1 }, { 1, 2 }};
//		int depth = 10;
		
//		Test 3
		int[] board = { 10, 10 };
		int[][] snake = { { 5, 5 }, { 5, 4 }, { 4, 4 }, { 4, 5 }};
		int depth = 4;
		
		System.out.println(String.format("There are %d possible paths in this given case.", Tools.numberOfAvailableDifferentPaths(board, snake, depth)));
	}

}

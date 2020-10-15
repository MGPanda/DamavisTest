package control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 * The Tools class is where most of the code is executed. Here is where, with the parameters received from the Main
 * class, every single possibility is tested and eventually returned.
 * @author Tòfol Martínez
 *
 */
public class Tools {
	/**
	 * An array containing all four possible directions in String form.
	 */
	private static String[] directions = { "L", "R", "D", "U" };
	
	/**
	 * An empty ArrayList that will be filled and used to test every possible movement by the snake.
	 */
	private static ArrayList<String> movements = new ArrayList<String>();
	
	/**
	 * An integer that counts the amount of possible paths by the snake. It's returned to the Main class at the
	 * end of the program's run.
	 */
	private static int counter = 0;

	/**
	 * The main method of the Tools class, it simply calls on the other methods in order to make the application work.
	 * First, it calls generateMovements to create the String ArrayList with every possible movement and then it uses
	 * checkIfMovePossible on every movement iteration. To do that, since the exercise asks for every movement to happen
	 * simultaneously, we create a Runnable that will use said method, and then run it on individual threads. Finally,
	 * we join the threads so that they don't overstep each other and making them wait until each and every one of them
	 * has finished working. If we don't, we will get a different result each time, depending on the processor speed.
	 * @param board The board where the snake will be moving around, on a int[x,y] format. It's passed on by the Main
	 * class.
	 * @param snake The snake on a 2D int array format. It's passed on by the Main class.
	 * @param depth An integer that determines the depth of movement, also passed on by the Main class.
	 * @return The method returns the move counter on integer form.
	 */
	public static int numberOfAvailableDifferentPaths(int[] board, int[][] snake, int depth) {
		generateMovements("", depth);
		
		for (int i = 0; i < movements.size(); i++) {
			final int iteration = i;
			
			Runnable checker = () -> {
				checkIfMovePossible(movements.get(iteration), board, snake);
			};
			
			try {
				Thread t = new Thread(checker);
				t.start();
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return counter;
	}
	
	/**
	 * A simple recursive method to add every possible movement iteration to the ArrayList, only using the directions
	 * array as well as the movement depth. While recursiveness is still not optimal in most cases, it works as intended
	 * here, even with really large depths, and it certainly works better than the original solution.
	 * @param loop A (initially empty) string that keeps on filling itself and being added to the ArrayList.
	 * @param depth The depth of movement. The method will loop as long as there are still movement possibilities to be
	 * added.
	 */
	private static void generateMovements(String loop, int depth) {
		if (depth == 0) {
			movements.add(loop);
			return;
		}
		for (int i = 0; i < directions.length; i++) {
			String nextLoop = loop + directions[i];
			generateMovements(nextLoop, depth-1);
		}
	}
	
	/**
	 * This method is used to check every possible movement that could be done by the snake. By receiving a
	 * movement parameter (UDL, RRU, etc.), the board and the snake itself, we test every possible step, move
	 * the snake alongside it, and finally, if everything can be completed correctly, add a point to the final counter.
	 * @param move A string containing the movements to be checked. 
	 * @param board The board where the snake will be moving around, on a int[x,y] format.
	 * @param snake The snake on a 2D int array format.
	 */
	private static void checkIfMovePossible(String move, int[] board, int[][] snake) {
		int[][] testSnake = snake.clone();
		boolean canMove = false;
		int movX = 0, movY = 0;
		for (int i = 0; i < move.length(); i++) {
			
			canMove = false;
			movX = 0;
			movY = 0;
			
			switch ("" + move.charAt(i)) {
			case "L":
				movX = -1;
				break;
			case "R":
				movX = 1;
				break;
			case "D":
				movY = 1;
				break;
			case "U":
				movY = -1;
				break;
			}
			
			int[] snakeHead = testSnake[0].clone();
			snakeHead[0] += movX;
			snakeHead[1] += movY;
			
			if (snakeHead[0] >= 0 && snakeHead[0] < board[0] && snakeHead[1] >= 0 && snakeHead[1] < board[1]
					&& !steppingOnTop(snakeHead, testSnake)) {
				canMove = true;
				
				Collections.rotate(Arrays.asList(testSnake), 1);
				
				testSnake[0] = snakeHead;
			} else break;
		}
		if (canMove) {
			//System.out.println(String.format("The snake has moved %s.",move));
			counter++;
		}
	}
	
	/**
	 * Since the "contains" method doesn't work on arrays (because it doesn't compare the contents, just the raw data
	 * of it), this method will take care of comparing each value in the snake to the next available position.
	 * @param snakeHead The position where the snake head will supposedly move to next.
	 * @param snake The array containing the snake in full.
	 * @return found, a boolean that tells us if the position we're moving to is already being used.
	 */
	private static boolean steppingOnTop(int[] snakeHead, int[][] snake) {
		boolean found = false;
		for (int i = 0; i < snake.length-1; i++) {
			if (Arrays.equals(snakeHead, snake[i])) found = true;
		}
		return found;
	}
	
	/**
	 * The original solution to the issue of generating every possible direction combination. In order to test the
	 * program itself, I made this method that generates random combinations, checks if they're already on the list,
	 * and if not, it adds them up to 4^depth (the max amount of combinations).
	 * @param depth The depth of the movements, used to get the size of the ArrayList.
	 * @deprecated
	 */
//	private static void generateMovements(int depth) {
//		Random r = new Random();
//		
//		do {
//			String test = "";
//			for (int i = 0; i < depth; i++) {
//				test += directions[r.nextInt(directions.length)];
//			}
//			if (!movements.contains(test)) {
//				movements.add(test);
//			}
//		} while (movements.size() < Math.pow(4, depth));
//	}
}

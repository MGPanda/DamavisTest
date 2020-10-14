package control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Tools {
	
	private static String[] directions = { "U", "D", "L", "R" };
	private static ArrayList<String> movements = new ArrayList<String>();
	private static int counter = 0;

	public static int numberOfAvailableDifferentPaths(int[] board, int[][] snake, int depth) {
		generateMovements(depth);
		for (int i = 0; i < movements.size(); i++) {
			checkIfMovePossible(movements.get(i), board, snake);
		}
		return counter;
	}
	
	
	private static void generateMovements(int depth) {
		Random r = new Random();
		
		do {
			String test = "";
			for (int i = 0; i < depth; i++) {
				test += directions[r.nextInt(directions.length)];
			}
			if (!movements.contains(test)) {
				movements.add(test);
			}
		} while (movements.size() < Math.pow(4, depth));
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
			case "U":
				movY = -1;
				break;
			case "D":
				movY = 1;
				break;
			case "L":
				movX = -1;
				break;
			case "R":
				movX = 1;
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
		for (int i = 1; i < snake.length-1; i++) {
			if (Arrays.equals(snakeHead, snake[i])) found = true;
		}
		return found;
	}
}

package game;

import java.util.Random;

/**
* <h1>Dice</h1>
* <p>A simple dice class that can generate dice throws for the game.</p>
* <b>Note:</b> This class can also randomly choose an turn option for a BOT.
*
* @version 1.0
* @since   2021-08-03
*/
public class Dice {
	private Random random = new Random();
	private Integer currentDice = 0;

	/**
	 * <h1><i>getRandomInt</i></h1>
	 * <p>This method returns an integer between the minimum and maximum integer provided.</p>
	 * @param min - Integer of the minimum value
	 * @param max - Integer of the maximum value
	 * @return Integer - returns an integer between the minimum and maximum value
	 */
	protected Integer getRandomInt(Integer min, Integer max) {
		if (max - min <= 0) {
			return 0;
		}
		else { return random.nextInt(max - min) + min; }
	}

	/**
	 * <h1><i>set</i></h1>
	 * <p>This method rolls the dice once and saves
	 * the result as attribute.</p>
	 */
	protected void set() {
		currentDice = random.nextInt(6) +1;
	}

	/**
	 * <h1><i>setStart</i></h1>
	 * <p>This method throws the dice 3 times and either
	 * returns a six or a 1 to 5, indicating if a player got a six
	 * after 3 times or not.</p>
	 */
	protected void setStart() {
		Boolean gotSix = false;
		for (Integer i = 0; i < 3; i++) {
			if (random.nextInt(6) +1 == 6) {
				gotSix = true;
				break;
			}
		}
		if (gotSix) { currentDice = 6; }
		else { currentDice = random.nextInt(5) +1; }
	}

	/**
	 * <h1><i>get</i></h1>
	 * <p>This method returns the current dice.</p>
	 * @return Integer - returns the current dice
	 */
	protected Integer get() {
		return currentDice;
	}

	/**
	 * <h1><i>reset</i></h1>
	 * <p>This method resets the current dice attribute to 0.</p>
	 */
	protected void reset() {
		currentDice = 0;
	}
}

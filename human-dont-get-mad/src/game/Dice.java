package game;

import java.util.Random;

//since August 03rd 2021
public class Dice {
	private Random random = new Random();
	private Integer currentDice = 0;
	
	//return an init between min and max
	protected Integer getRandomInt(Integer min, Integer max) {
		if (max - min <= 0) {
			return 0;
		}
		else { return random.nextInt(max - min) + min; }
	}
	
	//throw the dice once
	protected void setDice() {
		currentDice = random.nextInt(6) +1;
	}
	
	//return currentDice
	protected Integer getDice() {
		return currentDice;
	}
	
	//reset dice after turn
	protected void resetDice() {
		currentDice = 0;
	}
	
	//TODO generate start dice (6 tries for six an than the throw after that)
}

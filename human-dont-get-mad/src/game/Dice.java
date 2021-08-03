package game;

import java.util.Random;

//since August 03rd 2021
public class Dice {
	private Random random = new Random();
	
	//return an init between min and max
	protected Integer getRandomInt(Integer min, Integer max) {
		if (max - min <= 0) {
			return 0;
		}
		else { return random.nextInt(max - min) + min; }
	}
	
	//return a dice throw
	protected Integer getDice() {
		return random.nextInt(6) +1;
	}
}

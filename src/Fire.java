import java.util.Random;

/**
 * The Fire class creates the fire graphic. The update() method contains all of
 * the code needed in order to draw the water graphic and assign its direction
 * and speed.
 * 
 * @author Ellen Krebs
 *
 */

public class Fire {
	// List of the fields in this class

	// Fire graphic
	private Graphic graphic;

	// Random number generator
	private Random randGen;

	// Countdown to the number of fireballs
	private int fireballCountdown;

	// Integer heat
	private int heat;

	public Fire(float x, float y, Random randGen) {
		this.randGen = randGen;
		
		// Sets the graphic to the Fire graphic
		graphic = new Graphic("FIRE");

		// Sets the Fire's position
		graphic.setPosition(x, y);

		// Number of milliseconds before the next new Fireball object is hurled 
		fireballCountdown = randGen.nextInt(3001) + 3000;

		// Sets field equal to parameter
		this.randGen = randGen;

		// Initializes the heat field
		heat = 40;

	}

	/**
	 * This update method draws the fireball graphic and counts down the number
	 * of seconds before the next fireball can be hurled from a Fire object.
	 * 
	 * @param time: the number of seconds 
	 */
	
	public Fireball update(int time) {
		graphic.draw();
		fireballCountdown -= time;
		
		if (fireballCountdown <= 0) {
			fireballCountdown = randGen.nextInt(3001) + 3000;
			Fireball fireball = new Fireball(graphic.getX(), graphic.getY(),
					(float) (randGen.nextFloat() * 2 * Math.PI));
			return fireball;
		}

		return null;

	}
	
	/**
	 * This draws a fire object
	 */
	
	public Graphic getGraphic() {
		return graphic;
	}
	
	/**
	 * If a water object is colliding with a Fire object, then each collision
	 * will lower the heat field by 1.
	 * @param water: the water array is taken in as a parameter
	 */

	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {
			if (water[i] != null
					&& graphic.isCollidingWith(water[i].getGraphic())) {
				heat--;
				water[i] = null;
			}

		}
	}
	
	/**
	 * If the heat field is less than 1, then the fire should be removed
	 */
	
	public boolean shouldRemove() {
		if (heat < 1) {
			return true;
		} else {
			return false;
		}
	}

}

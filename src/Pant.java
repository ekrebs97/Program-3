import java.util.Random;
import java.util.ArrayList;

/**
 * The Pant class creates the pant graphic. The update() method contains all of
 * the code needed in order to draw the pant graphic, generate its position, and
 * state whether it is alive or not.
 * 
 * @author Ellen Krebs
 *
 */
public class Pant {
	// List of the fields in this class

	// Pant graphic
	private Graphic graphic;

	// Random number generator
	private Random randGen;

	// Boolean field to check that the pants are alive
	private boolean isAlive;

	// Constructor
	public Pant(float x, float y, Random randGen) {

		// Draws pant graphic
		graphic = new Graphic("PANT");

		// Sets the position of the pant graphic
		graphic.setPosition(x, y);

		// If this is true, then the update method will run
		isAlive = true;

	}

	/**
	 * The pant update method is updated every time the Level class is run. If
	 * the isAlive field is = true, then the pant graphic will be drawn.
	 * 
	 * @param time:
	 *            the number of seconds
	 */

	public void update(int time) {
		if (isAlive == true) {
			graphic.draw();
		}

	}

	/**
	 * This method draws the pant object
	 */
	
	public Graphic getGraphic() {
		return graphic;
	}

	/**
	 * This method handles fireball collisions between the pants and the
	 * fireballs. If the fireballs are colliding with the pants, then a fireball
	 * destroys the pant object
	 * 
	 * @param fireballs: an ArrayList of fireballs
	 */
	
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for (int i = 0; i < fireballs.size(); i++) {
			if (graphic
					.isCollidingWith(fireballs.get(i).getGraphic()) == true) {
				fireballs.get(i).destroy();

				// Initializes the random number generator so that it's not null
				randGen = new Random();

				// Initializes a new fire object
				Fire fire = new Fire(graphic.getX(), graphic.getY(), randGen);
				isAlive = false;
				return fire;
			}
		}
		return null;

	}

	/**
	 * If a pant is not alive, then it should be removed
	 */
	
	public boolean shouldRemove() {
		if (isAlive == false) {
			return true;
		} else {
			return false;
		}
	}

}

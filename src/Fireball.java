/**
 * The Fireball class creates the fireball graphic. The update() method contains
 * all of the code needed in order to draw the water graphic and assign its
 * direction and speed.
 * 
 * @author Ellen Krebs
 *
 */
public class Fireball {
	// List of the fields in this class

	// Fireball graphic
	private Graphic graphic;

	// Fireball's speed
	private float speed;

	// Boolean field to check that the fireballs are alive
	private boolean isAlive;

	/**
	 * This is the constructor for the fireball class, which handles a
	 * fireball's x and why coordinates and its direction
	 * 
	 * @param x:
	 *            a fireball's x coordinate
	 * @param y:
	 *            a fireball's y coordinate
	 * @param directionAngle:
	 *            a fireball's direction
	 */
	
	public Fireball(float x, float y, float directionAngle) {
		// Initializes each field

		// Determines which graphic will be drawn
		graphic = new Graphic("FIREBALL");

		// Determines the fireball's speed
		speed = 0.2f;

		// Sets the position of the fireball graphic
		graphic.setPosition(x, y);

		// Sets the fireball's direction
		graphic.setDirection(directionAngle);

		// If the fireball is alive, then this is initialized to true
		isAlive = true;

	}

	/**
	 * This update method draws the fireball graphic and sets isAlive to false
	 * if the fireball's x or y coordinates are greater than or equal to
	 * GameEngine.getWidth/getHeight + 100
	 * 
	 * @param time
	 */

	public void update(int time) {
		// Draws the fireball graphic
		graphic.draw();

		// Calculates the change in the x position of the fireball
		float changeX = (speed * time) * (graphic.getDirectionX());

		// Calculates the change in the y position of the fireball
		float changeY = (speed * time) * (graphic.getDirectionY());

		// Sets the fireball's correct position
		graphic.setPosition(graphic.getX() + changeX, graphic.getY() + changeY);

		// If the fireball's x and y coordinates are greater than the game
		// board's width and height + 100, then the graphic will not be drawn
		if (graphic.getX() >= GameEngine.getWidth() + 100) {
			isAlive = false;
		} else if (graphic.getY() >= GameEngine.getHeight() + 100) {
			isAlive = false;
		} else {
			isAlive = true;
		}

	}

	/**
	 * Draws the fireball object
	 */
	
	public Graphic getGraphic() {
		return graphic;
	}

	/**
	 * If the water array is not null, and a fireball is colliding with water,
	 * then the fireball and water will stop being drawn
	 * @param water: the water array is taken in as a parameter
	 */
	
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {
			if (water[i] != null
					&& graphic.isCollidingWith(water[i].getGraphic())) {
				isAlive = false;
				water[i] = null;
			}

		}
	}

	/**
	 * This method destroys a fireball
	 */
	
	public void destroy() {
		isAlive = false;
	}

	/**
	 * If a fireball is not alive, then it should be destroyed
	 */
	
	public boolean shouldRemove() {
		if (isAlive == false) {
			return true;
		} else {
			return false;
		}
	}

}

/**
 * The Water class creates the water graphic. The update() method contains all
 * of the code needed in order to draw the water graphic and assign its
 * direction and speed.
 *
 * @author Ellen Krebs
 */
public class Water {
	// List of the fields in this class

	// Water graphic
	private Graphic graphic;

	// Water's speed
	private float speed;

	// The distance the water drop has traveled
	private float distanceTraveled;

	// Water's direction
	private float direction;

	// Constructor
	public Water(float x, float y, float direction) {
		// Initializes each field
		
		// Determines which graphic to be drawn
		graphic = new Graphic("WATER");
		
		// Determine's the water's speed
		speed = 0.7f;

		// Sets position of the water
		graphic.setPosition(x, y);

		// Sets the water's direction
		graphic.setDirection(direction);

		this.direction = direction;
	}

	/**
	 * The water update method is updated every time the Level class is run.
	 * This method draws the graphic, calculates the x and y position of the
	 * water drop, sets the water's correct position, then updates the
	 * distanceTraveled field each time the water drop moves. Also, if the water
	 * drop travels more than 200 pixels, the water drop will disappear.
	 * 
	 * @param time:
	 *            is multiplied by speed in order to get the change in the
	 *            water's X and Y positions, and to update the distanceTraveled
	 *            variable
	 * @return: this - if the water has not traveled 200 pixels, the water
	 *          update method returns itself
	 * @return: null - if the water has traveled 200 pixels, the water update
	 *          method returns null
	 */
	
	public Water update(int time) {

		// Draws the water graphic
		graphic.draw();

		// Calculates the change in the x position of the water drop
		float changeInX = (speed * time) * ((float) Math.cos(direction));

		// Calculates the change in the y position of the water drop
		float changeInY = (speed * time) * ((float) Math.sin(direction));

		// Sets the water's correct position
		graphic.setPosition(graphic.getX() + changeInX,
				graphic.getY() + changeInY);

		// Updates the distanceTraveled variable every time the water drop moves
		distanceTraveled += (speed * time);

		// If the water drop's distance traveled is less than 200 (pixels), the
		// field will return itself, and if not, it will return null
		if (distanceTraveled < 200) {
			return this;
		} else {
			return null;
		}
	}
	
	/**
	 * This method draws the water object
	 */
	public Graphic getGraphic() {
		return graphic;
	}

}

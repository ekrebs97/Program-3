import java.util.ArrayList;

/**
 * The Hero class creates the hero graphic. The update() method contains all of
 * the code needed for the hero graphic to move and shoot water. The hero
 * graphic moves differently depending on whether the control type is 1, 2, or
 * 3.
 *
 * @author Ellen Krebs
 */
public class Hero {
	// List of fields in this class

	// Hero graphic
	private Graphic graphic;

	// Hero's speed
	private float speed;

	// Hero's controlType (how is the hero's movement controlled?)
	private int controlType;

	// Constructor
	public Hero(float x, float y, int controlType) {
		
		// Hero graphic
		graphic = new Graphic("HERO");
		
		// Hero's speed
		speed = 0.12f;
		
		// Hero's controlType
		this.controlType = controlType;

		// Sets the hero's position
		graphic.setPosition(x, y);
	}

	/**
	 * The hero update method updates the state of the hero and water array.
	 * Essentially, it will allow the hero to move around and shoot water
	 * depending on the control Type that it set and what keys are pressed.
	 * 
	 * @param time:
	 *            Is multiplied by speed * time to get the hero's X and Y
	 *            positions.
	 * @param water:
	 *            takes the water array as its second parameter so that it can
	 *            have access to the water array. This allows the the hero to
	 *            shoot water whenever pressing the space bar or left mouse key.
	 */
	
	public void update(int time, Water[] water) {
		// Draws the hero graphic
		graphic.draw();

		// If controlType == 1, the hero is controlled by the A, S, D, W keys
		if (controlType == 1) {

			// If the player is pressing the "D" key, the hero moves right
			if (GameEngine.isKeyHeld("D") || GameEngine.isKeyPressed("D")) {
				graphic.setDirection(0);
				graphic.setX(graphic.getX() + speed * time);

				// If the player is pressing the "A" key, the hero moves left
			} else if (GameEngine.isKeyHeld("A")
					|| GameEngine.isKeyPressed("A")) {
				graphic.setDirection((float) Math.PI);
				graphic.setX(graphic.getX() - speed * time);

				// If the player is pressing the "W" key, the hero moves up
			} else if (GameEngine.isKeyHeld("W")
					|| GameEngine.isKeyPressed("W")) {
				graphic.setDirection((float) (3 * Math.PI / 2));
				graphic.setY(graphic.getY() - speed * time);

				// If the player is pressing the "S" key, the hero moves down
			} else if (GameEngine.isKeyHeld("S")
					|| GameEngine.isKeyPressed("S")) {
				graphic.setDirection((float) (Math.PI / 2));
				graphic.setY(graphic.getY() + speed * time);
			}
		}

		// If controlType == 2, the hero is controlled by the A, S, D, W keys
		// and the hero can face the direction of the mouse's movement
		if (controlType == 2) {
			graphic.setDirection(GameEngine.getMouseX(),
					GameEngine.getMouseY());

			// No matter which key is pressed, the hero graphic will always face
			// the direction of the mouse
			if (GameEngine.isKeyHeld("D") || GameEngine.isKeyPressed("D")) {
				graphic.setX(graphic.getX() + speed * time);
			} else if (GameEngine.isKeyHeld("A")
					|| GameEngine.isKeyPressed("A")) {
				graphic.setX(graphic.getX() - speed * time);
			} else if (GameEngine.isKeyHeld("W")
					|| GameEngine.isKeyPressed("W")) {
				graphic.setY(graphic.getY() - speed * time);
			} else if (GameEngine.isKeyHeld("S")
					|| GameEngine.isKeyPressed("S")) {
				graphic.setY(graphic.getY() + speed * time);
			}
		}

		// If controlType == 3, the mouse completely controls the hero's
		// movement and direction
		if (controlType == 3) {

			// This calculates the distance between the mouse's position and the
			// hero graphic's coordinates
			float heroChangeOfX = GameEngine.getMouseX() - graphic.getX();
			float heroChangeOfY = GameEngine.getMouseY() - graphic.getY();

			// This calculates the distance formula
			float distance = (float) Math
					.sqrt(squared(heroChangeOfX) + squared(heroChangeOfY));

			// This sets the hero's direction towards the mouse
			graphic.setDirection(GameEngine.getMouseX(),
					GameEngine.getMouseY());

			// If the distance is greater than 20 (pixels), then the hero
			// graphic will continue to move towards the mouse. If it is less
			// than 20 (pixels), the hero will stop moving
			if (distance > 20) {
				graphic.setPosition(
						graphic.getX()
								+ graphic.getDirectionX() * (speed * time),
						graphic.getY()
								+ graphic.getDirectionY() * (speed * time));
			}

		}
		
		// If the player presses the space bar or the left mouse key, then the
		// hero will spit out 8 water drops in a row
		if (GameEngine.isKeyPressed("SPACE") || GameEngine.isKeyPressed("MOUSE")
				|| GameEngine.isKeyHeld("SPACE")
				|| GameEngine.isKeyHeld("MOUSE")) {
			for (int i = 0; i < water.length; i++) {
				if (water[i] == null) {
					water[i] = new Water(graphic.getX(), graphic.getY(),
							graphic.getDirection());
					break;
				}
			}

		}
	}

	/**
	 * This is a private method used in the distance variable, it allows the
	 * changeOfX and changeOfY variables to be squared.
	 *
	 * @param float
	 *            x: returns a float variable
	 * @return x * x: will multiply x by itself to get x squared. If this method
	 *         is called, then it will square the float that it is applied to.
	 */
	
	private float squared(float x) {
		return x * x;
	}

	/**
	 * This draws the hero graphic
	 */
	public Graphic getGraphic() {
		return graphic;
	}

	/**
	 * This method handles fireball collisions with the hero
	 * @param fireballs: the ArrayList fireballs is taken in as a parameter
	 */
	
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for (int i = 0; i < fireballs.size(); i++) {
			if (graphic.isCollidingWith(fireballs.get(i).getGraphic()))
				return true;
		}
		return false;
	}

}

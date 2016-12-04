// Title:            Pants on Fire
// Files:            Level.java, Hero.java, Water.java, GameEngine.java, 
// 					 Graphic.java
// Semester:         Fall 2016
//
// Author:           Ellen Krebs
// Email:            ekrebs@wisc.edu
// CS Login:         ekrebs
// Lecturer's Name:  Gary Dahl
// Lab Section:      335

// Partner Name:     Alice Rusnak
// Partner Email:    arusnak2@wisc.edu
// Partner CS Login: arusnak
// Lecturer's Name:  Gary Dahl
// Lab Section:      344

//    _X_ Write-up states that Pair Programming is allowed for this assignment.
//    _X_ We have both read the CS302 Pair Programming policy.
//    _X_ We have registered our team prior to the team registration deadline.

// Persons: CSLC Tutors  
// Online Sources: Piazza (URL: https://piazza.com/class/is95hp65mtx16g)

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * The Level class is responsible for managing all of the objects in your game.
 * The GameEngine creates a new Level object for each level, and then calls that
 * Level object's update() method repeatedly until it returns either "ADVANCE"
 * (to go to the next level), or "QUIT" (to end the entire game). <br/>
 * <br/>
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Random randGen;</li>
 * <li>private Hero hero;</li>
 * <li>private Water[] water;</li>
 * <li>private ArrayList&lt;Pant&gt; pants;</li>
 * <li>private ArrayList&lt;Fireball&gt; fireballs;</li>
 * <li>private ArrayList&lt;Fire&gt; fires;</li>
 * </ul></tt>
 */
public class Level {
	/**
	 * This constructor initializes a new Level object, so that the GameEngine
	 * can begin calling its update() method to advance the game's play. In the
	 * process of this initialization, all of the objects in the current level
	 * should be instantiated and initialized to their beginning states.
	 * 
	 * @param randGen
	 *            is the only Random number generator that should be used
	 *            throughout this level, by the Level itself and all of the
	 *            Objects within.
	 * @param level
	 *            is a string that either contains the word "RANDOM", or the
	 *            contents of a level file that should be loaded and played.
	 */
	// Allows the Level class to access the Hero class
	private Hero hero;

	// Allows the Level class to access the Hero class
	private Water[] water;

	// Creates an ArrayList that will create randomly positioned Pant objects
	private ArrayList<Pant> pants;

	// Creates an ArrayList that will create randomly positioned Fireball
	// objects
	private ArrayList<Fireball> fireballs;

	// Creates an ArrayList that will create randomly positioned Fire objects
	private ArrayList<Fire> fires;

	// Creates a random number generator
	private Random randGen;

	// Constructor
	public Level(Random randGen, String level) {

		if (level == "RANDOM") {
			createRandomLevel();
		} else {
			loadLevel(level);
		}
	}

	/**
	 * The GameEngine calls this method repeatedly to update all of the objects
	 * within your game, and to enforce all of the rules of your game.
	 * 
	 * @param time
	 *            is the time in milliseconds that have elapsed since the last
	 *            time this method was called. This can be used to control the
	 *            speed that objects are moving within your game.
	 * @return When this method returns "QUIT" the game will end after a short 3
	 *         second pause and a message indicating that the player has lost.
	 *         When this method returns "ADVANCE", a short pause and win message
	 *         will be followed by the creation of a new level which replaces
	 *         this one. When this method returns anything else (including
	 *         "CONTINUE"), the GameEngine will simply continue to call this
	 *         update() method as usual.
	 */
	public String update(int time) {

		// Calls the hero graphic with its designated parameters
		hero.update(time, water);

		// If the water array is not null, then the water graphic will be called
		if (water != null) {
			for (int i = 0; i < water.length; i++) {
				if (water[i] != null) {
					water[i] = water[i].update(time);
				}
			}
		}

		for (int i = 0; i < pants.size(); i++) {
			if (pants.get(i) != null) {
				pants.get(i).update(time);
			}
		}

		for (int i = 0; i < fireballs.size(); i++) {
			if (fireballs.get(i) != null) {
				fireballs.get(i).update(time);
			}
		}

		for (int i = 0; i < fires.size(); i++) {
			if (fires.get(i) != null) {
				Fireball storedFireball = fires.get(i).update(time);
				if (storedFireball != null) {
					fireballs.add(storedFireball);
				}
			}
		}

		// Boolean variable to store the handleFireBallCollsions method
		boolean collision;

		// If the hero collides with a fireball, then the game quits
		collision = hero.handleFireballCollisions(fireballs);
		if (collision == true) {
			return "QUIT";
		}

		// Handles Fireball-Water collisions
		// We are decrementing i because we're changing the size of the
		// ArrayList and if we didn't we would be repeating indexes
		for (int i = 0; i < fireballs.size(); i++) {
			fireballs.get(i).handleWaterCollisions(water);
			if (fireballs.get(i).shouldRemove()) {
				fireballs.remove(i);
				i--;
			}
		}

		// Handles Fire-Water collisions
		for (int i = 0; i < fires.size(); i++) {
			fires.get(i).handleWaterCollisions(water);
			if (fires.get(i).shouldRemove()) {
				fires.remove(i);
				i--;

				if (fires.size() == 0) {
					return "ADVANCE";
				}
			}
		}

		// Handles Fireball-Pant collisions
		for (int i = 0; i < pants.size(); i++) {
			if (pants.get(i).handleFireballCollisions(fireballs) != null) {
				fires.add(pants.get(i).handleFireballCollisions(fireballs));

				if (pants.get(i).shouldRemove()) {
					pants.remove(i);
				}

				if (pants.size() == 0) {
					return "QUIT";
				}

			}
		}

		return "CONTINUE";
	}

	/**
	 * This method returns a string of text that will be displayed in the upper
	 * left hand corner of the game window. Ultimately this text should convey
	 * the number of unburned pants and fires remaining in the level. However,
	 * this may also be useful for temporarily displaying messages that help you
	 * to debug your game.
	 * 
	 * @return a string of text to be displayed in the upper-left hand corner of
	 *         the screen by the GameEngine.
	 */
	public String getHUDMessage() {

		// This prints the number of pants left and the number of fires left on
		// the top left of the screen
		return "Pants Left: " + pants.size() + "\n" + "Fires Left: "
				+ fires.size();

	}

	/**
	 * This method creates a random level consisting of a single Hero centered
	 * in the middle of the screen, along with 6 randomly positioned Fires, and
	 * 20 randomly positioned Pants.
	 */

	public void createRandomLevel() {

		// This makes sure that randGen does not equal null
		randGen = new Random();

		// Sets the parameters of the hero graphic
		hero = new Hero(GameEngine.getWidth() / 2, GameEngine.getHeight() / 2,
				this.randGen.nextInt(3) + 1);

		// Sets the parameters of the water graphic
		water = new Water[8];

		// Initializes ArrayList pants
		pants = new ArrayList<Pant>();

		// Initializes ArrayList fireballs
		fireballs = new ArrayList<Fireball>();

		// Initializes ArrayList fires
		fires = new ArrayList<Fire>();

		// This iterates through the pants ArrayList
		for (int i = 0; i < 20; i++) {
			// Minimum x value that the pant graphic can be placed at
			float pantsMinX = 0;

			// Maximum x value that the pant graphic can be placed at
			float pantsMaxX = GameEngine.getWidth();

			// Minimum y value that the pant graphic can be placed at
			float pantsMinY = 0;

			// Maximum y value that the pant graphic can be placed at
			float pantsMaxY = GameEngine.getHeight();

			// Determines the x value of the pant graphic
			float pantsX = this.randGen.nextFloat() * (pantsMaxX - pantsMinX)
					+ pantsMinX;

			// Determines the y value of the pant graphic
			float pantsY = this.randGen.nextFloat() * (pantsMaxY - pantsMinY)
					+ pantsMinY;

			// Adds 20 pant graphics to the board
			pants.add(new Pant(pantsX, pantsY, this.randGen));
		}

		// This iterates through the fire ArrayList
		for (int i = 0; i < 6; i++) {
			// Minimum x value that the fire graphic can be placed at
			float firesMinX = 0;

			// Maximum x value that the fire graphic can be placed at
			float firesMaxX = GameEngine.getWidth();

			// Minimum y value that the fire graphic can be placed at
			float firesMinY = 0;

			// Maximum y value that the fire graphic can be placed at
			float firesMaxY = GameEngine.getHeight();

			// Determines the x value of the pant graphic
			float firesX = this.randGen.nextFloat() * (firesMaxX - firesMinX)
					+ firesMinX;

			// Determines the y value of the pant graphic
			float firesY = this.randGen.nextFloat() * (firesMaxY - firesMinY)
					+ firesMinY;

			// Adds 6 pant graphics to the board
			fires.add(new Fire(firesX, firesY, this.randGen));
		}
	}

	/**
	 * This method initializes the current game according to the Object location
	 * descriptions within the level parameter.
	 * 
	 * @param level
	 *            is a string containing the contents of a custom level file
	 *            that is read in by the GameEngine. The contents of this file
	 *            are then passed to Level through its Constructor, and then
	 *            passed from there to here when a custom level is loaded. You
	 *            can see the text within these level files by dragging them
	 *            onto the code editing view in Eclipse, or by printing out the
	 *            contents of this level parameter. Try looking through a few of
	 *            the provided level files to see how they are formatted. The
	 *            first line is always the "ControlType: #" where # is either 1,
	 *            2, or 3. Subsequent lines describe an object TYPE, along with
	 *            an X and Y position, formatted as: "TYPE @ X, Y". This method
	 *            should instantiate and initialize a new object of the correct
	 *            type and at the correct position for each such line in the
	 *            level String.
	 */

	public void loadLevel(String level) {
		randGen = new Random();

		// Sets the parameters of the water graphic
		water = new Water[8];

		// Initializes ArrayList pants
		pants = new ArrayList<Pant>();

		// Initializes ArrayList fireballs
		fireballs = new ArrayList<Fireball>();

		// Initializes ArrayList fires
		fires = new ArrayList<Fire>();

		// String containing the word FIRE
		final String FIRE = "FIRE";

		// String containing the word HERO
		final String HERO = "HERO";

		// String containing the word PANT
		final String PANT = "PANT";

		// Scanner
		Scanner scnr = new Scanner(level);

		// Stores the whole line into a string
		String line = scnr.nextLine();

		// The line is split into a String array (ex: [ControlType: ,1])
		String[] controlTypeLine = line.split(" ");

		// Takes the number out of the string and turns it into an int
		int controlTypeNumber = Integer.parseInt(controlTypeLine[1]);

		// While the scanner has more lines to read, this code will run
		while (scnr.hasNextLine()) {

			// This goes to the next line in the file
			line = scnr.nextLine();

			// This is checking if the line is starting with FIRE, and then if
			// it does, then the line is split into two, and then the numbers
			// (coordinates) are taken out and cast as an integer
			if (line.startsWith("FIRE")) {
				String fireCoordinates = line.substring(FIRE.length() + 3)
						.trim();
				String[] fireCoordinatesArray = fireCoordinates.split(",");
				float firesXCoordinate = Float
						.parseFloat(fireCoordinatesArray[0].trim());
				float firesYCoordinate = Float
						.parseFloat(fireCoordinatesArray[1].trim());

				// This is making the position of where the fires are located
				fires.add(new Fire(firesXCoordinate, firesYCoordinate,
						this.randGen));

			// This is checking if the line is starting with PANT
			} else if (line.startsWith(PANT)) {
				String pantCoordinates = line.substring(PANT.length() + 3)
						.trim();
				String[] pantCoordinatesArray = pantCoordinates.split(",");
				float pantXCoordinate = Float
						.parseFloat(pantCoordinatesArray[0].trim());
				float pantYCoordinate = Float
						.parseFloat(pantCoordinatesArray[1].trim());
				
				// This is making the position of where the pants are located
				pants.add(new Pant(pantXCoordinate, pantYCoordinate,
						this.randGen));

			// This is checking if the line is starting with HERO
			} else if (line.startsWith(HERO)) {
				String heroCoordinates = line.substring(HERO.length() + 3)
						.trim();
				String[] heroCoordinatesArray = heroCoordinates.split(",");
				float heroXCoordinate = Float
						.parseFloat(heroCoordinatesArray[0]);
				float heroYCoordinate = Float
						.parseFloat(heroCoordinatesArray[1]);

				// This is creating the hero object
				hero = new Hero(heroXCoordinate, heroYCoordinate,
						controlTypeNumber);

			}
		}
	}

	/**
	 * This method creates and runs a new GameEngine with its first Level. Any
	 * command line arguments passed into this program are treated as a list of
	 * custom level filenames that should be played in a particular order.
	 * 
	 * @param args
	 *            is the sequence of custom level files to play through.
	 */
	public static void main(String[] args) {
		GameEngine.start(null, args);
	}
}

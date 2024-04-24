/**
 * @author: Nicolas Gugliemo
 * @vertion 1.0
 * @date: 01/30/2023(Start Date)
 * Current date: 05/08/2023
 * End Date/Turn in: 05/10/23
 * This program is the final project for Sac state's csc 130 Data structures and algorithms class. The class was taught by Professor Phillips.
 * The point of the program was to make a top down, single screen video game that demonstrates a few different ideas taught in the class. We must
 * have a person that with walking animations that can be controlled with the keyboard. 2 intractable objects in the game that do something when
 * a button is pressed by them and the person is facing them. The edge and the floor of the screen must be painted with some sort of art to simulate
 * a play area. There must be collision that prevents the player from walking out of the map and the intractable must also have collision. Furthermore,
 * the intractable must display a description of the sign when interacted with and the program must contain at least one data structure. Finally, the
 * program must have a collision class that holds the bounding boxes of the collectibles neatly. This program was fun to play around with and taught me a lot
 * about not only java, but the fundamentals of data structures as a whole. Note there is an known error, if too many inputs are taken in while a collision
 * is detected, the character while move quickly out of bounce.
 */

package Main;
import java.awt.*;
import java.util.*;
import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

/**
 * The main class is where most of the program takes place. This includes all calls to other methods and classes,
 * static methods, and static variables needed for the program. The only thing not included is the other public classes
 * that when called upon here, make the program whole.
 */
public class Main{
	public static stopWatchX timer = new stopWatchX(200);				// The timer is used for controlling the speed for animation frames
	public static ArrayList<spriteInfo> spritesForward = new ArrayList<>();		// Used to store walking up animation frames
	public static ArrayList<spriteInfo> spritesLeft = new ArrayList<>();		// Used to store walking left animation frames
	public static ArrayList<spriteInfo> spritesRight = new ArrayList<>();		// Used to store walking right animation frames
	public static ArrayList<spriteInfo> spritesDown = new ArrayList<>();		// Used to store walking down animation frames
	public static ArrayList<CollisionNew> collisionCollection = new ArrayList<>();// Used to store all collisions created
	public static int currentSpriteIndex = 0;									// Keeps track of the index of the arrayList
	public static String trigger = "";											// String for key pressed (Key Processor)
	public static String direction ="";											// String to keep track of the current direction (Key Processor)
	public static boolean movement = true;										// Boolean to keep track if there is movement being inputted by user
	public static EZFileRead file = new EZFileRead("dialogue.txt");		// File object for reading sign dialogue
	static HashMap<String, String> map = new HashMap<>();						// Map data structure to pull dialogue and assign it a key/value
	public static int xDirection = 500;											// Starting x direction
	public static int yDirection = 300;											// Starting y direction
	public static boolean sign1 =false;											// Flag to keep track if sign1 has been interacted with
	public static boolean sign2 =false;											// Flag to keep track if sign2 has been interacted with
	public static int randomNumberOne;			// Used to pick a random dialogue from map so the game is not repetitive
	public static int randomNumberTwo;			// Used to pick a random dialogue from map so the game is not repetitive (Ironic comment...)
	public static String lastDirection = "Down";			// Used to keep track of the last direction inputted by user
	public static boolean isCollision = false;				// Used to keep track if there has been collision

	/**
	 * Main method to used to set up the Control object which is used all over the program. It is how we are able to draw to the screen and
	 * other things. Game loop is from the gaming library and was provided by the professor to make the game work correctly. These two lines
	 * of code shall not be removed!
	 * @param args Java required argument
	 */

	public static void main(String[] args) {
		Control ctrl = new Control();
		ctrl.gameLoop();
	}

	/**
	 * The start method is used to set up the game before it runs. The game loop will run after anything in this method is first set up.
	 * This is where the animation frames are added to their respected arrayLists, load the sign dialogue into a map data structure, and
	 * randomize what is on the sign for the user to discover. Calls can not be made here as instructed by the professor.
	 */

	public static void start() {
		//Load animation frames
		int i =1;
		while(i<5) {
			spritesForward.add(new spriteInfo(new Vector2D(500, 500), "walkforward"+i));
			spritesRight.add(new spriteInfo(new Vector2D(500, 500), "walkright"+i));
			spritesLeft.add(new spriteInfo(new Vector2D(500, 500), "walkleft"+i));
			spritesDown.add(new spriteInfo(new Vector2D(500, 500), "walkdown"+i));
			i++;
		}

		//Load text into map from file
		String tempString;
		int m=0;
		while(m<5) {
			tempString = file.getNextLine();
			StringTokenizer st = new StringTokenizer(tempString, "*");
			String key = st.nextToken();
			String value = st.nextToken();
			map.put(key,value);
			m++;
		}

		//Make random numbers so random text is displayed
		Random random = new Random();
		randomNumberOne= random.nextInt(5 - 1) + 1;
		Random random2 = new Random();
		randomNumberTwo= random2.nextInt(5 - 1) + 1;
	}

	/**
	 * The update method is what is run ever single frame of the game. This is where a chunk of the code is written and most logic
	 * stems from this method. The method creates all collision objects and adds them to a collection. The method takes in the
	 * Control object made earlier and is used to draw the game to the screen, draw the person to the screen, reset the timer for
	 * animation, reset the index when timer is up, display cords for the person, take user input and do something with it, check
	 * for collision, check for sign interactions, and check for win conditions.
	 * @param ctrl Control object needed for method to function
	 */

	public static void update(Control ctrl) {
		//Creating all collision objects
		 CollisionNew TreeUp = new CollisionNew(0,1280,128,0);				// Top collision box object
		 CollisionNew TreeRight = new CollisionNew( 0, 1280,720,1175);		// Right collision box object
		 CollisionNew TreeBottom = new CollisionNew( 595, 1280,720,0);		// Bottom collision box object
		 CollisionNew TreeLeft = new CollisionNew( 0, 128,720,0);			// Left collision box object
		 CollisionNew Sign1 = new CollisionNew(128,200,200,170);			// Sign 1 collision box object
		 CollisionNew Sign2 = new CollisionNew(510,1152,592,1100);			// Sign 2 collision box object
		 CollisionNew player = new CollisionNew(yDirection, xDirection+128, yDirection+128, xDirection);		// The players box object

		//Adds collision objects into arrayList to hold
		collisionCollection.add(TreeUp);
		collisionCollection.add(TreeLeft);
		collisionCollection.add(TreeRight);
		collisionCollection.add(TreeBottom);
		collisionCollection.add(Sign1);
		collisionCollection.add(Sign2);

		//Players Bounding box which is updated every loop
		player.setBoxX1(xDirection);
		player.setBoxX2(xDirection+128);
		player.setBoxY1(yDirection);
		player.setBoxY2(yDirection+128);

		//The players bounding box is drawn for visual/testing purposes
		//ctrl.drawString(xDirection+64,player.boxY1, String.valueOf(player.boxY1), Color.RED); 	//top
		//ctrl.drawString(xDirection+64,player.boxY2, String.valueOf(player.boxY2), Color.GREEN); 	//Bottom
		//ctrl.drawString(player.boxX1,yDirection+64, String.valueOf(player.boxX1), Color.BLUE); 	//Left
		//ctrl.drawString(player.boxX2,yDirection+64, String.valueOf(player.boxX2), Color.YELLOW); //Right

		//Draw the background art to the screen
		ctrl.addSpriteToFrontBuffer(0,0,"background");

		//Display current character position and key pressed by user for testing purposes
		//ctrl.drawString(150, 400, "x= " + xDirection, Color.RED);
		//ctrl.drawString(150, 450, "y= " + yDirection, Color.RED);
		//ctrl.drawString(150, 500, trigger, Color.RED);

		//When the timer is up and there is movement but no collision, update the direction
		// depending on what key is inputted. The timer is reset and index is updated.
		if (timer.isTimeUp()) {
			if (movement) {
				if (!isCollision) {
					if (direction.equalsIgnoreCase("Up")) {
						yDirection = yDirection - 20;
					}
					if (direction.equalsIgnoreCase("Down")) {
						yDirection = yDirection + 20;
					}
					if (direction.equalsIgnoreCase("Left")) {
						xDirection = xDirection - 20;
					}
					if (direction.equalsIgnoreCase("Right")) {
						xDirection = xDirection + 20;
					}
					currentSpriteIndex++;
					timer.resetWatch();
				}
			}
		}

		//If index is larger than the arrayList, it means it reached the end, send it back to 0
		if (currentSpriteIndex>=3) {
			currentSpriteIndex =0;
		}

		//Loops through all collision objects to find collision
		CollisionLoop(player);

		// Switch statement used to add logic to user inputted key; deals with animation, collision, and movement.
		// If there is collision, a different route is taken. Last direction used to check for sign direction
			switch (direction) {
				case "Up":
					if (movement) {
						lastDirection = "Up";
						if (!isCollision) {
							ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesForward.get(currentSpriteIndex).getTag());
						} else {
							ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesForward.get(currentSpriteIndex).getTag());
							yDirection=yDirection+30;
							isCollision= false;
						}
					}
					break;
				case "Left":
					if (movement) {
						lastDirection = "left";
						if (!isCollision) {
							ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesLeft.get(currentSpriteIndex).getTag());
						} else {
							ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesLeft.get(currentSpriteIndex).getTag());
							xDirection=xDirection+30;
							isCollision= false;
						}
					}
					break;
				case "Down":
					if (movement) {
						lastDirection = "Down";
						if (!isCollision) {
							ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesDown.get(currentSpriteIndex).getTag());
						} else {
							ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesDown.get(currentSpriteIndex).getTag());
							yDirection=yDirection-30;
							isCollision= false;
						}
					}
					break;
				case "Right":
					if (movement) {
						lastDirection = "Right";
						if (!isCollision) {
							ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesRight.get(currentSpriteIndex).getTag());
						} else {
							ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesRight.get(currentSpriteIndex).getTag());
							xDirection=xDirection-30;
							isCollision= false;
						}
					}
					break;
				case "Space":
						ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesDown.get(0).getTag());
						break;
		}

		// If there is no movement, show the default stance
		if (!movement) {
				if (direction.equalsIgnoreCase("Up")) {
					ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesForward.get(0).getTag());
				}
				if (direction.equalsIgnoreCase("Down")) {
					ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesDown.get(0).getTag());
				}
				if (direction.equalsIgnoreCase("Left")) {
					ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesLeft.get(0).getTag());
				}
				if (direction.equalsIgnoreCase("Right")) {
					ctrl.addSpriteToFrontBuffer(xDirection, yDirection, spritesRight.get(0).getTag());
			}
		}

		// The following is checking if 1 of the signs are pressed, or both are pressed and the user wins
		if (sign1Trigger()) {
			ctrl.drawString(400,300,map.get("string" +randomNumberOne) , Color.BLACK);
		}
		if (sign2Trigger()) {
			ctrl.drawString(400,300,map.get("string" +randomNumberTwo) , Color.BLACK);
		}
		if (win()) {
			ctrl.drawString(400, 400,"YOU WIN", Color.YELLOW);
		}

		//If the sign is pressed, a checkmark is drawn to a box for the user to check their goals.
		if (sign1) {
			ctrl.addSpriteToFrontBuffer(550,100,"check");
		}
		if (sign2) {
			ctrl.addSpriteToFrontBuffer(600,100,"check2");
		}
		isCollision= false;		// Reset collision for next loop
	}

	/**
	 * The collision method deals with checking if the user is colliding with any of the bounding boxes set up.
	 * If they are, it returns true and the switch statement above deals with it.
	 * @return True if collision is detected, false if not.
	 */
	public static boolean Collision(CollisionNew player, CollisionNew box) {

		if (player.boxX1 > box.boxX2) {
			return false;
		}
		if (player.boxX2 < box.boxX1) {
			return false;
		}
		if (player.boxY1 > box.boxY2) {
			return false;
		}
		if (player.boxY2 < box.boxY1) {
			return false;
		}
		return true;				//Collision
}

	/**
	 * Collision Loop goes through all collision objects and checks the player against the boxes.
	 * If any collision is detected, isCollision is changed to true.
	 * @param player The player's bounding box
	 */
	public static void CollisionLoop(CollisionNew player) {
		int i =0;
		while (i < collisionCollection.size()) {
			if (Collision(player, collisionCollection.get(i))) {
				isCollision = true;
				break;
			}
		i++;
		}
	}

	/**
	 * Sign 1 trigger tests if the user is near the sign and looking at it to allow interaction.
	 * @return True if the user passes the tests and is pressing space, false if not.
	 */
	public static boolean sign1Trigger () {		//Left Sign (Right side or bottom can be interacted with)
		if (xDirection>128 && xDirection<250 && yDirection>128 && yDirection<250 && direction.equalsIgnoreCase("Space")) {
			if (xDirection>190 && (lastDirection.equalsIgnoreCase("Left"))) {
				sign1 = true;
				return true;
			}
			if (xDirection<190 && (lastDirection.equalsIgnoreCase("Up"))) {
				sign1 = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * Sign 2 trigger tests if the user is near the sign and looking at it to allow interaction.
	 * @return True if the user passes the tests and is pressing space, false if not.
	 */
	public static boolean sign2Trigger () {		// Right Sign (Top or Left side can be interacted with)
		if (xDirection > 900 && xDirection < 1216 && yDirection > 350 && yDirection < 656 && direction.equalsIgnoreCase("Space")) {
			if (xDirection<1000 &&lastDirection.equalsIgnoreCase("Right")) {
				sign2 = true;
				return true;
			}
			if (xDirection>1000 && lastDirection.equalsIgnoreCase("Down")) {
				sign2 = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * This method checks to see if both signs were interacted with, if so user wins!
	 * @return True if both signs were interacted with, false if not
	 */
	public static boolean win () {
		return sign1 && sign2;
	}
}
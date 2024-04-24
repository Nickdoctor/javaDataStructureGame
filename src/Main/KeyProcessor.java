/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

import java.awt.event.KeyEvent;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	
	// Static Method(s)
	public static void processKey(char key) {
		if (key == ' '){
		Main.movement = false;		// When there is no key input, stop movement
		return;
	}
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
			case '$':
				Main.trigger = "Space Bar is triggered";
				Main.direction = "Space";
				break;
			case'w':
				Main.trigger ="w key is triggered";
				Main.direction ="Up";
				Main.movement=true;
				break;
			case'a':
				Main.trigger ="a key is triggered";
				Main.direction="Left";
				Main.movement=true;
				break;
			case's':
				Main.trigger ="s key is triggered";
				Main.direction="Down";
				Main.movement=true;
				break;
			case'd':
				Main.trigger ="d key is triggered";
				Main.direction="Right";
				Main.movement=true;
				break;
		}
	}
}
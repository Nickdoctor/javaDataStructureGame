/* This class is used to give you a handy way to pass a pair of 2D coordinates as a single object. The behavior (methods) of the class should allow for robust options in the future. */

package Data;

public class Vector2D {
	// Fields
	private int x;
	private int y;
		// TODO: Add private class fields to store x and y values given in class constructor
	
	// Constructor
	public Vector2D(int x, int y){
		this.x = x;
		this.y =y;
		// TODO: Save the constructor parameters into class fields
	}
	
	// Methods
	public int getX(){
		// TODO: Remove my placeholder code below (which is there to prevent an error) and replace it with returning the value of your private field x
		return this.x;
	}
	
	public int getY(){
		// TODO: Remove my placeholder code below (which is there to prevent an error) and replace it with returning the value of your private field y
		return this.y;
	}
	
	public void setX(int newX){
		this.x = newX;
		// TODO: Update the value of x to be the value in newX (Absolute assignment)
	}
	
	public void setY(int newY){
		this.y = newY;
		// TODO: Update the value of y to be the value in newY (Absolute assignment)
	}
	
	public void adjustX(int adjustment){
		this.x = this.x + adjustment;
		// TODO: Change the previous value of x by adding the adjustment to the previous value (Relative assignment)
		// Backward adjustments can be made by passing a negative number as an adjustment
	}
	
	public void adjustY(int adjustment){
		this.y = this.y +adjustment;
		// TODO: Change the previous value of y by adding the adjustment to the previous value (Relative assignment)
		// Backward adjustments can be made by passing a negative number as an adjustment
	}
}

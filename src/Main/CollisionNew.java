/**
 * CollisionNew is an updated version of my old class that stores collision cords neatly. These values are used later to check
 * for collisions.
 */
package Main;
public class CollisionNew {
    public  int boxX1;  //   Left Wall
    public  int boxX2;  //   Right Wall
    public  int boxY1;  //   Top Wall
    public  int boxY2;  //    Bottom Wall

    /**
     * Constructor for the class
     * @param top   Top cord
     * @param right Right cord
     * @param bottom Bottom Cord
     * @param left  Left Cord
     */
    public CollisionNew(int top, int right, int bottom, int left) {
        boxY1 = top;
        boxX2 = right;
        boxY2 = bottom;
        boxX1 = left;
    }

    //The following are the setters and getters of the class
    public int getBoxX1() {
        return boxX1;
    }

    public void setBoxX1(int boxX1) {
        this.boxX1 = boxX1;
    }

    public int getBoxX2() {
        return boxX2;
    }

    public void setBoxX2(int boxX2) {
        this.boxX2 = boxX2;
    }

    public int getBoxY1() {
        return boxY1;
    }

    public void setBoxY1(int boxY1) {
        this.boxY1 = boxY1;
    }

    public int getBoxY2() {
        return boxY2;
    }

    public void setBoxY2(int boxY2) {
        this.boxY2 = boxY2;
    }
}

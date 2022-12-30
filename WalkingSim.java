//////////////// FILE HEADER //////////////////////////
//
// Title: WALKING SIMULATOR PROJECT
// Course: CS 300 Fall 2022
//
// Author: VARDAAN KAPOOR
// Email: vkapoor5@wisc.edu
// Lecturer: Professor Hobbes LeGault
//
///////////////////////////////////////////////////////////////////////////////
/*
 * This class produces a walking simulation of a walker using images just like an animation using
 * images.We develop some methods and use pre defined classes to achieve this simulation
 */
import java.util.Random;
import java.io.File;
import processing.core.PImage;

public class WalkingSim {
  private static Random randGen;
  private static int bgColor;
  public static Walker[] walkers;

  private static PImage[] frames;

  /*
   * setup method sets up the basic functionality of the class and defines the variables to be used
   * It produces an array of walker objects among many other tasks
   */
  public static void setup() {
    // if we add a print statement, then it is printed only once
    randGen = new Random();
    bgColor = randGen.nextInt();
    walkers = new Walker[8];


    frames = new PImage[Walker.NUM_FRAMES];
    for (int i = 0; i < frames.length; i++) {
      frames[i] = Utility.loadImage("images" + File.separator + "walk-" + i + ".png");
    }
    int randnum = randGen.nextInt(1, walkers.length + 1);
    for (int i = 0; i < randnum; i++) {
      walkers[i] =
          new Walker(randGen.nextInt(0, Utility.width()), randGen.nextInt(0, Utility.height()));
    }

  }

  /*
   * draw method draws the walking simulation and is not called by us It is automaticaly called many
   * times to draw individual pieces of our simulation window
   */
  public static void draw() {
    // if we add print statement, then statement is printed many times
    Utility.background(bgColor);
    int i;
    /*
     * code for getting started with gpu and its classes
     * 
     * Utility.image(frames[6], 400, 300);
     */

    for (i = 0; i < walkers.length; i++) {
      if (walkers[i] != null) {
        Utility.image(frames[walkers[i].getCurrentFrame()], walkers[i].getPositionX(),
            walkers[i].getPositionY());// we give position coordinates and a number for current
                                       // frame
      }
    }

    /*
     * for( i=0;i<walkers.length;i++) { if(walkers[i]!=null && walkers[i].isWalking()==false) {
     * Utility.image(frames[walkers[i].getCurrentFrame()],walkers[i].getPositionX(),
     * walkers[i].getPositionY()); } }
     */

    for (i = 0; i < walkers.length; i++) {
      if (walkers[i] != null && walkers[i].isWalking() == true) {

        Utility.image(frames[walkers[i].getCurrentFrame()], walkers[i].getPositionX(),
            walkers[i].getPositionY());
        if (walkers[i].getPositionX() <= Utility.width() - 3) {
          walkers[i].setPositionX(walkers[i].getPositionX() + 3);
        } else {
          walkers[i].setPositionX(0);
        }
      }
    }

    /*
     * implementation check code boolean y=false; for(int k=0;k<4;k++) { for(int
     * l=0;l<walkers.length;l++) { if(walkers[l]!=null && isMouseOver(walkers[l])==true) { y=true; }
     * } if(y==true) { System.out.println("you are on a walker"); } else {
     * System.out.println("you are not on  a walker"); } }
     */
    /*
     * System.out.println(isMouseOver(walkers[0]));
     */
    /*
     * for(int j=0;j<5;j++) { if(isMouseOver(walkers[i-1])) {
     * System.out.println("mouse is on a walker"); } }
     */
    /*
     * mousePressed(); for(int ll=0;ll<walkers.length;ll++) { if(walkers[ll]!=null &&
     * walkers[ll].isWalking()) { walkers[ll].update(); } }
     */
    for (int ll = 0; ll < walkers.length; ll++) {
      if (walkers[ll] != null && walkers[ll].isWalking()) {
        walkers[ll].update();// we update our walker's status slowly
      }
    }
  }

  /*
   * key pressed does some tasks when a particular key is pressed It is not called by us
   */
  public static void keyPressed(char c) {
    if (c == 'a' || c == 'A') {
      for (int i = 0; i < walkers.length; i++) {
        if (walkers[i] == null) {

          walkers[i] =
              new Walker(randGen.nextInt(0, Utility.width()), randGen.nextInt(0, Utility.height()));
          // we only add till the length of array is reached
          break;
        }
      }
    }
    if (c == 's' || c == 'S') {
      for (int j = 0; j < walkers.length; j++) {
        if (walkers[j] != null && walkers[j].isWalking() == true) {
          walkers[j].setWalking(false);
        }
      }
    }
  }

  /*
   * mouse pressed is not called by us It i autmatically called when a mouse is pressed and we get
   * the mouse coordinates
   */
  public static void mousePressed() {

    for (int i = 0; i < walkers.length; i++) {
      if (walkers[i] != null) {
        int mousex = Utility.mouseX();
        int mousey = Utility.mouseY();
        int width_of_image = frames[0].width;
        int height_of_image = frames[0].height;
        int x_of_w = (int) walkers[i].getPositionX();
        int y_of_w = (int) walkers[i].getPositionY();
        int x_lendpoint = x_of_w - (width_of_image / 2);
        int x_uendpoint = x_of_w + (width_of_image / 2);
        int y_lendpoint = y_of_w - (height_of_image / 2);
        int y_uendpoint = y_of_w + (height_of_image / 2);
        if (walkers[i].isWalking() != true && mousex > x_lendpoint && mousex < x_uendpoint
            && mousey > y_lendpoint && mousey < y_uendpoint) {
          walkers[i].setWalking(true);
          // in this condition we check if our moue pointer is inside an imaginary box used by an
          // image
        }
      }
    }


  }

  /*
   * mouse over is also not called by us It runs when mouse hovers over something on the screen
   */
  public static boolean isMouseOver(Walker w) {
    int mousex = Utility.mouseX();
    int mousey = Utility.mouseY();
    int width_of_image = frames[0].width;
    int height_of_image = frames[0].height;
    int x_of_w = (int) w.getPositionX();
    int y_of_w = (int) w.getPositionY();
    int x_lendpoint = x_of_w - (width_of_image / 2);
    int x_uendpoint = x_of_w + (width_of_image / 2);
    int y_lendpoint = y_of_w - (height_of_image / 2);
    int y_uendpoint = y_of_w + (height_of_image / 2);
    if (mousex > x_lendpoint && mousex < x_uendpoint && mousey > y_lendpoint
        && mousey < y_uendpoint) {
      return true;
    }
    return false;
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub


    Utility.runApplication();

    /*
     * if(isMouseOver(walkers[0])) { System.out.println("you are on a walker"); }
     */
  }

}

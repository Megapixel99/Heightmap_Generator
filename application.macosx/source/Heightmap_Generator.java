import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Heightmap_Generator extends PApplet {

int cols, rows;

float flying = 0;

float[][] terrain;

float SALT = .009f;

public void setup() {
  
  cols = width;
  rows = height;
  terrain = new float[cols][rows];
}

public void mouseClicked(){
  saveFrame();
}

public void draw() {
  background(0);
  loadPixels();
  float yoff  = 0;
  for (int y = 1; y < rows - 1; y++) {
    float xoff = 0;
    for (int x = 1; x < cols - 1; x++) {
      terrain[x][y] = map(noise(xoff, yoff), 0, 1, -500, 500);
      xoff += SALT;

      terrain[x][y] -= terrain[x][y]/5;

      if (terrain[x][y] <= 25) {
        terrain[x][y] += (terrain[x-1][y] + 
          terrain[x+1][y] + 
          terrain[x][y-1] + 
          terrain[x][y+1]) / 256;
      }
      
      int index = x + y * cols;
      pixels[index] = color(terrain[x][y]);
    }
    yoff += SALT;
  }

  updatePixels();
}
  public void settings() {  size(900, 900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Heightmap_Generator" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

int cols, rows;

float flying = 0;

float[][] terrain;

float SALT = .005;

void setup() {
  size(900, 900);
  cols = width;
  rows = height;
  terrain = new float[cols][rows];
}

void mouseClicked(){
  saveFrame();
}

void draw() {
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

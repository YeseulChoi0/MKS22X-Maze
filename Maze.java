import java.util.*;
import java.io.*;
public class Maze{

    private char[][]maze;
    private boolean animate;//false by default
    private int lengthPath = 0;
    private boolean found = false;

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)
      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
         throw a FileNotFoundException or IllegalStateException
    */
    public Maze(String filename) throws FileNotFoundException{
      int numRows = 0;
      int numCols = 0;

      File file = new File(filename);
      Scanner scan = new Scanner(file);
      Scanner findLength = new Scanner(file);

      while (findLength.hasNextLine()){
        findLength.nextLine();
        numRows++;
      }

      String s = scan.nextLine();
      numCols = s.length();

      maze = new char[numRows][numCols];
      for (int i = 0; i < s.length(); i++){
        maze[0][i] = s.charAt(i);
      }
      for (int line = 1; scan.hasNextLine(); line++){
        s = scan.nextLine();
        for (int i = 0; i < s.length(); i++){
          maze[line][i] = s.charAt(i);
        }
      }

      animate = false;
    }

    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }

    public void setAnimate(boolean b){
        animate = b;
    }

    public void clearTerminal(){
        //erase terminal, go to top left of screen.
        System.out.println("\033[2J\033[1;1H");
    }


    /*Wrapper Solve Function returns the helper function
      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
    */
    public int solve(){
      int startRow = 0;
      int startCol = 0;
      for (int row = 0; row < maze.length; row++){
        for (int col = 0; col < maze[0].length; col++){
          if (maze[row][col] == 'S') {
            startRow = row;
            startCol = col;
            maze[row][col] = ' ';
          }
        }
      }
      return solve(startRow, startCol);
    }

    public String toString(){
      String s = "";
      for (int row = 0; row < maze.length; row++){
        for (int col = 0; col < maze[0].length; col++){
          s += maze[row][col];
        }
        s += "\n";
      }
      return s;
    }

    /*
      Recursive Solve function:

      A solved maze has a lengthPath marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.

      Postcondition:
        The S is replaced with '@' but the 'E' is not.
        All visited spots that were not part of the solution are changed to '.'
        All visited spots that are part of the solution are changed to '@'
    */

    private int solve(int row, int col){ //you can add more parameters since this is private
      if(animate){
          clearTerminal();
          System.out.println(this);
          wait(20);
      }
      if (maze[row][col] == 'E'){
        found = true;
        return lengthPath;
      }if (maze[row][col] == '#' || maze[row][col] == '.' || maze[row][col] == '@'){
        if (found){
          return lengthPath;
        }
        return -1;
      }if (maze[row][col] == ' '){
        maze[row][col] = '@';
        lengthPath++;
        if (solve(row+1, col) != -1){
          return lengthPath;
        }if (solve(row-1, col) != -1){
          return lengthPath;
        }if (solve(row, col+1) != -1){
          return lengthPath;
        }if (solve(row, col-1) != -1){
          return lengthPath;
        }else{
          maze[row][col] = '.';
          lengthPath--;
        }
      }
      return -1;
    }

    /**

    **/

}

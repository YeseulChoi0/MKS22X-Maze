import java.io.*;
public class Driver{
    public static void main(String[]args){
      String filename = "data3.txt";
      try{
        Maze f;
        f = new Maze(filename);//true animates the maze.

        f.setAnimate(true);
        f.solve();
        System.out.println(f);
        System.out.println(f.solve());
      }catch(FileNotFoundException e){
        System.out.println("Invalid filename: "+filename);
      }
    }
}

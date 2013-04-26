package sudoku;

//Eredeti: http://www.mathcs.emory.edu/~cs170002/bookslides/html/Sudoku.html

import java.awt.Point;

public class Sudoku {

  private int[][] grid;
    
  public Sudoku(int[][] grid) {
    this.grid = gridCopy(grid);
  }

  public int[][] getGrid() {
    return grid;
  }
  
  /** Search for a solution */
  /*
  public boolean search() {
    if (!isValid(grid, false)) return false;
    List<Point> freeCellList = getFreeCellList(); // Free cells
    int k = 0; // Start from the first free cell    
    boolean found = false; // Solution found?
    //Date d = new Date();
    int counter = 0;
    while (!found) {
      counter++;
      if (counter >= 1000000)
      //if ((new Date().getTime() - d.getTime()) >= 100)
          return false;
      int i = freeCellList.get(k).x;
      int j = freeCellList.get(k).y;
      if (grid[i][j] == 0) 
        grid[i][j] = 1; // Start with 1

      if (isValid(grid, i, j)) {
        if (k + 1 == freeCellList.size()) { // No more free cells 
          found = true; // A solution is found
        }
        else { // Move to the next free cell
          k++;
        }
      }
      else if (grid[i][j] < 9) {
        grid[i][j] = grid[i][j] + 1; // Check the next possible value
      }
      else { // grid[i][j] is 9, backtrack
        while (grid[i][j] == 9) {
          grid[i][j] = 0; // Reset to free cell
          if (k == 0) {
            return false; // No possible value
          }
          k--; // Backtrack
          i = freeCellList.get(k).x;
          j = freeCellList.get(k).y;
        }

        grid[i][j] = grid[i][j] + 1; // Check the next possible value
      }
    }

    return true; // A solution is found
  }
  */
  
  private static int[][] gridCopy(int[][] grid) {
      int[][] ret = new int[grid.length][grid.length];
      for(int i = 0; i < grid.length; i++) 
          for(int j = 0; j < grid.length; j++)
              ret[i][j] = grid[i][j];
      return ret;
  }
  
  public static Point getWorstEmptyPoint(int[][] grid) {
      int[][] count = new int[9][9];
      for (int i = 0; i < 9; i++) {
          for (int j = 0; j < 9; j++) {
              if (grid[i][j] == 0) {
                for (int number = 1; number <= 9; number++) {
                    grid[i][j] = number;
                    if (isValid(grid, i, j)) count[i][j]++;
                }
                grid[i][j] = 0;
              }
          }
      }
      int max = count[0][0];
      Point p = new Point(0, 0);
      for (int i = 1; i < 9; i++) {
          for (int j = 1; j < 9; j++) {
              if (count[i][j] > max) {
                  max = count[i][j];
                  p.x = i;
                  p.y = j;
              }
          }
      }
      return p;
  }
  
  // Check whether grid[i][j] is valid at the i's row
  private static boolean isValidAtRow(int[][] array, int i, int j) {
    for (int column = 0; column < 9; column++)
      if (column != j && array[i][column] == array[i][j])
        return false;
    return true;
  }

  // Check whether grid[i][j] is valid at the j's column
  private static boolean isValidAtCol(int[][] array, int i, int j) {
    for (int row = 0; row < 9; row++)
      if (row != i && array[row][j] == array[i][j])
        return false;
    return true;
    
  }
  
  // Check whether grid[i][j] is valid in the 3 by 3 box
  private static boolean isValidAtBox(int[][] array, int i, int j) {
    for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++)
      for (int col = (j / 3) * 3; col < (j / 3) * 3 + 3; col++) 
        if (row != i && col != j && array[row][col] == array[i][j])
          return false;
    return true;
  }
  
  /** Check whether grid[i][j] is valid in the grid */
  public static boolean isValid(int[][] array, int i, int j) {
        return isValidAtCol(array, i, j) && isValidAtRow(array, i, j) && isValidAtBox(array, i, j);
  }
  
  /*
  private List<Point> getFreeCellList() {
      List<Point> list = new ArrayList<Point>();
      for (int i = 0; i < 9; i++)
          for (int j = 0; j < 9; j++)
              if (grid[i][j] == 0)
                  list.add(new Point(i, j));
      return list;
  }
  */
  /** Check whether the fixed cells are valid in the grid */
  public static boolean isValid(int[][] array, boolean skipZero) {
    for (int i = 0; i < 9; i++)
      for (int j = 0; j < 9; j++)
        if ((skipZero || array[i][j] != 0) && !isValid(array, i, j)) return false;
    return true; // The fixed cells are valid
  }
  
}
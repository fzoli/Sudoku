/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import sudoku.chan.Grid;
import sudoku.chan.SudokuSolver;

/**
 *
 * @author Zoli
 */
public class Test {
    public static void main(String[] args) {
        int[][] numbers = new int[9][9];
        numbers[0][0] = 9;
        numbers[2][2] = 9;
        Grid def = Grid.create(numbers);
        System.out.println("Feladvány: ");
        System.out.println(def);
        SudokuSolver solver = new SudokuSolver(numbers);
        System.out.println("Megoldható: "+solver.isSolveable());
        if (solver.isSolveable()) {
            System.out.println("Egyetlen megoldás van: "+solver.isUniqueSolution());
            System.out.println("Megoldások:");
        }
        for (Grid g : solver.getSolutions()) {
            System.out.println(g);
        }
    }
}
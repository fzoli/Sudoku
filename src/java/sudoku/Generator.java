package sudoku;

import sudoku.chan.SudokuSolver;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    
    private int[][] solve = new int[9][9];

    public int[][] generateTask() {
        int[][] numbers;
        SudokuSolver solver;
        boolean hasSolve;
        do {
            numbers = generateGrid();
            solver = new SudokuSolver(numbers);
            hasSolve = solver.isSolveable();
        }
        while(!hasSolve);
        solve = solver.getSolution();
        while (!solver.isUniqueSolution()) {
            Point p = Sudoku.getWorstEmptyPoint(numbers);
            numbers[p.x][p.y] = solve[p.x][p.y];
            solver = new SudokuSolver(numbers);
        }
        return numbers;
    }
    
    public int[][] getSolve() {
        return solve;
    }
    
    private List<Point> generatePositions() {
        List<Point> ret = new ArrayList<Point>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ret.add(new Point(i, j));
            }
        }
        return ret;
    }
    
    private int[][] generateGrid() {
        int[][] numbers;
        GeneratedPositionList generatedPositions = new GeneratedPositionList();
        do {
            numbers = new int[9][9];
            generatedPositions.clear();
            List<Point> points = generatePositions();
            int i = 0;
            do {
                i++;
                int position = (int)(Math.random() * points.size());
                Point p = points.get(position);
                points.remove(position);
                numbers[p.x][p.y] = random();
                generatedPositions.add(p);
            }
            while (i != 25);
        }
        while (!Sudoku.isValid(numbers, false));
        return numbers;
    }
    
    private int random() {
        return (int)(Math.random() * 9 + 1);
    }
    
    /*private void fillEmptySolve() {
        for(int i=1; i<=9; i++) {
            for(int j=1; j<=9; j++) {
                solve[i][j] = ((i-1)*3+(i-1)/3+(j-1)) % 9 +1;
            }
        }
    }*/
    
}
package sudoku;

import sudoku.chan.SudokuSolver;
import java.awt.Point;

public class Storage {

    private boolean isUnique;
    private boolean isModeLoad;
    private boolean isSolved = false;
    private Generator generator;
    private int[][] numbers;
    private int col = 0, row = 0;
    private GeneratedPositionList generatedPositions = new GeneratedPositionList();
    
    public Storage() {
        this(false);
    }

    public Storage(boolean isModeLoad) {
        this.isModeLoad = isModeLoad;
        if (isModeLoad)
            initLoadMode();
        else
            initGameMode();
    }
    
    private void initLoadMode() {
        numbers = new int[9][9];
    }
    
    private void initGameMode() {
        generator = new Generator();
        numbers = generator.generateTask();
        setGeneratedPositions();
        isUnique = true;
    }
    
    public boolean getIsUniqueSolve() {
        return getIsNotSolved() || isUnique;
    }
    
    public void setNumber(int value) {
        if (!isSolved && value >= 0 && value <= 9 && !generatedPositions.isGenerated(row, col)) {
            numbers[row][col] = value;
        }
    }
    
    private int getNumber() {
        return numbers[row][col];
    }

    public String getNumberString() {
        if (getNumber() == 0) return "";
        return String.valueOf(getNumber());
    }
    
    public String getType() {
        if (isEmpty()) return "empty";
        if (generatedPositions.isGenerated(row, col)) return "generated";
        else if (!getIsNotSolved()) return "solved";
        return Sudoku.isValid(numbers, row, col) ? "good" : "bad";
    }
    
    private boolean isEmpty() {
        return getNumber() == 0;
    }
    
    public boolean getIsNotSolved() {
        if (isSolved) return false;
        isSolved = Sudoku.isValid(numbers, true);
        return !isSolved;
    }
    
    public void setRow(int value) {
        row = value - 1;
    }
    
    public void setColumn(int value) {
        col = value - 1;
    }
    
    public void showSolve() {
        if (getIsNotSolved()) {
            numbers = trySolve();
        }
    }
    
    private int[][] trySolve() {
        int[][] solve;
        if (isModeLoad) {
                SudokuSolver solver = new SudokuSolver(numbers);
                solve = solver.isSolveable() ? solver.getSolution() : numbers;
                setGeneratedPositions();
                isUnique = solver.isUniqueSolution();
        }
        else {
            solve = generator.getSolve();
        }
        return solve;
    }
    
    public boolean getIsModeLoad() {
        return isModeLoad;
    }
    
    public boolean getIsModeLoadOrIsNotSolved() {
        return getIsModeLoad() || getIsNotSolved();
    }
    
    private void setGeneratedPositions() {
        if (!isModeLoad || Sudoku.isValid(numbers, false))
            for (int i=0; i<9; i++) 
                for (int j=0; j<9; j++) 
                    if (numbers[i][j]!=0) generatedPositions.add(new Point(i,j));
    }
    
}
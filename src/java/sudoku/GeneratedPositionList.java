package sudoku;

import java.awt.Point;
import java.util.ArrayList;

public class GeneratedPositionList extends ArrayList<Point> {
    
    public boolean isGenerated(int row, int col) {
        return contains(new Point(row, col));
    }
    
}
package sudoku;

import java.awt.Point;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Worker extends HttpServlet {
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Created on April 30, 2011 by Zoltán Farkas.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        work(request);
        redirect(response);
    }

    @Override
    public String getServletInfo() {
        return "I can save numbers, create new game and show/solve the solution if you POST one of 'save', 'new_game', 'solve' or 'load'.";
    }
    
    private void redirect(HttpServletResponse response) throws IOException {
        //getServletConfig().getServletContext().getRequestDispatcher("/index.jspx").forward(request,response);
        response.sendRedirect("index.jspx");
    }
    
    private boolean isPosted(String str, HttpServletRequest request) {
        return request.getParameter(str) != null;
    }
    // </editor-fold>
    
    private void work(HttpServletRequest request) {
        if (isPosted("new_game", request)) {
            newGame(request);
        }
        else if (isPosted("solve", request)) {
            solve(request);
        }
        else if (isPosted("save", request)) {
            save(request);
        }
        else if (isPosted("load", request)) {
            load(request);
        }
    }
    
    private void newGame(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute("storage");
    }
    
    private void solve(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Storage storage = (Storage)session.getAttribute("storage");
        if (storage != null) {
            if (storage.getIsModeLoad()) save(request);
            storage.showSolve();
        }
    }

    private void load(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Storage storage = new Storage(true);
        session.setAttribute("storage", storage);
    }
    
    protected void save(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Storage storage = (Storage)session.getAttribute("storage");
        if (storage != null) {
            int[][] postedNumbers = getPostedNumbers(request);
            for(int i = 1; i <= 9; i++) {
                for(int j = 1; j <= 9; j++) {
                    storage.setRow(i);
                    storage.setColumn(j);
                    storage.setNumber(postedNumbers[i][j]);
                }
            }
        }
    }

    private int[][] getPostedNumbers(HttpServletRequest request) {
        Enumeration<String> parameters = request.getParameterNames();
        int[][] numbers = new int[10][10];
        while(parameters.hasMoreElements()) {
            String position = parameters.nextElement();
            Point p = getPosition(position);
            try {
                numbers[p.x][p.y] = Integer.parseInt(request.getParameter(position));
            }
            catch(Exception ex) {
                ;
            }
        }
        return numbers;
    }
    
    private Point getPosition(String position) {
        if (isValid(position)) return new Point(
                                    strToInt(position, 0),
                                    strToInt(position, 2)
                                );
        return new Point(0, 0);
    }
    
    private int strToInt(String position, int index) {
            return Integer.parseInt(Character.toString(position.charAt(index)));
    }
    
    private boolean isValid(String string) {
        return string.matches("^[1-9]{1}[.]{1}[1-9]{1}$");
    }
    
}
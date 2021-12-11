package SnakeBackground;

public class SnakeBackground {
    private static final int WIDTH =800;
    private static final int HEIGHT = WIDTH;
    private static final int ROWS=20;
    private static final int COLUMNS =ROWS;
    private static final int SQUARE_SIZE= WIDTH /ROWS;
    public SnakeBackground(){

    }
    public double getWidth(){
        return WIDTH;
    }
    public double getHeight(){
        return HEIGHT;
    }
    public double getRows(){
        return ROWS;
    }
    public double getColumns(){
        return COLUMNS;
    }
    public double getSquareSize(){return SQUARE_SIZE;}
}

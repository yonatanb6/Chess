import java.util.ArrayList;

public class Bishop extends Piece {
    
    private int type = 4;
    private char color;
    private int[] position = new int[2];
    private String imagePath;
    private Board board;
    private String name = "Bishop";
    private Boolean captured = false;


    public Bishop(Board b, char color, int x, int y) {
        this.color = color;
        this.position[0] = x;
        this.position[1] = y;
        this.board = b;
        this.imagePath = this.color + "Bishop.png";
        b.squares.get(x)[y].setOccupant(this);        
    }

    /*
    * Input: None
    * Ouptput: Arraylist of integer arrays with coordinates for possible moves [xcor, ycor]
    * Description: Find and store the possible moves
    */  
    @Override
    public ArrayList<int[]> findValidMoves() {
        ArrayList<int[]> validMoves = diagonalHelperTopLeftBottomRight(position, board, color);
        validMoves.addAll(diagonalHelperBottonLeftTopRight(position, board, color));
        return validMoves;
    }

    // set captured

    public void setCaptured(boolean captureState) {
        this.captured = captureState;
    }
        
    @Override
    public int[] getPosition() {
        return position;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public char getColor() {
        return color;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Boolean getCaptured() {
        return this.captured;
    }
    
}

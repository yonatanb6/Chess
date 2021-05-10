import java.util.ArrayList;

public class Rook extends Piece {
    
    private int type = 4;
    private char color;
    private int[] position = new int[2];
    private String imagePath;
    private Board board;
    private String name = "Rook";
    private Boolean captured = false;


    public Rook(Board b, char color, int x, int y) {
        this.color = color;
        this.position[0] = x;
        this.position[1] = y;
        this.board = b;
        this.imagePath = this.color + "Rook.png";
        b.squares.get(x)[y].setOccupant(this);        
    }

    /*
    * Input: None
    * Ouptput: Arraylist of integer arrays with coordinates for possible moves [xcor, ycor]
    * Description: Find and store the possible moves
    */  
    @Override
    public ArrayList<int[]> findValidMoves() {
        ArrayList<int[]> validMoves = horizontalHelper(position, board, color);
        validMoves.addAll(verticalHelper(position, board, color));
        return validMoves;
    }

    // set captured

    public void setCaptured(boolean captureState) {
        this.captured = captureState;
    }
    
    // getters

    @Override
    public int[] getPosition() {
        return this.position;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public String getImagePath() {
        return this.imagePath;
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    @Override
    public char getColor() {
        return this.color;
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

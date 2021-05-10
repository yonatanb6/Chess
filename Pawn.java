import java.util.ArrayList;

public class Pawn extends Piece {
    
    private int type = 6;
    private char color;
    private int[] position = new int[2];
    private String imagePath;
    private Board board;
    private boolean firstMove; // has the pawn made its first move 
    private String name = "Pawn";
    private Boolean captured = false;


    public Pawn(Board b, char color, int x, int y) {
        this.color = color;
        this.position[0] = x;
        this.position[1] = y;
        this.board = b;
        this.imagePath = this.color + "Pawn.png";
        b.squares.get(x)[y].setOccupant(this);  
        this.firstMove = true;      
    }

    /*
    * Input: None
    * Ouptput: Arraylist of integer arrays with coordinates for possible moves [xcor, ycor]
    * Description: Find and store the possible moves
    */  
    @Override
    public ArrayList<int[]> findValidMoves() {
        ArrayList<int[]> validMoves = new ArrayList<int[]>();

        int adjustment;
        if (color == 'W') {
            adjustment = -1;
        }
        else {
            adjustment = 1;
        }
        // check for diagonal captures
        for (int horizontal = -1; horizontal < 2; horizontal += 2) {
            int xCor = position[0] + horizontal;
            int yCor = position[1] + adjustment;
            if (xCor < 0 || xCor > 7 || yCor > 7) {
                continue;
            }
            int[] move = {xCor, yCor};
            if (board.squares.get(xCor)[yCor].getOccupant() != null) {
                validMoves.add(move);
            }
        }

        // add two square move if first move
        if (firstMove) {
            int[] move = {position[0], position[1] + (2 * adjustment)};
            // check to see if the square is occupied
            if (board.squares.get(move[0])[move[1]].getOccupant() == null)
            {
                validMoves.add(move);
            }
        }

        // move forward one square
        int[] move = {position[0], position[1] + adjustment};

        // check to see if the square is occupied
        if (board.squares.get(move[0])[move[1]].getOccupant() == null)
        {
            validMoves.add(move);
        }

        // add enpassant 
        return validMoves;
    }

    // setters 

    @Override
    public void setFirstMove() {
        this.firstMove = false;
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
    public Boolean getCaptured() {
        return this.captured;
    }
    @Override
    public String getName() {
        return this.name;
    }
}

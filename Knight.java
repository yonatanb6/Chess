import java.util.ArrayList;

public class Knight extends Piece {
    
    private int type = 4;
    private char color;
    private int[] position = new int[2];
    private String imagePath;
    private Board board;
    private String name = "Knight";
    private Boolean captured = false;


    public Knight(Board b, char color, int x, int y) {
        this.color = color;
        this.position[0] = x;
        this.position[1] = y;
        this.board = b;
        this.imagePath = this.color + "Knight.png";
        b.squares.get(x)[y].setOccupant(this);        
    }

    /*
    * Input: None
    * Ouptput: Arraylist of integer arrays with coordinates for possible moves [xcor, ycor]
    * Description: Find and store the possible moves
    */  
    @Override
    public ArrayList<int[]> findValidMoves() {
        ArrayList<int[]> validMoves = new ArrayList<int[]>();
        // find all squares that are directly diagonal
        for (int horizontal = -1; horizontal < 2; horizontal += 2) {
            for (int vertical = -1; vertical < 2; vertical += 2) {
                
                // reset current position
                int curX = position[0];
                int curY = position[1];

                // move diagonally
                curX += horizontal;
                curY += vertical;
                
                int[] curMove1 = new int[2];
                int[] curMove2 = new int[2];

                // move one over each way
                curMove1[0] = curX;
                curMove1[1] = curY + vertical;

                curMove2[0] = curX + horizontal;
                curMove2[1] = curY;

                if (curMove1[0] <= 7 && curMove1[1] <= 7 && curMove1[0] >= 0 && curMove1[1] >= 0) {
                    validMoves.add(curMove1);
                }
                if (curMove2[0] <= 7 && curMove2[1] <= 7 && curMove2[0] >= 0 && curMove2[1] >= 0) {
                    validMoves.add(curMove2);
                }
            }
        }
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
    public void printValidMoves() {
        
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

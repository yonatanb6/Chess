import java.util.ArrayList;
public class King extends Piece{

    private int type = 99;
    private char color;
    private int[] position = new int[2];
    private String imagePath;
    private Board board;
    private String name = "King";
    private Boolean captured = false;
    private Boolean check = false;
    private Piece checkingPiece;

    public King(Board b, char color, int x, int y) {
        this.color = color;
        this.position[0] = x;
        this.position[1] = y;
        this.board = b;
        this.imagePath = this.color + "King.png";
        b.squares.get(x)[y].setOccupant(this);  
    }

    /*
    * Input: None
    * Ouptput: None
    * Description: Check board for a possible check
    */   
    public Boolean findCheck() {
        /* get the kings current position
        check all oponent pices valid moves and see if king is in check */
        System.out.println(this.color + " " + this.name + " " + "Checking for check....");
        
        // set the enemy color to that opposite the king
        char enemyColor = 'B';
        if (this.color == 'B') {
            enemyColor = 'W';
        }
        
        // find all enemy pieces
        ArrayList<Piece> pieces = this.board.getPieces(enemyColor);
        System.out.print("###########################################");
        for (int i = 0; i < pieces.size(); i++) {

            // if the piece hasn't been captured yet
            if (!pieces.get(i).getCaptured()) {
                
                // ##### TESTING ####
                Piece curPiece = pieces.get(i);
                if (curPiece.getName() == "Pawn") {
                    System.out.println("\nValid Moves for " + curPiece.getColor() + " " + curPiece.getName());
                    curPiece.printValidMoves();
                }

                // #######################

                // find all of their valid moves
                ArrayList<int[]> validMoves = pieces.get(i).findValidMoves();
                for (int j = 0; j < validMoves.size(); j++) {

                    // check to see if the kings current square is one of those moves
                    if (validMoves.get(j)[0] == this.getPosition()[0] && validMoves.get(j)[1] == this.getPosition()[1]) {
                        System.out.println("#### CHECK ####");
                        Square curSquare = this.board.squareFinder(this.position[0], this.position[1]);
                        curSquare.highlightCheck();
                        this.check = true;
                    }
                }
            }
        }
        // return boolean describing if the king is in check
        return null;
    }

    /*
    * Input: None
    * Ouptput: Arraylist of integer arrays with coordinates for possible moves [xcor, ycor]
    * Description: Find and store the possible moves
    */  
    @Override
    public ArrayList<int[]> findValidMoves() {
        ArrayList<int[]> validMoves = new ArrayList<int[]>();

        // loop through all surrounding square
        for (int col = -1; col < 2; col++) {
            for (int row = -1; row < 2; row++) {
                int xPos = position[0] + col;
                int yPos = position[1] + row;
                // skip invalid columns and rows
                if (xPos < 0 || xPos > 7 || yPos < 0 || yPos> 7) {
                    continue;
                }

                Square curSquare = this.board.squares.get(xPos)[yPos];
                
                // skip the square the king occupies
                if (curSquare.getOccupant() == this) {
                    continue;
                }

                // add unoccupied squares as valid
                else if (curSquare.getOccupant() == null) {
                    int[] curMove = {xPos, yPos};
                    validMoves.add(curMove);
                }
             }
        }
        return validMoves;
    }

    // setters

    @Override
    public void setCaptured(boolean captureState) {
        // do nothing
    }

    public void setCheck(Boolean checkState) {
        this.check = checkState;
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

    public Boolean getCheck() {
        return this.check;
    }

    public Piece getCheckingPiece() {
        return this.checkingPiece;
    }

        // setters

    public void setCheck() {
        this.check = true;
    }

    public void setCheckingPiece(Piece p) {
        this.checkingPiece = p;
    }
}